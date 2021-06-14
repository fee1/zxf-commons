package com.zxf.cache;

/**
 * @author 朱晓峰
 */
public abstract class BaseLoadingCache<Value> extends AbstractLoadingCache<String, Value> {

    protected BaseLoadingCache(){
    }

    @Override
    protected String convertKey(String s) {
        return s;
    }
}
