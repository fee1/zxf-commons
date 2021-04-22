package com.zxf.elastic.config;

import lombok.Data;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * ES基本配置信息类
 * @author zxf
 */
@Data
public class EsConfig {

    /**
     * Elasticsearch uri
     */
    private List<String> uris = Collections.singletonList("http://localhost:9200");

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 是否支持多线程
     */
    private boolean multiThreaded = true;

    /**
     * 链接超时时间 60s
     */
    private Duration connectionTimeout = Duration.ofSeconds(60L);

    /**
     * 读取时间 300s
     */
    private Duration readTimeout = Duration.ofSeconds(300L);

    public EsConfig(String uris){
        this.uris = Arrays.asList(uris.split(","));
    }

    public EsConfig(){
    }

}
