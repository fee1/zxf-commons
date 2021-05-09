package com.zxf.cache;

/**
 * 缓存工厂接口类
 * @author zhuxiaofeng
 */
public interface CacheFactory {

    /**
     * 获取缓存的类型名称
     * @return String
     */
    String getCacheType();

    /**
     * 获取缓存工厂产品类
     * @param cacheName 缓存类型的名称
     * @return ICache
     */
    ICache getCache(String cacheName);

    /**
     *
     * @param cacheName 缓存类型的名称
     * @param timeOut 过期时间
     * @return
     */
    ICache getCache(String cacheName, int timeOut);

    /**
     *
     * @param cacheName 缓存类型的名称
     * @param withoutTTL 是否无过期时间 true 无 false 有
     * @return
     */
    ICache getCache(String cacheName, boolean withoutTTL);

    /**
     * 销毁缓存
     */
    default void dispose(){}

}
