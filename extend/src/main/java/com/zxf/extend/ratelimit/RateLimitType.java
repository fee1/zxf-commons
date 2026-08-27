package com.zxf.extend.ratelimit;

/**
 * 限流类型
 */
public enum RateLimitType {

    /**
     * Guava 本地限流
     */
    GUAVA,

    /**
     * Redis 共享限流
     */
    REDIS
}
