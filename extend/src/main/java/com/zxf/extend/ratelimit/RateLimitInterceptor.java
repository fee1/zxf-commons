package com.zxf.extend.ratelimit;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 限流拦截器
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class RateLimitInterceptor implements HandlerInterceptor {

    private final RateLimiterService rateLimiterService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 如果不是HandlerMethod类型，直接放行
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        // 获取方法上的RateLimit注解
        RateLimit rateLimit = method.getAnnotation(RateLimit.class);
        if (rateLimit == null) {
            // 没有限流注解，直接放行
            return true;
        }

        // 获取限流key和QPS
        String key = getRateLimitKey(method, rateLimit);
        double qps = rateLimit.qps();

        // 尝试获取令牌
        boolean allowed = rateLimiterService.tryAcquire(key, qps);
        if (!allowed) {
            log.warn("接口 [{}] 被限流，当前QPS: {}", key, qps);
            // 抛出限流异常，由全局异常处理器处理
            throw new RateLimitException(rateLimit.message());
        }

        return true;
    }

    /**
     * 获取限流的key
     * 如果注解中设置了key，则使用注解中的key；否则使用类名+方法名作为key
     *
     * @param method 方法
     * @param rateLimit 注解
     * @return 限流key
     */
    private String getRateLimitKey(Method method, RateLimit rateLimit) {
        String key = rateLimit.key();
        if (key.isEmpty()) {
            key = method.getDeclaringClass().getName() + "." + method.getName();
        }
        return key;
    }
} 