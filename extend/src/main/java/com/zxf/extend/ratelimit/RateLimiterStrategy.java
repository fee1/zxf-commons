package com.zxf.extend.ratelimit;

/**
 * 限流策略接口
 */
public interface RateLimiterStrategy {

    /**
     * 获取限流类型
     *
     * @return 限流类型
     */
    RateLimitType getType();

    /**
     * 尝试获取令牌
     *
     * @param key 限流 key
     * @param qps 每秒查询率
     * @return 是否获取到令牌
     */
    boolean tryAcquire(String key, double qps);
}
