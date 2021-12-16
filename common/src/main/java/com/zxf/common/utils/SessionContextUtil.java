package com.zxf.common.utils;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * spring 应用会话请求工具
 * @author zhuxiaofeng
 * @date 2021/11/22
 */
public class SessionContextUtil extends RequestContextHolder {

    /**
     * 获取当前会话请求request
     * @return HttpServletRequest
     */
    public static HttpServletRequest getCurrentRequest(){
        RequestAttributes requestAttributes = currentRequestAttributes();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
        return servletRequestAttributes.getRequest();
    }

    /**
     * 获取当前会话请求response
     * @return HttpServletResponse
     */
    public static HttpServletResponse getCurrentResponse(){
        RequestAttributes requestAttributes = currentRequestAttributes();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
        return servletRequestAttributes.getResponse();
    }

}
