package com.soubao.common.exception;

import com.soubao.common.utils.ShopStringUtil;
import com.soubao.common.vo.SBApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 处理所有不可知的异常
     * @return sbApi
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    SBApi handleException(Exception e){
        log.error(e.getMessage(), e);
        SBApi sbApi = new SBApi();
        sbApi.setStatus(ResultEnum.UNKNOWN_ERROR.getCode());
        sbApi.setMsg(e.toString());
        return sbApi;
    }

    /**
     * 处理所有业务异常
     * @return sbApi
     */
    @ExceptionHandler(ShopException.class)
    @ResponseBody
    SBApi handleBusinessException(ShopException e){
        log.error(e.getMessage(), e);
        SBApi sbApi = new SBApi();
        sbApi.setStatus(e.getCode());
        sbApi.setMsg(e.getMessage());
        sbApi.setResult(e.getResult());
        return sbApi;
    }

    /**
     * 处理所有接口数据验证异常
     * @return api
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    SBApi handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        log.error(e.getMessage(), e);
        SBApi sbApi = new SBApi();
        sbApi.setStatus(ResultEnum.FAIL.getCode());
        sbApi.setMsg(ResultEnum.FAIL.getMsg());
        Map<String, String> resultMap = new HashMap<>();
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        fieldErrors.forEach( error -> {
            resultMap.put(ShopStringUtil.humpToLine(error.getField()), error.getDefaultMessage());
        });
        sbApi.setResult(resultMap);
        return sbApi;
    }

    @ExceptionHandler(BindException.class)
    @ResponseBody
    SBApi handleBindException(BindException e){
        log.error(e.getMessage(), e);
        SBApi sbApi = new SBApi();
        sbApi.setStatus(ResultEnum.FAIL.getCode());
        sbApi.setMsg(e.getFieldErrors().get(0).getDefaultMessage());
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        Map<String, String> resultMap = new HashMap<>();
        for (FieldError error:fieldErrors){
            resultMap.put(error.getField(), error.getDefaultMessage());
        }
        sbApi.setResult(resultMap);
        return sbApi;
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(value= HttpStatus.UNAUTHORIZED)
    @ResponseBody
    SBApi accessDeniedException(AccessDeniedException e, HttpServletRequest request) {
        log.error("请求url:{},权限被拒绝, 原因：{}", request.getRequestURI(), e.getMessage());
        SBApi sbApi = new SBApi();
        sbApi.setStatus(ResultEnum.NO_LOGIN_ERROR.getCode());
        sbApi.setMsg(ResultEnum.NO_LOGIN_ERROR.getMsg());
        return sbApi;
    }
}
