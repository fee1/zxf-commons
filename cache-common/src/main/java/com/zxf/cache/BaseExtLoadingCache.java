package com.zxf.cache;

/**
 * 拓展理解缓存，自定义key
 *
 * @author zhuxiaofeng
 * @date 2025/3/28
 */
public abstract class BaseExtLoadingCache<K extends CacheKey,V> extends AbstractLoadingCache<K, V> {

    public BaseExtLoadingCache() {
    }

    @Override
    protected String convertKey(CacheKey key) {
        return key.toKey();
    }

}
