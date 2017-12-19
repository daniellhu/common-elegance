package com.yonyou.cloud.common.exception;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yonyou.cloud.common.beans.ResultBean;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
	
    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    
    @ExceptionHandler(BizException.class)
    public ResultBean baseExceptionHandler(HttpServletResponse response, BizException ex) {
        logger.error(ex.getMessage(),ex);
        return new ResultBean(ex.getCode(), ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResultBean otherExceptionHandler(HttpServletResponse response, Exception ex) {
        logger.error(ex.getMessage(),ex);
        return new ResultBean(ResultBean.ERROR_UNKNOWN, ex.getMessage());
    }
}
