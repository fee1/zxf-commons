package com.zxf.extend.ratelimit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * 限流服务
 */
@Slf4j
@Service
public class RateLimiterService {

    /**
     * 使用Guava的LoadingCache来存储不同key的RateLimiter
     * 设置缓存过期时间为1小时，最大缓存数量为1000个
     */
    private final LoadingCache<String, RateLimiter> rateLimiterCache = CacheBuilder.newBuilder()
            .expireAfterAccess(1, TimeUnit.HOURS)
            .maximumSize(1000)
            .build(new CacheLoader<String, RateLimiter>() {
                @Override
                public RateLimiter load(String key) {
                    return RateLimiter.create(10); // 默认限流QPS为10
                }
            });

    /**
     * 获取指定key的RateLimiter并尝试获取令牌
     *
     * @param key 限流key
     * @param qps 每秒查询率
     * @return 是否获取到令牌
     */
    public boolean tryAcquire(String key, double qps) {
        try {
            RateLimiter rateLimiter = rateLimiterCache.get(key);
            // 如果需要更新QPS设置
            if (Math.abs(rateLimiter.getRate() - qps) > 0.0001) {
                rateLimiter.setRate(qps);
            }
            // 尝试获取令牌，立即返回结果，不等待
            return rateLimiter.tryAcquire();
        } catch (ExecutionException e) {
            log.error("获取RateLimiter失败", e);
            return false;
        }
    }

    /**
     * 清除特定key的RateLimiter
     *
     * @param key 限流key
     */
    public void invalidateKey(String key) {
        rateLimiterCache.invalidate(key);
    }

    /**
     * 清除所有RateLimiter
     */
    public void invalidateAll() {
        rateLimiterCache.invalidateAll();
    }
} 