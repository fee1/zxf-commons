package com.zxf.cache;

/**
 * @author 朱晓峰
 */
public abstract class BaseLoadingCache<V> extends AbstractLoadingCache<String, V> {

    protected BaseLoadingCache(){
    }

    @Override
    protected String convertKey(String s) {
        return s;
    }
}
