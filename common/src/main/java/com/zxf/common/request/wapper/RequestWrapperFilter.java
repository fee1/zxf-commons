package com.zxf.common.request.wapper;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class RequestWrapperFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                  HttpServletResponse response,
                                  FilterChain filterChain) throws ServletException, IOException {
        
        if (isMultipartContent(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        RequestWrapper requestWrapper = new RequestWrapper(request);
        filterChain.doFilter(requestWrapper, response);
    }

    /**
     * 判断当前HTTP请求是否包含多部分内容（通常用于文件上传）
     *
     * @param request HTTP请求对象，用于获取请求头信息
     * @return boolean 类型返回值：
     *         - true：当Content-Type存在且以"multipart/"开头时
     *         - false：其他所有情况
     */
    private boolean isMultipartContent(HttpServletRequest request) {
        String contentType = request.getContentType();
        return contentType != null && contentType.toLowerCase().startsWith("multipart/");
    }
} 