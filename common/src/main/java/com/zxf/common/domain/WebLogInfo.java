package com.zxf.common.domain;

import lombok.Data;

/**
 * log 日志对象
 *
 * @author zhuxiaofeng
 * @date 2021/12/16
 */
@Data
public class WebLogInfo {

    /**
     * ip
     */
    private String remoteUser;

    /**
     * 请求方式
     */
    private String method;

    /**
     * requestURI
     */
    private String requestURI;

    /**
     * requestURL
     */
    private String requestURL;

    /**
     * 请求入参
     */
    private Object[] requestBody;

    /**
     * 请求结果
     */
    private Object responseBody;

    /**
     * 开始时间
     */
    private long startTime;

    /**
     * 结束时间
     */
    private long endTime;

    /**
     * 花费时间
     */
    private long spendTime;

}

