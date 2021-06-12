package com.zxf.common.exception;

/**
 * 序列化异常
 * @author 朱晓峰
 */
public class SerializationException extends RuntimeException {

    public SerializationException(String message){
        super(message);
    }

    public SerializationException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
