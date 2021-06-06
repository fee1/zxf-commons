package com.zxf.cache;

/**
 * 缓存工厂接口类
 *
 * @author zhuxiaofeng
 */
public interface CacheFactory {

    /**
     * 获取缓存的类型名称
     *
     * @return String
     */
    String getCacheType();

    /**
     * 获取缓存工厂产品类
     *
     * @param cacheName 缓存类型的名称
     * @return ICache
     */
    ICache doGetCache(String cacheName);

    /**
     * @param cacheName 缓存类型的名称
     * @param timeout   过期时间
     * @return
     */
    ICache doGetCache(String cacheName, int timeout);

    /**
     * @param cacheName  缓存类型的名称
     * @param withoutTTL 是否无过期时间 true 无 false 有
     * @return
     */
    ICache doGetCache(String cacheName, boolean withoutTTL);

    /**
     * 初始化缓存
     */
    default void init() {
    }

    /**
     * 销毁缓存
     */
    default void dispose() {
    }

}
