package com.linkspring.spv.service;

import com.github.pagehelper.PageHelper;
import com.linkspring.spv.common.Page;
import com.linkspring.spv.common.ResponseResult;
import com.linkspring.spv.common.enums.IStatusMessage;
import com.linkspring.spv.config.shiro.ShiroRealm;
import com.linkspring.spv.dao.UserMapper;
import com.linkspring.spv.dao.UserRoleMapper;
import com.linkspring.spv.pojo.User;
import com.linkspring.spv.pojo.UserRoleKey;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 */
@Service
public class UserServiceImpl implements UserService {
	private static final Logger logger = LoggerFactory
			.getLogger(UserServiceImpl.class);
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private UserRoleMapper userRoleMapper;

	private ResponseResult responseResult = new ResponseResult();
	@Override
	public List<User>  getUsers(Page page) {
		PageHelper.startPage(page.getCurrentPage(), page.getPageSize());
		List<User> urList = userMapper.getUsers(page.getCondition());
		return urList;
	}

	@Override
	public String setDelUser(Integer id, Integer isDel, Integer insertUid) {
		return this.userMapper.setDelUser(id, isDel, insertUid) == 1 ? "ok"
				: "删除失败，请您稍后再试";
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 30000, rollbackFor = {
			RuntimeException.class, Exception.class })
	public ResponseResult setUser(User user, ArrayList<Integer> roleIds) {
		try {
		int userId;
		if (user.getId() != null) {
			// 判断用户是否已经存在
			User existUser = this.userMapper.findUserByMobile(user.getMobile());
			if (null != existUser
					&& !String.valueOf(existUser.getId()).equals(
							String.valueOf(user.getId()))) {
				responseResult.setCode(IStatusMessage.SystemStatus.PARAM_ERROR.getCode());
				responseResult.setMessage("该手机号已经存在");
				return responseResult;
			}
			User exist = this.userMapper.findUserByName(user.getUsername());
			if (null != exist
					&& !String.valueOf(exist.getId()).equals(
							String.valueOf(user.getId()))) {
				responseResult.setCode(IStatusMessage.SystemStatus.PARAM_ERROR.getCode());
				responseResult.setMessage("该用户名已经存在");
				return responseResult;
			}
			User dataUser = this.userMapper.selectByPrimaryKey(user.getId());
			// 版本不一致
			if (null != dataUser
					&& null != dataUser.getVersion()
					&& !String.valueOf(user.getVersion()).equals(
							String.valueOf(dataUser.getVersion()))) {
				responseResult.setCode(IStatusMessage.SystemStatus.ERROR.getCode());
				responseResult.setMessage("操作失败，请您稍后再试\"");
				return responseResult;
			}
			// 更新用户
			userId = user.getId();
			user.setUpdateTime(new Date());
			// 设置加密密码
			/*if (StringUtils.isNotBlank(sys.getPassword())) {
				sys.setPassword(DigestUtils.md5Hex(sys.getPassword()));
			}*/
			this.userMapper.updateByPrimaryKeySelective(user);
			// 删除之前的角色
			List<UserRoleKey> urs = this.userRoleMapper.findByUserId(userId);
			if (null != urs && urs.size() > 0) {
				for (UserRoleKey ur : urs) {
					this.userRoleMapper.deleteByPrimaryKey(ur);
				}
			}
			// 如果是自己，修改完成之后，直接退出；重新登录
			User adminUser = (User) SecurityUtils.getSubject().getPrincipal();
			if (adminUser != null
					&& adminUser.getId().intValue() == user.getId().intValue()) {
				logger.debug("更新自己的信息，退出重新登录！adminUser=" + adminUser);
				SecurityUtils.getSubject().logout();
			}
			// 方案一【不推荐】：通过SessionDAO拿到所有在线的用户，Collection<Session> sessions =
			// sessionDAO.getActiveSessions();
			// 遍历找到匹配的，更新他的信息【不推荐，分布式或用户数量太大的时候，会有问题。】；
			// 方案二【推荐】：用户信息价格flag（或version）标记，写个拦截器，每次请求判断flag（或version）是否改动，如有改动，请重新登录或自动更新用户信息（推荐）；

			// 清除ehcache中所有用户权限缓存，必须触发鉴权方法才能执行授权方法doGetAuthorizationInfo
			RealmSecurityManager rsm = (RealmSecurityManager) SecurityUtils
					.getSecurityManager();
			ShiroRealm authRealm = (ShiroRealm) rsm.getRealms().iterator()
					.next();
			authRealm.clearCachedAuth();
			logger.debug("清除所有用户权限缓存！！！");
		} else {
			// 判断用户是否已经存在
			User existUser = this.userMapper.findUserByMobile(user.getMobile());
			if (null != existUser) {
				responseResult.setCode(IStatusMessage.SystemStatus.PARAM_ERROR.getCode());
				responseResult.setMessage("该手机号已经存在");
				return responseResult;
			}
			User exist = this.userMapper.findUserByName(user.getUsername());
			if (null != exist) {
				responseResult.setCode(IStatusMessage.SystemStatus.PARAM_ERROR.getCode());
				responseResult.setMessage("该用户名已经存在");
				return responseResult;

			}
			// 新增用户
			user.setInsertTime(new Date());
			user.setIsDel(false);
			user.setIsJob(false);
			// 设置加密密码
			if (StringUtils.isNotBlank(user.getPassword())) {
				user.setPassword(DigestUtils.md5Hex(user.getPassword()));
			} else {
				user.setPassword(DigestUtils.md5Hex("111111"));
			}
			this.userMapper.insert(user);
			userId = user.getId();
		}
		// 给用户授角色
		if(!"".equals(roleIds)&&roleIds!=null){
			//String[] arrays = roleIds.split(",");
			for (Integer roleId : roleIds) {
				UserRoleKey urk = new UserRoleKey();
				urk.setRoleId(roleId);
				urk.setUserId(userId);
				this.userRoleMapper.insert(urk);
			}
		}

		}catch (Exception e){
			e.printStackTrace();
			responseResult.setCode(IStatusMessage.SystemStatus.ERROR.getCode());
			responseResult.setMessage("系统异常,请联系管理员!");
		}
		return responseResult;
	}



	@Override
	public List<UserRoleKey> getUserRoles(Integer id) {
		List<UserRoleKey> userRoles = this.userRoleMapper.findByUserId(id);
		return userRoles;
	}

	@Override
	public User findUserByMobile(String mobile) {
		return this.userMapper.findUserByMobile(mobile);
	}
	@Override
	public User findUserByName(String username) {
		return this.userMapper.findUserByName(username);
	}
	@Override
	public int updatePwd(Integer id, String password) {
		return this.userMapper.updatePwd(id, password);
	}

}
