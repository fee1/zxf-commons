package com.zxf.method.trace.http;

import com.zxf.method.trace.constants.Constants;
import com.zxf.method.trace.util.TraceFatch;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

/**
 * RestTemplate 的traceId传递添加
 *
 * @author zhuxiaofeng
 * @date 2023/3/27
 */
@Slf4j
public class RestTemplateTraceInterceptor implements ClientHttpRequestInterceptor {
    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
        String traceId = TraceFatch.getTraceId();
        if (StringUtils.isNotBlank(traceId)) {
            httpRequest.getHeaders().add(Constants.TRACE_ID, traceId);
        } else {
            log.debug("本地threadLocal变量没有正确传递traceId,本次调用不传递traceId");
        }
        return clientHttpRequestExecution.execute(httpRequest, bytes);
    }
}
