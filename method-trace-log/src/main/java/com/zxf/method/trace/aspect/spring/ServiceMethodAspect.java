package com.zxf.method.trace.aspect.spring;

import com.zxf.method.trace.aspect.AbstractMethod;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author zhuxiaofeng
 * @date 2022/12/6
 */
@Aspect
@Slf4j
public class ServiceMethodAspect extends AbstractMethod {

    @Pointcut("@within(org.springframework.stereotype.Service)")
    public void pointCut(){}

    @Override
    @Around("pointCut() && mustPointCutMethod() && notPointCutLambdaMethod()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        return super.doAround(joinPoint);
    }

}
