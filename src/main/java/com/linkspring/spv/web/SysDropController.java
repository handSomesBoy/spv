package com.linkspring.spv.web;


import com.linkspring.spv.dao.UserMapper;
import com.linkspring.spv.pojo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

//枚举值获取
@RestController
@RequestMapping("/sysDrop")
public class SysDropController {
    @Autowired
    private UserMapper userMapper;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 查询
     */
    @RequestMapping(value = "/getValueByCode",method = RequestMethod.POST)
    @ResponseBody
    public String getValueByCode(@RequestBody Map<String,Object> param){
        //示例 后续根据配置的sql取的结果 ，或者从缓存获取
        String typeCode=param.get("typeCode").toString();
        String paramCode=param.get("paramCode").toString();
        String paramValue="";
        if("SPV.USER_NAME".equals(typeCode)){
               User user=userMapper.selectByPrimaryKey(Integer.valueOf(paramCode));
               if(user!=null)
               {
                   paramValue=user.getFullname();
               }
        }
        return paramValue;
    }
}