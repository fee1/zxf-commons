package com.zxf.cache.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * redis配置
 * @author 朱晓峰
 */
@Component
@ConfigurationProperties(prefix = "cache.redis")
public class RedisConfigProperties {

    /**
     * redis 节点
     * 多个使用','分割
     *
     * 例：localhost:6379,127.0.0.1:6379
     */
    @Value("${cache.redis.redis-node:localhost:6379}")
    private String redisNode;

    @Value("${cache.redis.password:}")
    private String password;


}
