package com.linkspring.spv.web.sys;

import com.linkspring.spv.common.ResponseResult;
import com.linkspring.spv.entity.UserDTO;
import com.linkspring.spv.pojo.User;
import com.linkspring.spv.service.UserService;
import com.linkspring.spv.common.enums.IStatusMessage;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 登录
 */
@Controller
public class LoginController {
	private Logger logger = LoggerFactory.getLogger(LoginController.class);
	@Autowired
	private UserService userService;
	@Autowired
	private EhCacheManager ecm;
	/**
	 * 登录【使用shiro中自带的HashedCredentialsMatcher结合ehcache（记录输错次数）配置进行密码输错次数限制】
	 * </br>缺陷是，无法友好的在后台提供解锁用户的功能，当然，可以直接提供一种解锁操作，清除ehcache缓存即可，不记录在用户表中；
	 * </br>
	 * @param user
	 * @param rememberMe
	 * @return
	 */
	@RequestMapping(value = "loginCheck", method = RequestMethod.POST)
	@ResponseBody
	public ResponseResult login(
			UserDTO user,
			@RequestParam(value = "rememberMe", required = false) boolean rememberMe) {
		ResponseResult responseResult = new ResponseResult();
		responseResult.setCode(IStatusMessage.SystemStatus.ERROR.getCode());
		if (null == user) {
			responseResult.setCode(IStatusMessage.SystemStatus.PARAM_ERROR
					.getCode());
			responseResult.setMessage("请求参数有误，请您稍后再试");
			logger.debug("用户登录，结果=responseResult:" + responseResult);
			return responseResult;
		}
		//取消后台验证必填
		/*if (!validatorRequestParam(sys, responseResult)) {
			logger.debug("用户登录，结果=responseResult:" + responseResult);
			return responseResult;
		}*/
		// 用户是否存在
		User existUser = this.userService.findUserByName(user.getUsername());
		if (existUser == null) {
			responseResult.setMessage("该用户不存在，请您联系管理员");
			return responseResult;
		} else {
			// 是否离职
			if (existUser.getIsJob()) {
				responseResult.setMessage("登录用户已离职，请您联系管理员");
				return responseResult;
			}
		}
		// 用户登录
		try {
			// 1、 封装用户名、密码、是否记住我到token令牌对象 [支持记住我]
			AuthenticationToken token = new UsernamePasswordToken(
					user.getUsername(), DigestUtils.md5Hex(user.getPassword()),
					rememberMe);
			// 2、 Subject调用login
			Subject subject = SecurityUtils.getSubject();
			// 在调用了login方法后,SecurityManager会收到AuthenticationToken,并将其发送给已配置的Realm执行必须的认证检查
			// 每个Realm都能在必要时对提交的AuthenticationTokens作出反应
			// 所以这一步在调用login(token)方法时,它会走到MyRealm.doGetAuthenticationInfo()方法中,具体验证方式详见此方法
			subject.login(token);
			responseResult.setCode(IStatusMessage.SystemStatus.SUCCESS
					.getCode());
			//返回user obj
			responseResult.setObj(existUser);
		} catch (UnknownAccountException uae) {
			logger.error("用户登录，用户验证未通过：未知用户！sys=" + user.getMobile(), uae);
			responseResult.setMessage("该用户不存在，请您联系管理员");
		} catch (IncorrectCredentialsException ice) {
			// 获取输错次数
			logger.error("用户登录，用户验证未通过：错误的凭证，密码输入错误！sys=" + user.getMobile(),
					ice);
			responseResult.setMessage("用户名或密码不正确");
		} catch (LockedAccountException lae) {
			logger.error("用户登录，用户验证未通过：账户已锁定！sys=" + user.getMobile(), lae);
			responseResult.setMessage("账户已锁定");
		} catch (ExcessiveAttemptsException eae) {
			logger.error(
					"用户登录，用户验证未通过：错误次数大于5次,账户已锁定！sys=.getMobile()" + user, eae);
			responseResult
					.setMessage("用户名或密码错误次数大于5次,账户已锁定!</br><span style='color:red;font-weight:bold; '>2分钟后可再次登录，或联系管理员解锁</span>");
			// 这里结合了，另一种密码输错限制的实现，基于redis或mysql的实现；也可以直接使用RetryLimitHashedCredentialsMatcher限制5次
		} /*catch (DisabledAccountException sae){
		 logger.error("用户登录，用户验证未通过：帐号已经禁止登录！sys=" +
		 sys.getMobile(),sae);
		 responseResult.setCode(IStatusMessage.SystemStatus.ERROR.getCode());
		 responseResult.setMessage("帐号已经禁止登录");
		}*/catch (AuthenticationException ae) {
			// 通过处理Shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景
			logger.error("用户登录，用户验证未通过：认证异常，异常信息如下！sys=" + user.getMobile(),
					ae);
			responseResult.setMessage("用户名或密码不正确");
		} catch (Exception e) {
			logger.error("用户登录，用户验证未通过：操作异常，异常信息如下！sys=" + user.getMobile(), e);
			responseResult.setMessage("用户登录失败，请您稍后再试");
		}
		Cache<String, AtomicInteger> passwordRetryCache = ecm
				.getCache("passwordRetryCache");
		if (null != passwordRetryCache) {
			int retryNum = (passwordRetryCache.get(existUser.getMobile()) == null ? 0
					: passwordRetryCache.get(existUser.getMobile())).intValue();
			logger.debug("输错次数：" + retryNum);
			if (retryNum > 0 && retryNum < 6) {
				responseResult.setMessage("用户名或密码错误" + retryNum + "次,再输错"
						+ (6 - retryNum) + "次账号将锁定");
			}
		}
		logger.debug("用户登录，sys=" + user.getMobile() + ",登录结果=responseResult:"
				+ responseResult);
		return responseResult;
	}
}
