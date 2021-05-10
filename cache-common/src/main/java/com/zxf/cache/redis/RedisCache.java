package com.zxf.cache.redis;

import com.zxf.cache.AbstractCache;

/**
 * 缓存产品类
 * @author 朱晓峰
 */
public class RedisCache extends AbstractCache {

    @Override
    public void put(String key, Object value) {

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
