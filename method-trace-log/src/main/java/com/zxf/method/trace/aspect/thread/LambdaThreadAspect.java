//package com.zxf.method.trace.aspect.thread;
//
//import com.kc.evo.trace.constants.Constants;
//import com.kc.evo.trace.util.TraceFatch;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.slf4j.MDC;
//
///**
// * 影响runable线程的正常运行，暂时不加入
// *
// * @author zhuxiaofeng
// * @date 2022/11/10
// */
//@Aspect
//public class LambdaThreadAspect {
//
//
////    @Pointcut("execution(void *..lambda$*(..))")
//    public void pointCut(){}
//
//    @Around(value = "pointCut()")
//    public void doAroud(ProceedingJoinPoint joinPoint) throws Throwable {
//        MDC.put(Constants.TRACE_ID, TraceFatch.getTraceId());
//        joinPoint.proceed();
//        MDC.remove(Constants.TRACE_ID);
//    }
//
//}
