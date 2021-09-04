package com.zxf.startup.config;

import com.zxf.common.config.BaseSwaggerConfig;
import com.zxf.common.domain.SwaggerProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger API文档相关配置
 * Created by macro on 2018/4/26.
 */
@ConditionalOnProperty(value = "swagger.enable", matchIfMissing = true)
@Configuration
@EnableSwagger2 ////表示打开swagger功能
public class SwaggerConfig extends BaseSwaggerConfig {

    @Value("${swagger.title}")
    private String title;

    @Value("${swagger.description}")
    private String description;

    @Value("${swagger.name}")
    private String name;

    @Value("${swagger.version}")
    private String version;

    @Override
    public SwaggerProperties swaggerProperties() {
        return SwaggerProperties.builder()
                .apiBasePackage("com.zxf.application")
                .title(title)
                .description(description)
                .contactName(name)
                .version(version)
                .enableSecurity(true)
                .build();
    }
}
