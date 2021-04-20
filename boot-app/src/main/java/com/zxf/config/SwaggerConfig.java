package com.zxf.config;

import com.zxf.domain.SwaggerProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger API文档相关配置
 * Created by macro on 2018/4/26.
 */
@Configuration
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
