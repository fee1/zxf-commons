package com.zxf.cache.guava;

import com.google.common.cache.CacheLoader;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 朱晓峰
 */
@Slf4j
public abstract class BaseGuavaCacheLoader<T> extends CacheLoader<String, T> {

    private ConcurrentHashMap<String, T> lastCacheItems = new ConcurrentHashMap<>();

    /**
     * 获取数据
     * @param key key
     * @return T 返回
     */
    protected abstract T doLoad(String key);

    /**
     * 从缓存中或者源数据中加载数据
     * @param key
     * @return
     * @throws Exception
     */
    @Override
    public T load(String key) throws Exception {
        try {
            T data = this.doLoad(key);
            if (this.isReturnLastIfFailed()){
                this.lastCacheItems.put(key, data);
            }
            return data;
        }catch (Exception e){
            return this.onLoadError(key, e);
        }
    }

    private T onLoadError(String key, Exception e) throws Exception {
        T lastItem = this.getCacheLastItem(key);
        if (null != lastItem){
            log.error("failed load cache: {}", key, e);
            return lastItem;
        }else {
            throw e;
        }
    }

    /**
     * 获取上一次缓存数据
     * @param key key
     * @return T 返回
     */
    protected T getCacheLastItem(String key){
        if (this.isReturnLastIfFailed()){
            return this.lastCacheItems.get(key);
        }
        return null;
    }

    /**
     * 如果失败是否返回上一次缓存
     * @return boolean
     */
    protected boolean isReturnLastIfFailed(){
        return true;
    }

}
