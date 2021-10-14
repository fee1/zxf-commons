package com.zxf.common.exception;


import com.zxf.common.model.ApiResult;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常捕捉
 *
 * @author zhuxiaofeng
 * @date 2021/9/4
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 通用异常捕捉
     * @param ex 异常
     * @return api
     */
    @ResponseBody
    @ExceptionHandler(value = ApiException.class)
    public ApiResult<Void> handleException(Exception ex){
        return ApiResult.failed(ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ApiResult<Void> handleValidException(MethodArgumentNotValidException validException){
        BindingResult bindingResult = validException.getBindingResult();
        String message = null;
        if (bindingResult.hasErrors()){
            FieldError fieldError = bindingResult.getFieldError();
            message = fieldError.getField() + fieldError.getDefaultMessage();
        }
        return ApiResult.validFailed(message);
    }

    @ResponseBody
    @ExceptionHandler(value = BindException.class)
    public ApiResult<Void> handleValidException(BindException bindException){
        BindingResult bindingResult = bindException.getBindingResult();
        String message = null;
        if (bindingResult.hasErrors()){
            FieldError fieldError = bindingResult.getFieldError();
            message = fieldError.getField() + fieldError.getDefaultMessage();
        }
        return ApiResult.validFailed(message);
    }

}
