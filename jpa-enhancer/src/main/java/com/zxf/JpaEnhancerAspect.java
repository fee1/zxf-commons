//package com.zxf;
//
//import com.alibaba.fastjson2.JSON;
//import lombok.SneakyThrows;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.springframework.stereotype.Component;
//
///**
// * @author zhuxiaofeng
// * @date 2024/11/1
// */
//@Aspect
//@Component
//public class JpaEnhancerAspect {
//
//    @Pointcut("@annotation(jpaEnhancer)")
//    public void pointCut(JpaEnhancer jpaEnhancer){}
//
//    @SneakyThrows
//    @Around("pointCut(jpaEnhancer)")
//    public Object around(ProceedingJoinPoint joinPoint, JpaEnhancer jpaEnhancer){
//        Object proceed = joinPoint.proceed();
//        Class<?> value = jpaEnhancer.value();
//        return JSON.parseObject(JSON.toJSONString(proceed)).toJavaObject(value);
//    }
//
//}
