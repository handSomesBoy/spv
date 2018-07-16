package com.linkspring.spv.service;

import com.linkspring.spv.pojo.Permission;

import java.util.List;

/**
 * Created by tengj on 2017/4/7.
 */

public interface SysPermissionService{
     List<Permission> queryMenu(Integer userId,Integer pid);
     List<Permission> getAllPermission();
     List<Permission> getPermissionByRole(Integer roleId);
}
