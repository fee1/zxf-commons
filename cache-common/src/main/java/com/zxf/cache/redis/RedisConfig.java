package com.zxf.cache.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * redis配置类
 * @author 朱晓峰
 */
@Configuration
@ConditionalOnProperty(value = "cache.redis.enable", matchIfMissing = true)
@Slf4j
public class RedisConfig {

//    @Bean
//    Le

}
