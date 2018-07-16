package com.linkspring.spv.web.sys;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import com.linkspring.common.utils.ValidateUtil;
import com.linkspring.spv.common.Page;
import com.linkspring.spv.common.ResponseResult;
import com.linkspring.spv.common.enums.IStatusMessage;
import com.linkspring.spv.pojo.Role;
import com.linkspring.spv.pojo.User;
import com.linkspring.spv.pojo.UserRoleKey;
import com.linkspring.spv.service.SysRoleService;
import com.linkspring.spv.service.UserService;
import com.linkspring.spv.utils.OvalValidateUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户管理
 */
@Controller
@RequestMapping("/spv/sys/userManager")
public class UserController {

	private Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private UserService userService;
	@Autowired
	private SysRoleService sysRoleService;

	@RequestMapping(value = "/userList", method = RequestMethod.GET)
 /*   @RequiresPermissions(value = "usermanage")*/
	public String toUserList() {
		return "/sys/userList";
	}

	/**
	 * 分页查询用户列表
	 * 转实体示例
	 */
	@RequestMapping(value = "/getUsers", method = RequestMethod.POST)
	@ResponseBody
/*	@RequiresPermissions(value = "usermanage")*/
	///public ResponseResult getUsers(@RequestBody Map<String,Object> pageParam) {
	public ResponseResult getUsers(@RequestBody Page page) {
		ResponseResult responseResult = new ResponseResult();
		//屏蔽代码的功能 实现从map转为实体 学习
		//Page page=new Page();
     try{
     	 //A 第一种 先json再bean
		 ///ObjectMapper mapper = new ObjectMapper();
		 //String jsonString = mapper.writeValueAsString(pageParam);
		 //page= mapper.readValue(jsonString, Page.class);
		 //B 第二种 convert
		 //Page pojo = mapper.convertValue(pageParam, Page.class);
		 //List<POJO> pojos = mapper.convertValue(listOfObjects, new TypeReference<List<POJO>>() { });
		 //B 第三种 BeanUtils
		 //BeanUtils.populate(page,pageParam);//填充到page对象上用，也可以直接用map
		List<User> 	user = userService.getUsers(page);
		PageInfo<User> pageInfo =new PageInfo<User>(user);
		 responseResult.setObj(pageInfo);
		 HashMap<String, Object> objs=new HashMap();
		 objs.put("page",pageInfo);
		 responseResult.setObjs(objs);
		} catch (Exception e) {
			e.printStackTrace();
		    responseResult.setCode(IStatusMessage.SystemStatus.PARAM_ERROR
				 .getCode());
		    responseResult.setMessage("查询用户错误");
		}
		return responseResult;
	}

	/**
	 * 设置用户[新增或更新]
	 * @return ok/fail
	 */
	@RequestMapping(value = "/setUser", method = RequestMethod.POST)
	@ResponseBody
	public ResponseResult setUser(@RequestBody Map<String,Object> param) {
		ResponseResult responseResult = new ResponseResult();
		ArrayList<Integer> roleIds=(ArrayList<Integer>)param.get("selectRoles");
		try {
			ObjectMapper mapper = new ObjectMapper();
			User user = mapper.convertValue(param.get("tempUser"), User.class);
			if (null == user) {
				responseResult.setCode(IStatusMessage.SystemStatus.PARAM_ERROR
						.getCode());
				responseResult.setMessage("请您填写用户信息");
				return responseResult;
			}
//			if (StringUtils.isEmpty(roleIds)) {
//				logger.debug("置用户[新增或更新]，结果=请您给用户设置角色");
//				return "请您给用户设置角色";
//			}
			User existUser = (User) SecurityUtils.getSubject().getPrincipal();
			if (null == existUser) {
                responseResult.setCode(IStatusMessage.SystemStatus.PARAM_ERROR
                        .getCode());
                responseResult.setMessage("您未登录或登录超时，请您登录后再试");
                return responseResult;
            }
            user.setInsertUid(existUser.getId());
			boolean flag= OvalValidateUtils.validatorRequestParam(user,responseResult);//通过后台oval校验 示例
           if(flag){
			   responseResult.setMessage("保存成功!");
			   responseResult=userService.setUser(user, roleIds);
		   }
			return  responseResult;
		} catch (Exception e) {
			e.printStackTrace();
			responseResult.setCode(IStatusMessage.SystemStatus.PARAM_ERROR
					.getCode());
			responseResult.setMessage("操作异常");
			return responseResult;
		}
	}

