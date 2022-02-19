package com.zxf.elastic.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * ES基本配置信息类
 * @author zxf
 */
@Data
@ConfigurationProperties(prefix = "elastic.jest")
public class JestEsProperties {

    /**
     * Elasticsearch uri
     */
    @Value("${uris:http://localhost:9200}")
    private List<String> uris;

    /**
     * 用户名
     */
    @Value("${username:}")
    private String username;

    /**
     * 密码
     */
    @Value("${password:}")
    private String password;

    /**
     * 是否支持多线程
     */
    @Value("${multiThreaded:true}")
    private boolean multiThreaded;

    /**
     * 链接超时时间 60s
     */
    private Duration connectionTimeout = Duration.ofSeconds(60L);

    /**
     * 读取时间 300s
     */
    private Duration readTimeout = Duration.ofSeconds(300L);

    public JestEsProperties(String uris){
        this.uris = Arrays.asList(uris.split(","));
    }

    public JestEsProperties(){
    }

}
