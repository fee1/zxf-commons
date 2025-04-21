package com.zxf.extend.ratelimit;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 限流示例控制器
 */
@RestController
@RequestMapping("/api/rate-limit")
public class RateLimitDemo {

    /**
     * 默认限流配置，QPS=10
     */
    @RateLimit
    @GetMapping("/default")
    public Map<String, Object> defaultLimit() {
        Map<String, Object> result = new HashMap<>(2);
        result.put("message", "默认限流配置，QPS=10");
        result.put("time", LocalDateTime.now().toString());
        return result;
    }

    /**
     * 自定义QPS限流配置，QPS=5
     */
    @RateLimit(qps = 5)
    @GetMapping("/custom-qps")
    public Map<String, Object> customQpsLimit() {
        Map<String, Object> result = new HashMap<>(2);
        result.put("message", "自定义QPS限流配置，QPS=5");
        result.put("time", LocalDateTime.now().toString());
        return result;
    }

    /**
     * 自定义key限流配置
     * 相同key的接口共享限流配额
     */
    @RateLimit(key = "shared-limit", qps = 2)
    @GetMapping("/shared-1")
    public Map<String, Object> sharedLimit1() {
        Map<String, Object> result = new HashMap<>(2);
        result.put("message", "共享限流key: shared-limit, QPS=2");
        result.put("time", LocalDateTime.now().toString());
        return result;
    }

    /**
     * 共享限流key的另一个接口
     */
    @RateLimit(key = "shared-limit", qps = 2)
    @GetMapping("/shared-2")
    public Map<String, Object> sharedLimit2() {
        Map<String, Object> result = new HashMap<>(2);
        result.put("message", "共享限流key: shared-limit, QPS=2");
        result.put("time", LocalDateTime.now().toString());
        return result;
    }

    /**
     * 自定义错误信息
     */
    @RateLimit(qps = 1, message = "操作太频繁了，请休息一下再试")
    @GetMapping("/custom-message")
    public Map<String, Object> customMessage() {
        Map<String, Object> result = new HashMap<>(2);
        result.put("message", "自定义错误信息，QPS=1");
        result.put("time", LocalDateTime.now().toString());
        return result;
    }

    /**
     * 无限流接口
     */
    @GetMapping("/no-limit")
    public Map<String, Object> noLimit() {
        Map<String, Object> result = new HashMap<>(2);
        result.put("message", "无限流接口");
        result.put("time", LocalDateTime.now().toString());
        return result;
    }
} 