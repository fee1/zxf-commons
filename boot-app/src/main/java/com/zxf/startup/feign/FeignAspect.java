package com.zxf.startup.feign;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author zhuxiaofeng
 * @date 2023/1/15
 */
@Aspect
@Component
@Slf4j
public abstract class FeignAspect {

    @Pointcut("@annotation(com.zxf.startup.feign.FeignAspectInterceptor)")
    public void pointCut(){}

    @Around("pointCut()")
    public Object afterReturning(ProceedingJoinPoint joinPoint) throws Throwable {
        reqBefore(joinPoint);
        Object result = joinPoint.proceed();
        respAfter(result);
        return result;
    }

    /**
     * 请求前处理
     * @param joinPoint
     */
    public abstract void reqBefore(ProceedingJoinPoint joinPoint);

    /**
     * 请求完成后处理
     * @param result
     */
    public abstract void respAfter(Object result);

}
