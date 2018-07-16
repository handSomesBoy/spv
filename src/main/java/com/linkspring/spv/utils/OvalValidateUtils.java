package com.linkspring.spv.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.linkspring.spv.common.ResponseResult;
import com.linkspring.spv.common.enums.IStatusMessage;
import net.sf.oval.ConstraintViolation;
import net.sf.oval.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;

/**
 * 
 * @项目名称：spv-manager
 * @类名称：ShiroFilterUtils
 * @类描述：shiro工具类
 * @创建人：spv
 * @创建时间：2018年4月24日 下午5:12:04 
 * @version：
 */
public class OvalValidateUtils {
	private static final Logger logger = LoggerFactory
			.getLogger(OvalValidateUtils.class);
    /**
     *  可以使用
     */
	public static boolean validatorRequestParam(Object obj, ResponseResult response) {
		boolean flag = false;
		Validator validator = new Validator();
		List<ConstraintViolation> ret = validator.validate(obj);
		if (ret.size() > 0) {
			// 校验参数有误
			response.setCode(IStatusMessage.SystemStatus.PARAM_ERROR.getCode());
			response.setMessage(ret.get(0).getMessageTemplate());
		} else {
			flag = true;
		}
		return flag;
	}

}
