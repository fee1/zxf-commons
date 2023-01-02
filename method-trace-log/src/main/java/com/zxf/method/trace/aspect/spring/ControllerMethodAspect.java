package com.zxf.method.trace.aspect.spring;

import com.zxf.method.trace.aspect.AbstractMethod;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author zhuxiaofeng
 * @date 2022/12/8
 */
@Slf4j
@Aspect
public class ControllerMethodAspect extends AbstractMethod {

    @Pointcut("(@within(org.springframework.stereotype.Controller) || @within(org.springframework.web.bind.annotation.RestController))")
    public void pointCut(){}

    @Override
    @Around("pointCut() && mustPointCutMethod() && notPointCutLambdaMethod()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        log.debug("request args: {}", joinPoint.getArgs());
        return super.doAround(joinPoint);
    }
}
