package com.zxf.method.trace.feign;

import com.zxf.method.trace.constants.Constants;
import com.zxf.method.trace.util.TraceFatch;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;

/**
 * 快仓 定制化 interface-plugin直接复制粘贴进去这个类
 *
 * @author zhuxiaofeng
 * @date 2022/10/25
 */
@Slf4j
public class FeignTraceInterceptor implements RequestInterceptor {

    @Value("${spring.application.name}")
    private String appName;

    @Override
    public void apply(RequestTemplate template) {
        String traceId = TraceFatch.getTraceId();
        if (StringUtils.isNotBlank(traceId)) {
            template.header(Constants.TRACE_ID, traceId);
            template.header(Constants.APP_NAME, this.appName);
        } else {
            log.debug("本地threadLocal变量没有正确传递traceId,本次调用不传递traceId");
        }
    }
}
