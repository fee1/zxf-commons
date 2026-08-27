package com.zxf.extend.ratelimit;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

/**
 * Redis 共享限流策略
 * <p>
 * 基于 Redisson 的 {@link RRateLimiter} 实现跨 JVM 实例的共享限流。
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class RedisRateLimiterStrategy implements RateLimiterStrategy {

    private static final String RATE_LIMIT_KEY_PREFIX = "rate:limit:";

    private final RedissonClient redissonClient;

    @Override
    public RateLimitType getType() {
        return RateLimitType.REDIS;
    }

    @Override
    public boolean tryAcquire(String key, double qps) {
        try {
            RRateLimiter rateLimiter = redissonClient.getRateLimiter(RATE_LIMIT_KEY_PREFIX + key);
            // 按毫秒精度表达 QPS，使 double 类型也能较精确生效
            long rate = Math.round(qps * 1000);
            rateLimiter.setRate(RateType.OVERALL, rate, 1000, RateIntervalUnit.MILLISECONDS);
            return rateLimiter.tryAcquire();
        } catch (Exception e) {
            log.error("Redis 限流执行失败, key={}, qps={}", key, qps, e);
            // Redis 异常时默认放行，避免 Redis 故障拖垮全站
            return true;
        }
    }
}
