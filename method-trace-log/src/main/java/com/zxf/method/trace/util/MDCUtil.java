package com.zxf.method.trace.util;

import org.slf4j.MDC;
import org.springframework.util.CollectionUtils;

import java.util.Map;
import java.util.concurrent.Callable;

/**
 * @author zhuxiaofeng
 * @date 2022/11/15
 */
public class MDCUtil {

    public static <T> Callable<T> wrap(final Callable<T> callable, final Map<String, String> context) {
        return () -> {
            if (CollectionUtils.isEmpty(context)) {
                MDC.clear();
            } else {
                MDC.setContextMap(context);
            }
            try {
                return callable.call();
            } finally {
                //一定会执行，return的结果会暂时保存到，线程栈本地变量中, 但是也会被finally的return的值覆盖
                MDC.clear();
            }
        };
    }

}