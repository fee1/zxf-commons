package com.zxf.cache.redis;

import com.zxf.cache.AbstractCache;
import com.zxf.cache.AbstractCacheFactory;

/**
 * redis 产品缓存工厂
 * @author 朱晓峰
 */
public class RedisCacheFactory extends AbstractCacheFactory {

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
