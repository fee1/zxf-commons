package com.zxf.cache.redis;

import com.zxf.cache.AbstractCache;
import com.zxf.cache.AbstractCacheFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

/**
 * redis 产品缓存工厂
 * @author 朱晓峰
 */
public class RedisCacheFactory extends AbstractCacheFactory {


    private final RedisConfig redisConfig;
    private final String prefixKey;
    private final LettuceConnectionFactory lettuceConnectionFactory;

    public RedisCacheFactory(RedisConfig redisConfig, String prefixKey){
        this.redisConfig = redisConfig;
        this.prefixKey = prefixKey;
        this.lettuceConnectionFactory = redisConfig.lettuceConnectionFactory();
    }

    @Override
    protected AbstractCache createCache(String cacheName, int timeout) {
        return null;
    }

    @Override
    public String getCacheType() {
        return null;
    }

    @Override
    protected int getDefaultTimeout() {
        return super.getDefaultTimeout();
    }
}
