package com.linkspring.spv.web;


import com.linkspring.spv.pojo.Permission;
import com.linkspring.spv.pojo.User;
import com.linkspring.spv.service.SysPermissionService;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sysMenu")
public class SysMenuController {
    @Autowired
    private SysPermissionService sysMenuService;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 查询
     */
    @RequestMapping(value = "/queryMenu",method = RequestMethod.POST)
    @ResponseBody
    public List<Permission> queryMenuList(){
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Integer userId = user.getId();
        List<Permission> menuList=sysMenuService.queryMenu(userId,0);
        for (Permission rootMenu : menuList) {
            // rootMenu是根菜单
            // 给rootMenu创建一级菜单
            rootMenu.setChildren(sysMenuService.queryMenu(userId,(rootMenu.getId())));

            for (Permission childMenu : rootMenu.getChildren()) {
                // childMenu是一级菜单
                // 给childMenu创建子菜单（二级菜单）
                childMenu.setChildren(sysMenuService.queryMenu(userId,(childMenu.getId())));

                for (Permission childOfChildMenu : childMenu.getChildren()) {
                    // childOfChildMenu是二级菜单
                    // 给childOfChildMenu创建子菜单（三级菜单）
                    childOfChildMenu.setChildren(sysMenuService.queryMenu(userId,(childOfChildMenu.getId())));
                }
            }
        }
        return menuList;
    }
}