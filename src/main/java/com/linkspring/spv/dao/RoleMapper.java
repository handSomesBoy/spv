package com.linkspring.spv.dao;

import com.linkspring.spv.common.MyMapper;
import com.linkspring.spv.pojo.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface RoleMapper  extends MyMapper<Role> {

	/**
	 * 根据用户id获取角色数据
	 * @param userId
	 * @return
	 */
	List<Role> getRoleByUser(Integer userId);


	/**
	 * 分页查询用户数据
	 * @return
	 */
	List<Role> getRoles(Map<String,Object> map);

}