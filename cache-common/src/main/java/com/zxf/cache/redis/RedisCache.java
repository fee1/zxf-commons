package com.zxf.cache.redis;

import com.zxf.cache.AbstractCache;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 缓存产品类
 * @author 朱晓峰
 */
public class RedisCache extends AbstractCache {



    @Override
    public void put(String key, Object value) {

    }

    @Override
    public void put(String key, Object value, long timeout, TimeUnit timeUnit) {

    }

    @Override
    public void putAndRefreshTimeout(String key, Object value) {

    }

    @Override
    public void putMap(String key, Map<String, Object> hashMap) {

    }

    @Override
    public void putMap(String key, String hashKey, Object value) {

    }

    @Override
    public Map<String, Object> getEntries(String key) {
        return null;
    }

    @Override
    public void removeInMap(String key, String hashKey) {

    }

    @Override
    public <T> T getAndRefreshTimeout(String key) {
        return null;
    }

    @Override
    public <T> T getFromHash(String key, String hashKey) {
        return null;
    }

    @Override
    public boolean setTimeout(String key, long timeout, TimeUnit timeUnit) {
        return false;
    }

    @Override
    public <T> T get(String key) {
        return null;
    }

    @Override
    public void remove(String key) {

    }

    @Override
    public void removeKeys(String... keys) {

    }

    @Override
    public boolean exists(String key) {
        return false;
    }

    @Override
    public void clear() {

    }

}
