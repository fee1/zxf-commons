package com.zxf.common.utils.model;

import lombok.Data;

/**
 * @author zhuxiaofeng
 * @date 2022/11/30
 */
@Data
public class MethodExecuteMessageRequestDTO {

    /**
     * 链路id
     */
    private String traceId;

    /**
     * 类全路径
     */
    private String classFullPath;

    /**
     * 方法
     */
    private String method;

    /**
     * 入参
     */
    private String[] args;

    /**
     * 出参
     */
    private Object returnObj;

    /**
     * 花费时间
     */
    private Long costTime;

    /**
     * 记录时间
     */
    private Long recordTime;

}
