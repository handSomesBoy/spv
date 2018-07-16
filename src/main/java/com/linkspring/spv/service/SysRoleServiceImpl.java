package com.linkspring.spv.service;

import com.github.pagehelper.PageHelper;
import com.linkspring.spv.common.Page;
import com.linkspring.spv.common.ResponseResult;
import com.linkspring.spv.common.enums.IStatusMessage;
import com.linkspring.spv.dao.RoleMapper;
import com.linkspring.spv.pojo.Role;
import com.linkspring.spv.pojo.User;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 示例 使用通用mapper
 */
@Service
public class SysRoleServiceImpl  implements SysRoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Role> getRoleByUser(Integer userId) {
        return roleMapper.getRoleByUser(userId);
    }

    @Override
    public List<Role> getAllRoles() {
        return roleMapper.selectAll();
    }

    @Override
    public List<Role> getRoles(Page page) {
        PageHelper.startPage(page.getCurrentPage(), page.getPageSize());
        Example example=new Example(Role.class);
        example.orderBy("id").desc();
        example.setDistinct(true);
        //example.selectProperties("author");
        Example.Criteria criteria01=example.createCriteria();
        for (Map.Entry<String, Object> entry : page.getCondition().entrySet()) {
            System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
            criteria01.andEqualTo(entry.getKey(), entry.getValue());
        }
        example.and(criteria01);
        List<Role> roleList= roleMapper.selectByExample(example);
        return roleList;
    }

    @Override
    public ResponseResult setRole(Role role) {
        ResponseResult responseResult = new ResponseResult();
        try {
            if(role.getId()!=null){
                role.setUpdateTime(new Date());
                roleMapper.updateByPrimaryKeySelective(role);
            }else{
                role.setIsUse(0);
                role.setInsertTime(new Date());
                User existUser = (User) SecurityUtils.getSubject().getPrincipal();
                role.setInsertUid(existUser.getId());
                roleMapper.insert(role);
            }
        } catch (Exception e) {
            responseResult.setCode(IStatusMessage.SystemStatus.ERROR.getCode());
            responseResult.setMessage(IStatusMessage.SystemStatus.ERROR.getMessage());
            e.printStackTrace();
        } finally {
        }
        return responseResult;
    }
}
