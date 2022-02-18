package com.zxf.cache.redis;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author zhuxiaofeng
 * @date 2022/2/10
 */
@Data
//@ConfigurationProperties(prefix = "cache.auto.config.redis")
public class RedisProperties {

    /**
     * hosts
     */
    @Value("${hosts:127.0.0.1:6379}")
    private String[] hosts;

    /**
     * 连接超时时间,5s
     */
    @Value("${connect-timeout:5000}")
    private long connectTimeout;

    /**
     * 命令超时时间10s
     */
    @Value("${command-timeout:10000}")
    private long commandTimeout;

    /**
     * 密码
     */
    @Value("${password:}")
    private String password;

    /**
     * 缓存超时时间
     */
    @Value("${timeout:86400}")
    private int timeout;

    /**
     * 连接池最大连接数，负数无限制
     */
    @Value("${max-total:8}")
    private int maxTotal;

    /**
     * 连接池最大空闲连接
     */
    @Value("${max-idle:8}")
    private int maxIdle;

    /**
     * 连接池最小空闲连接
     */
    @Value("${min-idle:1}")
    private int minIdle;

    /**
     * 连接池最大等待阻塞时间,负数无限制
     */
    @Value("${max-wait:5000}")
    private long maxWait;

    /**
     * 集群配置，最大重定向。第一台挂了，连第二台，第二台挂了连第三台
     */
    @Value("${max-redirects:3}")
    private int maxRedirects;

    /**
     * key前缀
     */
    @Value("${prefix-key:}")
    private String prefixKey;

}
