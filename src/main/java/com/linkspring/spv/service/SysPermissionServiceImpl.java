package com.linkspring.spv.service;

import com.linkspring.spv.dao.PermissionMapper;
import com.linkspring.spv.pojo.Permission;
import com.linkspring.spv.pojo.Role;
import com.linkspring.spv.pojo.User;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * Created by tengj on 2017/4/7.
 */
@Service
public class SysPermissionServiceImpl  implements SysPermissionService {

    @Autowired
    private PermissionMapper permissionMapper;
    //取消使用自定义mapper
    @Override
    public List<Permission> queryMenu(Integer userId,Integer pid) {
        List<Permission> menuList=this.permissionMapper.getPermsByUserByParentPerm(userId,pid);
        return menuList;
    }
    @Override
    public List<Permission> getAllPermission() {
        List permissionLists= permissionMapper.findAll();
        return permissionLists;
    }

    @Override
    public List<Permission> getPermissionByRole(Integer roleId) {
        return permissionMapper.findPermsByRole(roleId);
    }
}
