package com.zxf.extend.ratelimit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Guava 本地限流策略
 */
@Slf4j
@Component
public class GuavaRateLimiterStrategy implements RateLimiterStrategy {

    /**
     * 使用 Guava 的 LoadingCache 来存储不同 key 的 RateLimiter
     * 设置缓存过期时间为 1 小时，最大缓存数量为 1000 个
     */
    private final LoadingCache<String, RateLimiter> rateLimiterCache = CacheBuilder.newBuilder()
            .expireAfterAccess(1, TimeUnit.HOURS)
            .maximumSize(1000)
            .build(new CacheLoader<String, RateLimiter>() {
                @Override
                public RateLimiter load(String key) {
                    return RateLimiter.create(10); // 默认限流 QPS 为 10
                }
            });

    @Override
    public RateLimitType getType() {
        return RateLimitType.GUAVA;
    }

    @Override
    public boolean tryAcquire(String key, double qps) {
        try {
            RateLimiter rateLimiter = rateLimiterCache.get(key);
            // 如果需要更新 QPS 设置
            if (Math.abs(rateLimiter.getRate() - qps) > 0.0001) {
                rateLimiter.setRate(qps);
            }
            // 尝试获取令牌，立即返回结果，不等待
            return rateLimiter.tryAcquire();
        } catch (ExecutionException e) {
            log.error("获取 RateLimiter 失败", e);
            return false;
        }
    }

    /**
     * 清除特定 key 的 RateLimiter
     *
     * @param key 限流 key
     */
    public void invalidateKey(String key) {
        rateLimiterCache.invalidate(key);
    }

    /**
     * 清除所有 RateLimiter
     */
    public void invalidateAll() {
        rateLimiterCache.invalidateAll();
    }
}
