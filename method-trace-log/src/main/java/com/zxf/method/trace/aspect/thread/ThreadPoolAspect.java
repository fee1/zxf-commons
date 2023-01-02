//package com.zxf.method.trace.aspect.thread;
//
//import com.kc.evo.util.MDCUtil;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.slf4j.MDC;
//
//import java.util.Map;
//import java.util.concurrent.Callable;
//
///**
// * @author zhuxiaofeng
// * @date 2022/11/15
// */
//@Aspect
//public class ThreadPoolAspect {
//
//    @Pointcut("execution(public * (java.util.concurrent.AbstractExecutorService+).submit(java.util.concurrent.Callable)))")
//    public void pointCut(){}
//
//    @Around(value = "pointCut()")
//    public Object doAroud(ProceedingJoinPoint joinPoint) throws Throwable {
//        Object[] args = joinPoint.getArgs();
//        Callable callable = (Callable) args[0];
//        Map<String, String> copyOfContextMap = MDC.getCopyOfContextMap();
//        Callable wrapCallable = MDCUtil.wrap(callable, copyOfContextMap);
//        return joinPoint.proceed(new Callable[]{wrapCallable});
//    }
//
//}
