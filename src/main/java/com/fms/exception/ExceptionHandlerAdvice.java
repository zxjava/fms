package com.fms.exception;

import com.fms.dto.ResultTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Admin on 2016/1/14 0014.
 */
@ControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResultTO exceptionResponse(Exception e) {
        ResultTO result = new ResultTO();
        result.setSuccess(false);
        result.setCode(500);
        e.printStackTrace();
        result.setMsg("服务器繁忙！");
        return result;
    }


    @ExceptionHandler(value = CommonException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResultTO commonResponse(CommonException ce) {
        ResultTO result = new ResultTO();
        result.setSuccess(false);
        result.setCode(400);
        result.setMsg(ce.getMessage());
        return result;
    }


}
