package com.zxf.extend.idempotent;

/**
 * 幂等性校验异常
 * 当请求被识别为重复请求时抛出此异常
 */
public class IdempotentException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * 默认构造函数
     */
    public IdempotentException() {
        super("请求正在处理中，请勿重复提交");
    }

    /**
     * 构造函数
     *
     * @param message 错误信息
     */
    public IdempotentException(String message) {
        super(message);
    }
} 