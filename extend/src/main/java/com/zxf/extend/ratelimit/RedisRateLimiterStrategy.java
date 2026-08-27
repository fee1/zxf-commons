package com.zxf.extend.ratelimit;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

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

    /**
     * 缓存每个 key 最近一次配置的速率，避免每次请求都调用 setRate 重置限流器状态
     */
    private final ConcurrentHashMap<String, RateConfig> configuredRateMap = new ConcurrentHashMap<>();

    @Override
    public RateLimitType getType() {
        return RateLimitType.REDIS;
    }

    @Override
    public boolean tryAcquire(String key, double qps) {
        try {
            RRateLimiter rateLimiter = redissonClient.getRateLimiter(RATE_LIMIT_KEY_PREFIX + key);
            RateConfig newConfig = resolveRateConfig(qps);
            RateConfig oldConfig = configuredRateMap.get(key);
            if (oldConfig == null || !oldConfig.equals(newConfig)) {
                rateLimiter.setRate(RateType.OVERALL, newConfig.getRate(), newConfig.getInterval(), newConfig.getUnit());
                configuredRateMap.put(key, newConfig);
            }
            return rateLimiter.tryAcquire();
        } catch (Exception e) {
            log.error("Redis 限流执行失败, key={}, qps={}", key, qps, e);
            // Redis 异常时默认放行，避免 Redis 故障拖垮全站
            return true;
        }
    }

    /**
     * 将 QPS 转换为 Redisson 的速率配置
     * <ul>
     *     <li>整数 QPS：rate=qps, interval=1s，与 Guava RateLimiter 的突发行为一致</li>
     *     <li>小数 QPS：rate=1, interval=(1000/qps)ms，保证平均速率正确且突发为 1</li>
     * </ul>
     *
     * @param qps 每秒查询率
     * @return Redisson 速率配置
     */
    private RateConfig resolveRateConfig(double qps) {
        if (qps <= 0) {
            qps = 1;
        }
        if (qps == Math.rint(qps)) {
            return new RateConfig((long) qps, 1, RateIntervalUnit.SECONDS);
        }
        long intervalMs = Math.round(1000.0 / qps);
        if (intervalMs < 1) {
            // 极大 QPS 时退化为每毫秒 rate 个
            return new RateConfig(Math.max(1, Math.round(qps / 1000.0)), 1, RateIntervalUnit.MILLISECONDS);
        }
        return new RateConfig(1, intervalMs, RateIntervalUnit.MILLISECONDS);
    }

    /**
     * Redisson 速率配置值对象
     */
    private static class RateConfig {
        private final long rate;
        private final long interval;
        private final RateIntervalUnit unit;

        RateConfig(long rate, long interval, RateIntervalUnit unit) {
            this.rate = rate;
            this.interval = interval;
            this.unit = unit;
        }

        public long getRate() {
            return rate;
        }

        public long getInterval() {
            return interval;
        }

        public RateIntervalUnit getUnit() {
            return unit;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof RateConfig)) {
                return false;
            }
            RateConfig that = (RateConfig) o;
            return rate == that.rate && interval == that.interval && unit == that.unit;
        }

        @Override
        public int hashCode() {
            int result = Long.hashCode(rate);
            result = 31 * result + Long.hashCode(interval);
            result = 31 * result + (unit != null ? unit.hashCode() : 0);
            return result;
        }
    }
}
