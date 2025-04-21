package com.zxf.extend.ratelimit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * 限流异常处理器
 */
@Slf4j
@RestControllerAdvice
public class RateLimitExceptionHandler {

    /**
     * 处理限流异常
     *
     * @param e 限流异常
     * @return 响应结果
     */
    @ExceptionHandler(RateLimitException.class)
    public ResponseEntity<Map<String, Object>> handleRateLimitException(RateLimitException e) {
        log.warn("接口限流: {}", e.getMessage());

        Map<String, Object> result = new HashMap<>(2);
        result.put("code", 429);
        result.put("message", e.getMessage());

        return new ResponseEntity<>(result, HttpStatus.TOO_MANY_REQUESTS);
    }
} 