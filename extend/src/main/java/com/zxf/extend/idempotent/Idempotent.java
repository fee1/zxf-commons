package com.zxf.extend.idempotent;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * 接口幂等性注解
 * 用于标记需要保证幂等性的接口方法
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Idempotent {
    
    /**
     * 幂等性Key的前缀，用于在Redis中区分不同业务
     * @return 前缀
     */
    String prefix() default "idempotent";
    
    /**
     * 幂等性Key的组成部分，支持SpEL表达式
     * 如果为空，则使用请求参数生成唯一标识
     * @return key部分
     */
    String[] keys() default {};
    
    /**
     * 幂等性过期时间，超过该时间后允许重复请求
     * @return 过期时间
     */
    long expireTime() default 60;
    
    /**
     * 过期时间单位
     * @return 时间单位
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;
    
    /**
     * 幂等性校验失败时的提示信息
     * @return 提示信息
     */
    String message() default "请求正在处理中，请勿重复提交";
} 