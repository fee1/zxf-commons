package com.zxf.cache.guava;

import com.zxf.cache.AbstractCache;
import com.zxf.cache.AbstractCacheFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * guava缓存工厂
 * @author 朱晓峰
 */
@Configuration
@ConditionalOnProperty(value = "cache.type", havingValue = "guava")
@ConfigurationProperties(prefix = "cache.guava")
public class GuavaCacheFactory extends AbstractCacheFactory {

    @Override
    protected AbstractCache createCache(String cacheName, int timeout) {
        return new GuavaCache(cacheName, timeout);
    }

    @Override
    public String getCacheType() {
        return "guava";
    }

}
