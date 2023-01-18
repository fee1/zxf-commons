package com.zxf.method.trace.filter;

import com.zxf.method.trace.constants.Constants;
import com.zxf.method.trace.util.TraceFatch;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * traceId区分不同请求
 *
 * distinguish
 * @author zhuxiaofeng
 * @date 2022/9/1
 */
@Slf4j
@Order(1)
public class HttpRequestTraceFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String traceId = request.getHeader(Constants.TRACE_ID);
        if (StringUtils.isEmpty(traceId)) {
            MDC.put(Constants.TRACE_ID, TraceFatch.getTraceId());
        }else {
            String appName = request.getHeader(Constants.APP_NAME);
            traceId = String.format("%s_%s", appName, traceId);
            MDC.put(Constants.TRACE_ID, traceId);
        }
        log.info("request url: {}", request.getRequestURI());
        filterChain.doFilter(request, response);
        MDC.remove(Constants.TRACE_ID);
    }
}
