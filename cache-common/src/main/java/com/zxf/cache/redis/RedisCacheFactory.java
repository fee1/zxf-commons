package com.zxf.cache.redis;

import com.zxf.cache.AbstractCache;
import com.zxf.cache.AbstractCacheFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * redis 产品缓存工厂
 * @author 朱晓峰
 */
public class RedisCacheFactory extends AbstractCacheFactory {

    /**
     * redisConfig
     */
    private final RedisConfig redisConfig;

    /**
     * prefixKey
     */
    private final String prefixKey;

    /**
     * lettuceConnectionFactory
     */
    private final LettuceConnectionFactory lettuceConnectionFactory;

    public RedisCacheFactory(RedisConfig redisConfig, String prefixKey){
        this.redisConfig = redisConfig;
        this.prefixKey = prefixKey;
        this.lettuceConnectionFactory = redisConfig.lettuceConnectionFactory();
    }

    public RedisTemplate<String, Object> createRedisTemplate(LettuceConnectionFactory lettuceConnectionFactory, String cacheName){
        RedisSerializer valueSerializer =
    }

    /**
     * 新建
     * @param lettuceConnectionFactory lettuceConnectionFactory
     * @param cacheName cacheName
     * @param valueSerializer valueSerializer
     * @return RedisTemplate
     */
    public RedisTemplate<String, Object> createRedisTemplate(LettuceConnectionFactory lettuceConnectionFactory,
                                                             String cacheName, RedisSerializer valueSerializer){
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        //设置非共享连接，允许多个连接公用一个物理连接。如果设置false ,每一个连接的操作都会开启和关闭socket连接。
        lettuceConnectionFactory.setShareNativeConnection(false);
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
        PrefixKeySerializer keySerializer = new PrefixKeySerializer(RedisConfig.formatFullKey(this.prefixKey, cacheName));
        redisTemplate.setKeySerializer(keySerializer);
        redisTemplate.setValueSerializer(valueSerializer);
        redisTemplate.setHashKeySerializer(keySerializer);
        redisTemplate.setHashValueSerializer(valueSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Override
    protected AbstractCache createCache(String cacheName, int timeout) {
        RedisTemplate<String, Object> redisTemplate = this.createRedisTemplate(this.lettuceConnectionFactory, cacheName);
        return new RedisCache(redisTemplate, cacheName, timeout);
    }


    /**
     * 获取缓存类型
     * @return str
     */
    @Override
    public String getCacheType() {
        return "redis";
    }

    /**
     *
     * @return
     */
    @Override
    protected int getDefaultTimeout() {
        return this.redisConfig.getTimeout();
    }
}
