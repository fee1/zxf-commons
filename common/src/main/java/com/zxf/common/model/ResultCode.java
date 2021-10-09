package com.zxf.common.model;

/**
 * 返回码
 *
 * @author zhuxiaofeng
 * @date 2021/9/4
 */
public enum ResultCode implements IErrorCode {
    /**
     * 1-8开头标识不同服务之间的异常code响应
     */
    //通用成功响应
    SUCCESS("200000", "成功"),
    //通用失败响应
    FAIL("500000", "失败"),
    //参数校验失败
    PARAM_VALID_FAIL("500001", "参数校验失败");


    private final String code;

    private final String message;

    private ResultCode(String code, String message){
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
