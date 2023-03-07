//package com.zxf.trace.interceptor;
//
//import lombok.extern.slf4j.Slf4j;
//import net.bytebuddy.asm.Advice;
//
///**
// * @author zhuxiaofeng
// * @date 2023/2/15
// */
//@Slf4j
//public class MethodInterceptor {
//
//    @Advice.OnMethodEnter
//    public static long enter(@Advice.Origin("#t") String methodName) {
////        log.debug("Method " + methodName + " enters at " + System.currentTimeMillis());
//        System.out.println("Method " + methodName + " enters at " + System.currentTimeMillis());
//        return System.currentTimeMillis();
//    }
//
//    @Advice.OnMethodExit
//    public static void exit(@Advice.Origin("#t") String methodName, @Advice.Enter long startTime) {
//        long endTime = System.currentTimeMillis();
////        log.debug("Method " + methodName + " exits at " + endTime + ", cost " + (endTime - startTime) + " ms");
//        System.out.println("Method " + methodName + " exits at " + endTime + ", cost " + (endTime - startTime) + " ms");
//    }
//
//}
