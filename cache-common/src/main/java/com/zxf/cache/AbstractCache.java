package com.zxf.cache;

import java.util.concurrent.TimeUnit;

/**
 * 抽象类产品类
 *
 * @author 朱晓峰
 */
public abstract class AbstractCache implements ICache {

    /**
     * cache name
     */
    private String name;

    /**
     * timeout
     */
    protected int timeout;

    public AbstractCache(String name, int timeout) {
        this.name = name;
        this.timeout = timeout;
    }

    /**
     * 获取缓存的名称
     *
     * @return String
     */
    public final String getName() {
        return this.name;
    }

    /**
     * 获取缓存，并且刷新超时时间
     *
     * @param <T> 返回类型
     * @return T
     */
    @Override
    public <T> T getAndRefreshTimeout(String key) {
        T val = this.get(key);
        if (this.timeout > 0) {
            this.setTimeout(key, this.timeout, TimeUnit.SECONDS);
        }
        return val;
    }

    /**
     * 刷新缓存
     */
    //todo 保留
    public void refresh() {

    }

    /**
     * 批量移除key
     *
     * @param keys keys
     */
    @Override
    public void removeKeys(String... keys) {
        for (String key : keys) {
            this.remove(key);
        }
    }

}
