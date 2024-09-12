package com.zxf.trace.interceptor;

import com.zxf.trace.constants.Constants;
import com.zxf.trace.util.TraceFatch;
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
            // 切入run方法，没有意义
//            log.debug( "{} is start", method.getName());
//            System.out.println( method.getName()+" is start");
            // 原有函数执行
            return callable.call();
        } finally{
            // 切入run方法，没有意义
//            log.debug("{} is end, cost: {} ms", method.getName(), (System.currentTimeMillis() - start));
//            System.out.println( method.getName()+" is end, cost: "+(System.currentTimeMillis() - start));
            // 由最外层run推掉traceId(run 嵌套 run)
            int interceptorTimes = 0;
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            String traceMethodName = stackTrace[1].getMethodName();
            String traceClassName = stackTrace[1].getClassName();
            for (StackTraceElement stackTraceElement : stackTrace) {
                String className = stackTraceElement.getClassName();
                String methodName = stackTraceElement.getMethodName();
                if (methodName.equals(traceMethodName) && className.equals(traceClassName)){
                    interceptorTimes ++;
                }
            }
            if (interceptorTimes == 1){
                MDC.remove(Constants.TRACE_ID);
            }
        }
    }

}
