package com.zxf.extend.ratelimit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 接口限流注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface RateLimit {
    
    /**
     * 限流的QPS(每秒查询率)
     * @return QPS
     */
    double qps() default 10;
    
    /**
     * 限流的key，为空则使用方法完整路径
     * @return 限流key
     */
    String key() default "";
    
    /**
     * 超过限流时的提示信息
     * @return 提示信息
     */
    String message() default "请求过于频繁，请稍后再试";
} 