package com.zxf.cache.redis;

import com.zxf.cache.AbstractCache;
import com.zxf.cache.AbstractCacheFactory;
import com.zxf.cache.redis.serializer.AutoTypeValueSerializer;
import com.zxf.cache.redis.serializer.PrefixKeySerializer;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * redis 产品缓存工厂
 *
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

    public RedisCacheFactory(RedisConfig redisConfig, String prefixKey) {
        this.redisConfig = redisConfig;
        this.prefixKey = prefixKey;
        this.lettuceConnectionFactory = redisConfig.lettuceConnectionFactory();
    }

    /**
     * 新建
     *
     * @param lettuceConnectionFactory lettuceConnectionFactory
     * @param cacheName                cacheName
     * @return RedisTemplate
     */
    public RedisTemplate<String, Object> createRedisTemplate(LettuceConnectionFactory lettuceConnectionFactory, String cacheName) {
        RedisSerializer<Object> valueSerializer = AutoTypeValueSerializer.getInstance();
        return this.createRedisTemplate(lettuceConnectionFactory, cacheName, valueSerializer);
    }

    /**
     * 新建
     *
     * @param lettuceConnectionFactory lettuceConnectionFactory
     * @param cacheName                cacheName
     * @param valueSerializer          valueSerializer
     * @return RedisTemplate
     */
    public RedisTemplate<String, Object> createRedisTemplate(LettuceConnectionFactory lettuceConnectionFactory,
                                                             String cacheName, RedisSerializer<Object> valueSerializer) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
//        lettuceConnectionFactory.setShareNativeConnection(false);
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
        PrefixKeySerializer keySerializer = new PrefixKeySerializer(RedisConfig.formatFullKey(this.prefixKey, cacheName));
        redisTemplate.setKeySerializer(keySerializer);
        redisTemplate.setValueSerializer(valueSerializer);
        redisTemplate.setHashKeySerializer(keySerializer);
        redisTemplate.setHashValueSerializer(valueSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    /**
     * 新建缓存对象
     *
     * @param cacheName 缓存工厂名
     * @param timeout   超时时间
     * @return AbstractCache
     */
    @Override
    protected AbstractCache createCache(String cacheName, int timeout) {
        RedisTemplate<String, Object> redisTemplate = this.createRedisTemplate(this.lettuceConnectionFactory, cacheName);
        return new RedisCache(redisTemplate, cacheName, timeout);
    }


    /**
     * 获取缓存类型
     *
     * @return str
     */
    @Override
    public String getCacheType() {
        return "redis";
    }

    /**
     * 获取超时时间
     *
     * @return int
     */
    @Override
    protected int getDefaultTimeout() {
        return this.redisConfig.getTimeout();
    }
}
