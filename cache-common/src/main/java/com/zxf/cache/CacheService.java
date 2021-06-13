package com.zxf.cache;

public interface CacheService {

    /**
     * 默认的cacheFactory
     * @return cacheFactory
     */
    CacheFactory getDefaultCacheFactory();

    ICache getCache(String name);

    ICache getCache(String name ,String cacheType);

    ICache getCache(String name, boolean withoutTimeout);

    ICache getCache(String name, String cacheType, boolean withoutTimeout);

    ICache getCache(String name, int timeout);

    ICache getCache(String name, String cacheType, int timeout);

}
