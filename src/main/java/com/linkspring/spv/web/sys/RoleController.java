package com.linkspring.spv.web.sys;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import com.linkspring.spv.common.Page;
import com.linkspring.spv.common.ResponseResult;
import com.linkspring.spv.common.enums.IStatusMessage;
import com.linkspring.spv.pojo.Role;
import com.linkspring.spv.service.SysRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 */
@Controller
@RequestMapping("/spv/sys/roleManager")
public class RoleController {

	private Logger logger = LoggerFactory.getLogger(RoleController.class);
	@Autowired
	private SysRoleService SysRoleService;

	@RequestMapping(value = "/roleList", method = RequestMethod.GET)
	public String toUserList() {
		return "/sys/roleList";
	}

	@RequestMapping(value = "/roleDetail", method = RequestMethod.GET)
	public String toUserDetil() {
		return "/sys/roleDetail";
	}
	/**
	 * 分页查询角色列表
	 */
	@RequestMapping(value = "/getRoles", method = RequestMethod.POST)
	@ResponseBody
	public ResponseResult getRoles(@RequestBody Page page) {
		ResponseResult responseResult = new ResponseResult();
     try{
		List<Role> 	role = SysRoleService.getRoles(page);
		PageInfo<Role> pageInfo =new PageInfo<Role>(role);
		responseResult.setObj(pageInfo);
		} catch (Exception e) {
			e.printStackTrace();
		    responseResult.setCode(IStatusMessage.SystemStatus.PARAM_ERROR
				 .getCode());
		    responseResult.setMessage("查询角色错误");
		}
		return responseResult;
	}
    /**
     * 设置角色[新增或更新]
     */
    @RequestMapping(value = "/setRole", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult setRole(@RequestBody Map<String,Object> param) {
        ResponseResult responseResult = new ResponseResult();
        try {
            ObjectMapper mapper = new ObjectMapper();
            Role role = mapper.convertValue(param.get("tempEntity"), Role.class);
            if (null == role) {
                responseResult.setCode(IStatusMessage.SystemStatus.PARAM_ERROR
                        .getCode());
                responseResult.setMessage("请您填写角色信息");
                return responseResult;
            }
//			if (StringUtils.isEmpty(roleIds)) {
//				logger.debug("置用户[新增或更新]，结果=请您给用户设置角色");
//				return "请您给用户设置角色";
//			}
            responseResult.setMessage("保存成功!");
            responseResult=SysRoleService.setRole(role);
            return  responseResult;
        } catch (Exception e) {
            e.printStackTrace();
            responseResult.setCode(IStatusMessage.SystemStatus.PARAM_ERROR
                    .getCode());
            responseResult.setMessage("操作异常");
            return responseResult;
        }
    }

}
