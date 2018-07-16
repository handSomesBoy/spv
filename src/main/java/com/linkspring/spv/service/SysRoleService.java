package com.linkspring.spv.service;

import com.linkspring.spv.common.Page;
import com.linkspring.spv.common.ResponseResult;
import com.linkspring.spv.pojo.Role;
import com.linkspring.spv.pojo.User;

import java.util.ArrayList;
import java.util.List;

/**
 * 通用Service
 */

public interface SysRoleService {
     /*分页查询角色*/
     List<Role> getRoles(Page page);
     /*根据人员查询角色*/
     List<Role> getRoleByUser(Integer userId);
     /*查询全部角色*/
     List<Role> getAllRoles();
     //
     ResponseResult setRole(Role role);
}
