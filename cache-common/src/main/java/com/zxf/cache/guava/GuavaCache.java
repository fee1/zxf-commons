package com.zxf.cache.guava;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.zxf.cache.AbstractCache;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author 朱晓峰
 */
public class GuavaCache extends AbstractCache {

    private Cache<String, Object> cache;

    public GuavaCache(String name, int timeout) {
        super(name, timeout);
        CacheBuilder<Object, Object> builder = CacheBuilder.newBuilder();
        if (timeout > 0){
            //当缓存项在指定的时间段内没有更新就会被回收
            builder.expireAfterWrite(timeout, TimeUnit.SECONDS);
        }else {
            //当缓存项上一次更新操作之后的多久会被刷新
            builder.expireAfterAccess(timeout, TimeUnit.SECONDS);
        }

        cache = builder.build();
    }

    @Override
    public void put(String key, Object value) {
        this.cache.put(key, value);
    }

    @Override
    public void put(String key, Object value, long timeout, TimeUnit timeUnit) {
        this.cache.put(key, value);
    }

    @Override
    public void putAndRefreshTimeout(String key, Object value) {
        this.cache.put(key, value);
    }

    @Override
    public void putMap(String key, Map<String, Object> hashMap) {
        this.cache.put(key, hashMap);
    }

    @Override
    public void putMap(String key, String hashKey, Object value) {
        synchronized (this){
            Map<String, Object> map = this.get(key);
            if (null == map){
                map = new HashMap<>();
            }
            map.put(hashKey, value);
            this.cache.put(key, map);
        }
    }

    @Override
    public Map<String, Object> getEntries(String key) {
        return this.get(key);
    }

    @Override
    public void removeInMap(String key, String hashKey) {
        Map<String, Object> map = this.get(key);
        if (map != null) {
            map.remove(hashKey);
        }
    }

    @Override
    public <T> T getFromMap(String key, String hashKey) {
        Map<String , Object> map = this.get(key);
        if (map == null) {
            return null;
        }
        return (T) map.get(hashKey);
    }

    @Override
    public boolean setTimeout(String key, long timeout, TimeUnit timeUnit) {
        return null != this.cache.getIfPresent(key);
    }

    @Override
    public <T> T get(String key) {
        return (T) this.cache.getIfPresent(key);
    }

    @Override
    public void remove(String key) {
        this.cache.invalidate(key);
    }

    @Override
    public boolean exists(String key) {
        return this.cache.getIfPresent(key) != null;
    }

    @Override
    public void clear() {
        this.cache.invalidateAll();
    }
}
