package com.linkspring.spv.common;

import com.linkspring.spv.common.enums.ExceptionEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.AbstractErrorController;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * springboot异常重写
 */
@Controller
@RequestMapping("error")
public class GlobalErrorController extends AbstractErrorController {

    private static final Logger logger = LoggerFactory
            .getLogger(GlobalErrorController.class);

    private static final String ERROR_PATH = "framework/error";


    //构造
    public GlobalErrorController(ErrorAttributes errorAttributes) {
        super(errorAttributes);
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }

    @RequestMapping(produces = "text/html")
    public ModelAndView errorHtml(HttpServletRequest request,
                                  HttpServletResponse response) {
        ModelAndView mv = new ModelAndView(ERROR_PATH);
        Map<String, Object> errMap = getErrorAttributes(request, isIncludeStackTrace(request, MediaType.TEXT_HTML));
        logger.info("统一异常处理【" + getClass().getName()
                + ".error】统一异常处理：errMap=" + errMap);
        Integer statusCode = (Integer) errMap.get("status");
        String msg = "";
        switch (statusCode) {
            case 400:
                msg = ExceptionEnum.BAD_REQUEST.getMsg();
                break;
            case 403:
                msg = ExceptionEnum.BAD_REQUEST.getMsg();
                break;
            case 404:
                msg = ExceptionEnum.NOT_FOUND.getMsg();
                break;
            case 405:
                msg = "方法不被允许";
                break;
            case 500:
                msg = ExceptionEnum.SERVER_EPT.getMsg();
                break;
            default:
                msg = ExceptionEnum.UNKNOW_ERROR.getMsg();
        }
        errMap.put("msg", msg);
        if(errMap.get("exception")==null)
        {
            errMap.put("exception","");
        }
        // 3 将错误信息放入mv中
        mv.addObject("error", errMap);
        logger.info("统一异常处理【" + getClass().getName() + ".errorHtml】统一异常处理!错误信息mv：" + mv);
        return mv;
    }

    @RequestMapping
    @ResponseBody
    //设置响应状态码为：200，结合前端约定的规范处理。也可不设置状态码，前端ajax调用使用error函数进行控制处理
	/*@ResponseStatus(value=HttpStatus.OK)
	public Result<String> error(HttpServletRequest request, Exception e) {
		// 1 获取错误状态码（也可以根据异常对象返回对应的错误信息）
		HttpStatus httpStatus = getStatus(request);
		logger.debug("统一异常处理【" + getClass().getName()
				+ ".error】统一异常处理!错误状态码httpStatus：" + httpStatus);
		// 2 返回错误提示
		ExceptionEnum ee = getMessage(httpStatus);
		Result<String> result = new Result<String>(
				String.valueOf(ee.getType()), ee.getCode(), ee.getMsg());
		// 3 将错误信息返回
		//		ResponseEntity
		logger.info("统一异常处理【" + getClass().getName()
				+ ".error】统一异常处理!错误信息result：" + result);
		return result;
	}*/

    /**
     *
     * @描述：根据error状态码，返回不同的错误提示信息
     */
    /*private ExceptionEnum getMessage(HttpStatus httpStatus) {
        if (httpStatus.is4xxClientError()) {
            switch (httpStatus.value()) {
                case 400:
                    return ExceptionEnum.BAD_REQUEST;
                case 403:
                    return ExceptionEnum.BAD_REQUEST;
                case 404:
                    return ExceptionEnum.NOT_FOUND;
            }

        } else if (httpStatus.is5xxServerError()) {
            if ("500".equals(httpStatus.value())) {
                return ExceptionEnum.SERVER_EPT;
            }
        }
        return ExceptionEnum.UNKNOW_ERROR;
    }*/

    private boolean isIncludeStackTrace(HttpServletRequest request,
                                        MediaType produces) {
        ErrorProperties.IncludeStacktrace include = getErrorProperties().getIncludeStacktrace();
        if (include == ErrorProperties.IncludeStacktrace.ALWAYS) {
            return true;
        }
        if (include == ErrorProperties.IncludeStacktrace.ON_TRACE_PARAM) {
            return getTraceParameter(request);
        }
        return false;
    }

    /**
     * 此处也可以通过注入ServerProperties获取ErrorProperties
     *
     * @return
     */
    protected ErrorProperties getErrorProperties() {
        return new ErrorProperties();
    }
}
