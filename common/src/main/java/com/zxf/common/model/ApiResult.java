package com.zxf.common.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * api result 接口同一返回类
 *
 * @author zhuxiaofeng
 * @date 2021/9/4
 */
@Getter
@Setter
@NoArgsConstructor
public class ApiResult<T> {

    @ApiModelProperty("数据")
    private T data;

    @ApiModelProperty("提示信息")
    private String message;

    @ApiModelProperty("返回码")
    private String code;

    public ApiResult(String code, String message) {
        super();
        this.code = code;
        this.message = message;
    }

    public ApiResult(String code, String message, T data) {
        super();
        this.data = data;
        this.code = code;
        this.message = message;
    }

    /**
     * 成功
     *
     * @return ApiResult
     */
    public static <T> ApiResult<T> ok(){
        return new ApiResult<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage());
    }

    /**
     * 成功
     *
     * @return ApiResult
     */
    public static <T> ApiResult<T> ok(T data){
        return new ApiResult<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
    }

    /**
     * 失败
     *
     * @return ApiResult
     */
    public static <T> ApiResult<T> failed(){
        return new ApiResult<>(ResultCode.FAIL.getCode(), ResultCode.FAIL.getMessage());
    }

    /**
     * 失败
     *
     * @return ApiResult
     */
    public static <T> ApiResult<T> failed(String message){
        return new ApiResult<>(ResultCode.FAIL.getCode(), message);
    }

    /**
     * 失败
     *
     * @return ApiResult
     */
    public static <T> ApiResult<T> failed(IErrorCode errorCode){
        return new ApiResult<>(errorCode.getCode(), errorCode.getMessage());
    }

    /**
     * 失败
     *
     * @return ApiResult
     */
    public static <T> ApiResult<T> failed(String message ,IErrorCode errorCode, T data){
        return new ApiResult<>(errorCode.getCode(), message, data);
    }

    /**
     * 参数校验失败
     * @param message 失败信息
     * @return ApiResult
     */
    public static <T> ApiResult<T> validFailed(String message){
        return failed(message, ResultCode.PARAM_VALID_FAIL, null);
    }
}
