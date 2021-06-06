package com.zxf.cache;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import java.util.HashMap;

/**
 * 抽象缓存工厂
 *
 * @author 朱晓峰
 */
public abstract class AbstractCacheFactory implements CacheFactory, InitializingBean, DisposableBean {

    /**
     * cacheHashMap
     */
    private HashMap<String, ICache> cacheHashMap = new HashMap<>();

    /**
     * 获取缓存工厂产品
     *
     * @param cacheName 缓存类型的名称
     * @return ICache
     */
    @Override
    public final ICache doGetCache(String cacheName) {
        return this.doGetCache(cacheName, false);
    }

    /**
     * 获取缓存工厂产品
     *
     * @param cacheName  缓存类型的名称
     * @param withoutTTL 是否无过期时间 true 无 false 有
     * @return
     */
    @Override
    public ICache doGetCache(String cacheName, boolean withoutTTL) {
        if (withoutTTL) {
            return this.doGetCache(cacheName, -1);
        } else {
            return this.doGetCache(cacheName, this.getDefaultTimeout());
        }
    }

    /**
     * 获取缓存工厂产品
     *
     * @param cacheName
     * @param timeout
     * @return
     */
    @Override
    public final ICache doGetCache(String cacheName, int timeout) {
        synchronized (this) {
            ICache cache = cacheHashMap.get(cacheName);
            if (null == cache) {
                AbstractCache newCahe = this.createCache(cacheName, timeout);
                this.cacheHashMap.put(cacheName, newCahe);
                return newCahe;
            }
            return cache;
        }
    }

    /**
     * 创建缓存工厂
     *
     * @param cacheName 缓存工厂名
     * @param timeout   超时时间
     * @return AbstractCache
     */
    protected abstract AbstractCache createCache(String cacheName, int timeout);

    protected int getDefaultTimeout() {
        return 0;
    }

    @Override
    public final void afterPropertiesSet() throws Exception {
        this.init();
    }

    @Override
    public final void destroy() throws Exception {
        this.dispose();
    }
}
