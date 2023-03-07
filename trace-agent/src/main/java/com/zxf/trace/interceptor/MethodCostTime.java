package com.zxf.trace.interceptor;

import com.zxf.method.trace.util.TraceFatch;
import com.zxf.trace.constants.Constants;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import org.slf4j.MDC;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

/**
 * @author zhuxiaofeng
 * @date 2023/2/15
 */
@Slf4j
public class MethodCostTime {

//    private final static ThreadLocal<Integer> LOCAL = new ThreadLocal<>();

    @RuntimeType
    public static Object intercept(@Origin Method method, @SuperCall Callable<?> callable) throws Exception {
//        long start = System.currentTimeMillis();
        try {
            if (!TraceFatch.isExistTraceId()) {
                MDC.put(Constants.TRACE_ID, TraceFatch.getTraceId());
            }
//            log.debug( "{} is start", method);
            // 原有函数执行
            return callable.call();
        } finally{
//            log.debug("{} is end, cost: {} ms", method, (System.currentTimeMillis() - start));
            // 由最外层线程推掉traceId
//            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
//            StackTraceElement stackTraceElement = stackTrace[stackTrace.length-1];
//            String className = stackTraceElement.getClassName();
//            String methodName = stackTraceElement.getMethodName();
//            if (methodName.equals(method.getName()) && className.equals(method.getDeclaringClass().getName())){
                MDC.remove(Constants.TRACE_ID);
//            }
        }
    }

}
