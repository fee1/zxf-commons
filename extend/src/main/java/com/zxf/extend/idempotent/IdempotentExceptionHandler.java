package com.zxf.extend.idempotent;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * 幂等性异常处理器
 * 捕获幂等性异常，并返回友好的错误信息
 */
@RestControllerAdvice
public class IdempotentExceptionHandler {

    /**
     * 处理幂等性异常
     *
     * @param e 幂等性异常
     * @return 错误响应
     */
    @ExceptionHandler(IdempotentException.class)
    @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
    public Map<String, Object> handleIdempotentException(IdempotentException e) {
        Map<String, Object> result = new HashMap<>(2);
        result.put("code", HttpStatus.TOO_MANY_REQUESTS.value());
        result.put("message", e.getMessage());
        return result;
    }
} 