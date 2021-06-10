package com.zxf.cache.redis;

import com.zxf.cache.AbstractCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 缓存产品类
 * @author 朱晓峰
 */
@Slf4j
public class RedisCache extends AbstractCache {

    /**
     * valueOperations
     */
    private final ValueOperations<String, Object> valueOperations;

    /**
     * hashOperations
     */
    private final HashOperations<String, String, Object> hashOperations;

    /**
     * redisTemplate
     */
    private final RedisTemplate<String, Object> redisTemplate;


    public RedisCache(RedisTemplate<String, Object> redisTemplate, String name, int timeout){
        super(name, timeout);
        this.valueOperations = redisTemplate.opsForValue();
        this.hashOperations = redisTemplate.opsForHash();
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void put(String key, Object value) {
        this.putAndRefreshTimeout(key, value);
    }

    @Override
    public void put(String key, Object value, long timeout, TimeUnit timeUnit) {
        this.valueOperations.set(key, value, timeout, timeUnit);
        log.debug("[{}]缓存类将信息加入缓存，key:{}, value:{},有效期:{}", this.getName(), key, value, timeout);
    }

    @Override
    public void putAndRefreshTimeout(String key, Object value) {
        if (this.timeout > 0){
            this.put(key, value, this.timeout, TimeUnit.SECONDS);
            log.debug("[{}]缓存类将信息加入缓存，key:{}, value:{},有效期:{}", this.getName(), key, value, timeout);
        }else {
            this.valueOperations.set(key, value);
            log.debug("[{}]缓存类将信息加入缓存，key:{}, value:{}", this.getName(), key, value);
        }
    }

    @Override
    public void putMap(String key, Map<String, Object> hashMap) {
        this.hashOperations.putAll(key, hashMap);
        if (this.timeout > 0){
            redisTemplate.expire(key, this.timeout, TimeUnit.SECONDS);
        }
        log.debug("[{}]缓存类将信息加入缓存，key:{}, value:{},有效期:{}", this.getName(), key, hashMap.keySet(), timeout);
    }

    @Override
    public void putMap(String key, String hashKey, Object value) {
        this.hashOperations.put(key, hashKey, value);
        if (timeout > 0){
            redisTemplate.expire(key, this.timeout, TimeUnit.SECONDS);
        }
        log.debug("[{}]缓存类将信息加入缓存，key:{}, hashKey: {},value:{},有效期:{}", this.getName(), key, hashKey, value,timeout);
    }

    /**
     * 获取hash中所有的值
     * @param key 缓存key
     * @return Map<String, Object>
     */
    @Override
    public Map<String, Object> getEntries(String key) {
        return this.hashOperations.entries(key);
    }

    @Override
    public void removeInMap(String key, String hashKey) {
        this.hashOperations.delete(key, hashKey);
        log.debug("[{}]缓存类删除缓存，key:{}, hashKey: {}", this.getName(), key, hashKey);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getFromHash(String key, String hashKey) {
        return (T) this.hashOperations.get(key, hashKey);
    }

    @Override
    public boolean setTimeout(String key, long timeout, TimeUnit timeUnit) {
        return this.redisTemplate.expire(key, timeout, timeUnit);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(String key) {
        return (T) this.valueOperations.get(key);
    }

    @Override
    public void remove(String key) {
        this.redisTemplate.delete(key);
    }

    @Override
    public boolean exists(String key) {
        return this.redisTemplate.hasKey(key);
    }

    @Override
    public void clear() {

    }

}
