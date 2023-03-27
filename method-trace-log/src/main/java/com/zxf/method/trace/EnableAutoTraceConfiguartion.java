package com.zxf.method.trace;

import com.zxf.method.trace.feign.FeignTraceInterceptor;
import com.zxf.method.trace.filter.HttpRequestTraceFilter;
import com.zxf.method.trace.http.RestTemplateTraceInterceptor;
import feign.RequestInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;

import javax.servlet.Filter;

/**
 * feign请求携带traceId到header中
 * and
 * 从请求中获取traceId
 *
 * @author zhuxiaofeng
 * @date 2022/10/11
 */
@Configuration
@ConditionalOnProperty(name = "auto.log.config.enable", matchIfMissing = true)
public class EnableAutoTraceConfiguartion {

    /**
     * http traceId
     * @return
     */
    @Bean
    public Filter distinguishRequestLogFilter(){
        return new HttpRequestTraceFilter();
    }

    /**
     * feign traceId
     */
    @Bean
    public RequestInterceptor feignTraceInterceptor(){
        return new FeignTraceInterceptor();
    }

    /**
     * RestTemplate traceId
     */
    @Bean
    public ClientHttpRequestInterceptor restTemplateTraceInterceptor(){
        return new RestTemplateTraceInterceptor();
    }


}
