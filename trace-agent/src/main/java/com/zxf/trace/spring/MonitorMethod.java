//package com.zxf.trace.spring;
//
//import lombok.extern.slf4j.Slf4j;
//import net.bytebuddy.ByteBuddy;
//import net.bytebuddy.implementation.MethodDelegation;
//import net.bytebuddy.implementation.bind.annotation.AllArguments;
//import net.bytebuddy.implementation.bind.annotation.Origin;
//import net.bytebuddy.implementation.bind.annotation.RuntimeType;
//import net.bytebuddy.implementation.bind.annotation.This;
//import net.bytebuddy.matcher.ElementMatchers;
//
//
//import java.lang.reflect.Method;
//
///**
// * @author zhuxiaofeng
// * @date 2022/12/4
// */
//@Slf4j
//public class MonitorMethod {
//
//    public static <T> T interceptTime(Class<T> clazz) throws IllegalAccessException, InstantiationException {
//        return new ByteBuddy()
//                .subclass(clazz)
//                .method(ElementMatchers.any())
//                .intercept(MethodDelegation.to(TimeInterceptor.class))
//                .make()
//                .load(clazz.getClassLoader())
//                .getLoaded()
//                .newInstance();
//    }
//
//    public static class TimeInterceptor {
//        @RuntimeType
//        public static Object intercept(@This Object obj, @Origin Method method, @AllArguments Object[] args) {
//            long start = System.currentTimeMillis();
//            Object result = null;
//            try {
//                result = method.invoke(obj, args);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            long end = System.currentTimeMillis();
//            System.out.println(method.getName() + " run time: " + (end - start));
//            return result;
//        }
//    }
//
//}
