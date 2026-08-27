package com.zxf.extend.ratelimit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 限流服务
 * <p>
 * 根据限流类型分发到对应的限流策略实现。
 */
@Slf4j
@Service
public class RateLimiterService {

    private final Map<RateLimitType, RateLimiterStrategy> strategyMap;

    public RateLimiterService(List<RateLimiterStrategy> strategies) {
        this.strategyMap = strategies.stream()
                .collect(Collectors.toMap(
                        RateLimiterStrategy::getType,
                        Function.identity(),
                        (a, b) -> {
                            throw new IllegalStateException("Duplicate rate limiter strategy: " + a.getType());
                        },
                        () -> new EnumMap<>(RateLimitType.class)
                ));
    }

    /**
     * 使用默认的 Guava 本地限流尝试获取令牌
     *
     * @param key 限流 key
     * @param qps 每秒查询率
     * @return 是否获取到令牌
     */
    public boolean tryAcquire(String key, double qps) {
        return tryAcquire(key, qps, RateLimitType.GUAVA);
    }

    /**
     * 根据指定限流类型尝试获取令牌
     *
     * @param key  限流 key
     * @param qps  每秒查询率
     * @param type 限流类型
     * @return 是否获取到令牌
     */
    public boolean tryAcquire(String key, double qps, RateLimitType type) {
        RateLimiterStrategy strategy = strategyMap.get(type);
        Assert.notNull(strategy, "No rate limiter strategy for type: " + type);
        return strategy.tryAcquire(key, qps);
    }
}
