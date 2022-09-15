package com.zxf.common.log;

import com.alibaba.fastjson.JSONObject;
import com.zxf.common.domain.WebLog;
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

    @Value("${webLog.ignore.urls:}")
    private String[] ignoreUrls;

    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)||" +
            "@annotation(org.springframework.web.bind.annotation.PostMapping)||" +
            "@annotation(org.springframework.web.bind.annotation.GetMapping)||" +
            "@annotation(org.springframework.web.bind.annotation.DeleteMapping)||" +
            "@annotation(org.springframework.web.bind.annotation.PutMapping)")
    public void webLog(){}

    @Around("webLog()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable{
        HttpServletRequest currentRequest = SessionContextUtil.getCurrentRequest();

        //需要忽略的web请求日志
        for (String ignoreUrl : ignoreUrls) {
            if (StringUtils.contains(currentRequest.getRequestURI(), ignoreUrl)){
                return joinPoint.proceed();
            }
        }

        long startTime = System.currentTimeMillis();
        //方法执行
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        //获取当前请求

        WebLog webLog = new WebLog();
        webLog.setRemoteUser(currentRequest.getRemoteUser());
        webLog.setMethod(currentRequest.getMethod());
        webLog.setRequestURI(currentRequest.getRequestURI());
        webLog.setRequestURL(currentRequest.getRequestURL().toString());
        webLog.setRequestBody(joinPoint.getArgs());
        webLog.setResponseBody(result);
        webLog.setStartTime(startTime);
        webLog.setEndTime(endTime);
        webLog.setSpendTime(endTime - startTime);

        log.info(JSONObject.toJSONString(webLog));
        return result;
    }

}
