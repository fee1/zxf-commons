package com.zxf.method.trace;

import com.zxf.method.trace.feign.FeignTraceInterceptor;
import com.zxf.method.trace.filter.HttpRequestTraceFilter;
import feign.RequestInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

/**
 * Spring AOP的方式需要对象被spring管理，并且会让spring 去生产更多的Advice类去参与到执行，暂时采用此方式
 *
 * 后续会采用javaagent的方式进行字节码织入，非spring管理的对象也能够使用切面
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
     * 暂时不取缔此字段
     */
//    @Bean
//    public MetaObjectInsertHandlerAspect metaObjectHandlerAspect(){
//        return new MetaObjectInsertHandlerAspect();
//    }
//
//    @Bean
//    public MetaObjectUpdateHandlerAspect metaObjectUpdateHandlerAspect() {
//        return new MetaObjectUpdateHandlerAspect();
//    }

}