	/**
	 * 删除用户
	 * @return ok/fail
	 */
	@RequestMapping(value = "/delUser", method = RequestMethod.POST)
	@ResponseBody
	public ResponseResult delUser(@RequestBody Map<String,Object> param) {
		ResponseResult responseResult = new ResponseResult();
		List<Integer> deleteIds=(List<Integer>) param.get("deleteIds");
		Boolean delType=(Boolean) param.get("delType");
		int idDel;
        responseResult.setCode(IStatusMessage.SystemStatus.SUCCESS.getCode());
        if(delType){
            responseResult.setMessage("用户停用成功");
            idDel=1;
        }else {
            responseResult.setMessage("用户启用成功");
            idDel=0;
        }

		try {
			User existUser = (User) SecurityUtils.getSubject().getPrincipal();
			if (null == existUser) {
                responseResult.setCode(IStatusMessage.SystemStatus.PARAM_ERROR.getCode());
                responseResult.setMessage("您未登录或登录超时，请您登录后再试");
				return responseResult;
			}
			for(int id:deleteIds) {
				// 删除用户
				userService.setDelUser(id, idDel, existUser.getId());
			}
		} catch (Exception e) {
			e.printStackTrace();
            responseResult.setCode(IStatusMessage.SystemStatus.PARAM_ERROR.getCode());
            responseResult.setMessage("操作异常，请您稍后再试");
            return responseResult;
		}
		return responseResult;
	}

	/**
	 * 查询用户可选角色 和 已选角色
	 */
	@RequestMapping(value = "/getUserRole", method = RequestMethod.POST)
	@ResponseBody
	public ResponseResult getUserRole(@RequestBody Map<String,Object> param) {
		ResponseResult responseResult = new ResponseResult();
		Integer id=(Integer) param.get("id");
		List<UserRoleKey> selectRoles=userService.getUserRoles(id);
		List<Role> roles=sysRoleService.getAllRoles();
		HashMap<String,Object> objs=new HashMap();
		objs.put("selectRoles",selectRoles);
		objs.put("roles",roles);
		responseResult.setObjs(objs);
		return responseResult;
	}


	/**
	 * 修改密码
	 * @return
	 */
	@RequestMapping(value = "setPwd", method = RequestMethod.POST)
	@ResponseBody
	public ResponseResult setPwd(@RequestBody Map<String,Object> param) {
		Integer id= (Integer) param.get("id");
		String pwd= (String) param.get("pwd");
		String oldPwd= (String) param.get("oldPwd");
		ResponseResult responseResult = new ResponseResult();
		try {
			if (!ValidateUtil.isSimplePassword(pwd)) {
				responseResult.setCode(IStatusMessage.SystemStatus.PARAM_ERROR
						.getCode());
				responseResult.setMessage("密码格式有误，请您重新填写");
				logger.debug("修改密码，结果=responseResult:" + responseResult);
				return responseResult;
			}
			/*if (!pwd.equals(oldPwd)&&oldPwd.equals(pwd)) {
				responseResult.setCode(IStatusMessage.SystemStatus.PARAM_ERROR
						.getCode());
				responseResult.setMessage("两次密码输入不一致，请您重新填写");
				logger.debug("发修改密码，结果=responseResult:" + responseResult);
				return responseResult;
			}*/
			// 判断用户是否登录
			User existUser = (User) SecurityUtils.getSubject().getPrincipal();
			if (null == existUser) {
				responseResult.setCode(IStatusMessage.SystemStatus.NO_LOGIN
						.getCode());
				responseResult.setMessage("您未登录或登录超时，请您重新登录后再试");
				logger.debug("修改密码，结果=responseResult:" + responseResult);
				return responseResult;
			}
			// 修改密码
			int num = this.userService.updatePwd(id,
					DigestUtils.md5Hex(pwd));
			if (num != 1) {
				responseResult.setCode(IStatusMessage.SystemStatus.ERROR
						.getCode());
				responseResult.setMessage("修改密码失败，已经离职或该用户被删除！");
				return responseResult;
			}
		} catch (Exception e) {
			e.printStackTrace();
			responseResult.setCode(IStatusMessage.SystemStatus.ERROR.getCode());
			responseResult.setMessage("操作失败，请您稍后再试");
			logger.error("修改密码异常！", e);
		}
		responseResult.setMessage("密码修改成功");
		return responseResult;
	}

	/**
	 * 添加用户2
	 * @param userInfo
	 */
	@RequestMapping(value = "createUser", method = RequestMethod.POST)
	@ResponseBody
	public String createUser(@RequestBody User userInfo, BindingResult bindingResult){
		//不加@RequestBody，则为 query string parameters 类似？传参
		System.err.print("测试接受对象类型参数:"+userInfo.toString());
		if (bindingResult.hasErrors()){
			return bindingResult.getFieldError().getDefaultMessage();
		}
		return "OK";
	}

}
