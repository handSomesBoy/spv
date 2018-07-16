package com.linkspring.spv.service;

import com.linkspring.spv.common.Page;
import com.linkspring.spv.common.ResponseResult;
import com.linkspring.spv.pojo.User;
import com.linkspring.spv.pojo.UserRoleKey;

import java.util.ArrayList;
import java.util.List;

/**
 */
public interface UserService {
	/**
	 * 分页查询用户列表
	 * @return
	 */
	List<User> getUsers(Page page);

	/**
	 *	设置用户【新增或更新】
	 * @param user
	 * @param roleIds
	 * @return
	 */
	ResponseResult setUser(User user, ArrayList<Integer> roleIds);


	/**
	 * 删除用户
	 * @param id
	 * @param isDel
	 * @return
	 */
	String setDelUser(Integer id, Integer isDel, Integer insertUid);


	List<UserRoleKey> getUserRoles(Integer id);
	/**
	 * 根据手机号查询用户数据
	 * @param mobile
	 * @return
	 */
	User findUserByMobile(String mobile);
	/**
	 * 根据用户名查询用户数据
	 * @param username
	 * @return
	 */
	User findUserByName(String username);

	/**
	 * 修改用户手机号
	 * @param id
	 * @param password
	 * @return
	 */
	int updatePwd(Integer id, String password);

}
