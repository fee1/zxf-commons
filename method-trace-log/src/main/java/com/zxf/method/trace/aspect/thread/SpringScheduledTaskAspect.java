package com.zxf.method.trace.aspect.thread;

import com.zxf.method.trace.constants.Constants;
import com.zxf.method.trace.util.TraceFatch;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;

/**
 * Spring定时器线程
 *
 * @author zhuxiaofeng
 * @date 2022/12/22
 */
@Aspect
public class SpringScheduledTaskAspect {

    @Pointcut("@annotation(org.springframework.scheduling.annotation.Scheduled)")
    public void cut() {
    }

    @Around("cut()")
    public Object around(ProceedingJoinPoint jp) throws Throwable {
        Object proceed = null;
        if (TraceFatch.isExistTraceId()) {
            MDC.put(Constants.TRACE_ID, TraceFatch.getTraceId());
            proceed = jp.proceed();
            MDC.remove(Constants.TRACE_ID);
        }else {
            proceed = jp.proceed();
        }
        return proceed;
    }

}
