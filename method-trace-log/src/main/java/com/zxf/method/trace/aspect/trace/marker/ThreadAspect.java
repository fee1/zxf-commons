package com.zxf.method.trace.aspect.trace.marker;

import com.zxf.method.trace.constants.Constants;
import com.zxf.method.trace.util.TraceFatch;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;

/**
 * note：会对反复运行（while(true)）的线程造成tracceId多次复用
 *
 * @author zhuxiaofeng
 * @date 2022/10/12
 */
@Aspect
@Slf4j
public class ThreadAspect {

    @Pointcut("execution(public * (java.lang.Runnable+).run())")
    public void pointCut(){}

    @Pointcut("!within(org.springframework.scheduling..*)")
    public void notPointCut(){}

    @Around(value = "pointCut() && notPointCut()")
    public void doAroud(ProceedingJoinPoint joinPoint) throws Throwable {
        //不要影响lambda的切面，两者同时切入嵌套runable - runable，会提前移除traceId
        if (TraceFatch.isExistTraceId()) {
            MDC.put(Constants.TRACE_ID, TraceFatch.getTraceId());
            joinPoint.proceed();
            MDC.remove(Constants.TRACE_ID);
        }
    }

}
