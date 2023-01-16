package com.zxf.common.log;

import com.alibaba.fastjson.JSONObject;
import com.zxf.common.domain.WebLogInfo;
import com.zxf.common.utils.SessionContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * RequestMapping GetMapping PostMapping等请求方式的入参出参日志
 *
 * @author zhuxiaofeng
 * @date 2021/12/14
 */
@Component
@Aspect
@Order(1)
@Slf4j
public class WebLogAspect {

    @Pointcut("@annotation(com.zxf.common.log.WebLog)")
    public void webLog(){}

    @Around("webLog()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable{
        HttpServletRequest currentRequest = SessionContextUtil.getCurrentRequest();

        long startTime = System.currentTimeMillis();
        //方法执行
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        //获取当前请求

        WebLogInfo webLogInfo = new WebLogInfo();
        webLogInfo.setRemoteUser(currentRequest.getRemoteUser());
        webLogInfo.setMethod(currentRequest.getMethod());
        webLogInfo.setRequestURI(currentRequest.getRequestURI());
        webLogInfo.setRequestURL(currentRequest.getRequestURL().toString());
        webLogInfo.setRequestBody(joinPoint.getArgs());
        webLogInfo.setResponseBody(result);
        webLogInfo.setStartTime(startTime);
        webLogInfo.setEndTime(endTime);
        webLogInfo.setSpendTime(endTime - startTime);

        log.info(JSONObject.toJSONString(webLogInfo));
        return result;
    }

}
