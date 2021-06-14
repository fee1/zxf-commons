package com.zxf.cache;

public interface CacheService {

    /**
     * 默认的cacheFactory
     * @return cacheFactory
     */
    CacheFactory getDefaultCacheFactory();

    /**
     * factory更具名字获取信息的cache对象
     * @param name 名字
     * @return ICache
     */
    ICache getCache(String name);

    /**
     * factory根据类型与缓存类型获取一个cache对象
     * @param name 名字
     * @param cacheType 缓存的类型
     * @return ICache
     */
    ICache getCache(String name ,String cacheType);

    /**
     * factory获取一个缓存
     * @param name 名字
     * @param withoutTimeout 是否有超时时间
     * @return ICache
     */
    ICache getCache(String name, boolean withoutTimeout);

    /**
     * factory 获取缓存
     * @param name 名字
     * @param cacheType cache 类型
     * @param withoutTimeout 是否有超时时间
     * @return ICache
     */
    ICache getCache(String name, String cacheType, boolean withoutTimeout);

    /**
     * factory 获取缓存
     * @param name 名字
     * @param timeout 超时时间
     * @return ICache
     */
    ICache getCache(String name, int timeout);

    /**
     * 获取缓存
     * @param name 名字
     * @param cacheType cache 类型
     * @param timeout 超时时间
     * @return ICache
     */
    ICache getCache(String name, String cacheType, int timeout);

}
