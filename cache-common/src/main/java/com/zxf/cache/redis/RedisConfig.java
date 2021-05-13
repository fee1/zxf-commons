package com.zxf.cache.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * redis配置类
 * @author 朱晓峰
 */
@Configuration
@ConditionalOnProperty(value = "spring.cache.redis.enable", matchIfMissing = true)
@ConfigurationProperties(prefix = "spring.cache.redis")
@Slf4j
public class RedisConfig {

    /**
     * 单机 ip
     */
    @Value("${host:}")
    private String host;

    /**
     * 集群hosts
     */
    @Value("${hosts:}")
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
    @Value("${maxTotal:8}")
    private int maxTotal;

    /**
     * 连接池最小空闲数
     */
    @Value("${maxIdle:1}")
    private int maxIdle;

    /**
     * 连接池最大等待阻塞时间,负数无限制
     */
    @Value("${maxWait:5000}")
    private long maxWait;

    /**
     *
     */
    @Value("${maxRedirects:}")
    private int maxRedirects;

}
