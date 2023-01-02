package com.zxf.method.trace.aspect.spring;

import com.zxf.method.trace.aspect.AbstractMethod;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author zhuxiaofeng
 * @date 2022/12/5
 */
@Aspect
@Slf4j
public class ComponentMethodAspect extends AbstractMethod {

    @Pointcut("@within(org.springframework.stereotype.Component)")
    public void pointCut(){}

    @Pointcut("!within(org.springframework.beans.factory.config.BeanPostProcessor+)" +
            " && !within(org.springframework.beans.factory.config.BeanFactoryPostProcessor+)" +
            " && !within(org.springframework.boot.CommandLineRunner+)")
    public void notPointSpringStart(){}

    @Pointcut
    public void customizeNotPointCut(){}

    @Override
    @Around("pointCut() && mustPointCutMethod() && notPointSpringStart() && notPointCutLambdaMethod() && customizeNotPointCut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        return super.doAround(joinPoint);
    }

}
