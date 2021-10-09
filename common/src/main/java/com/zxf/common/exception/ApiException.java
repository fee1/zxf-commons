package com.zxf.common.exception;

import com.zxf.common.model.IErrorCode;

/**
 * Api异常类
 *
 * @author zhuxiaofeng
 * @date 2021/9/30
 */
public class ApiException extends RuntimeException{

    private IErrorCode errorCode;

    public ApiException(IErrorCode errorCode){
        this.errorCode = errorCode;
    }

    public ApiException(String message){
        super(message);
    }

    public ApiException(String message, Throwable cause){
        super(message, cause);
    }

    public IErrorCode getErrorCode() {
        return this.errorCode;
    }
}
