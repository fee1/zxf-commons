package com.zxf.method.trace.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author zhuxiaofeng
 * @date 2022/12/6
 */
@Slf4j
public abstract class AbstractMethod {

    @Pointcut("execution(* com.zxf..*.*(..))")
    public void mustPointCutMethod(){}

    @Pointcut("!execution(* com.zxf..*.lambda*(..))")
    public void notPointCutLambdaMethod(){}

    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        String fullClassName =null;
        try {
            fullClassName = joinPoint.getTarget().getClass().getName();
        }catch (Exception e){
//            log.debug("SpringBeanMethodAspect error getSourceLocation:{}, getStaticPart: {}, getKind: {}, getTarget:{}, getThis:{}",
//                    joinPoint.getSourceLocation(), joinPoint.getStaticPart(), joinPoint.getKind(), joinPoint.getTarget(), joinPoint.getThis());
            //lambda 表达式使用时如果有需要时使用
//            fullClassName = joinPoint.getStaticPart().getSourceLocation().getWithinType().getName();
            return joinPoint.proceed();
        }
        String methodName = null;
        try {
            methodName = joinPoint.getSignature().getName();
        }catch (Exception e){
        }
        //后续改成与新的服务记录
        log.debug("{}.{}, is start", fullClassName, methodName);
        long start = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        log.debug("{}.{}, is end. cost: {} ms", fullClassName, methodName, (System.currentTimeMillis() - start));
        return proceed;
    }

}
