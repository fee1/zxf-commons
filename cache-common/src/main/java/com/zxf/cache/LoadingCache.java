package com.zxf.cache;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @author 朱晓峰
 */
public interface LoadingCache<Key, Value> {

    int getTimeout();

    /**
     * 获取缓存
     * @param key key
     * @return value
     */
    Value get(Key key);

    /**
     * 获取多个缓存值
     * @param args
     * @param Value
     * @return
     */
    List<Value> multiGet(Collection<Key> keys, Function<Value, Key> function);

    default List<Value> multiGet(Collection<Key> keys){
        return this.multiGet(keys, null);
    }

    /**
     * 设置缓存
     * @param key key
     * @param value value
     */
    void set(String key, Value value);

    /**
     * 批量设置缓存
     * @param map map
     */
    void multiSet(Map<String, Value> map);

    /**
     * 批量设置缓存
     * @param map map
     */
    void multiPut(Map<Key, Value> map);

    /**
     * 批量移除缓存
     * @param keys keys
     */
    void remove(String... keys);

    /**
     * 加载缓存
     * @param key key
     * @return Value
     */
    Value load(Key key);

    /**
     * 加载缓存
     * @param keys keys
     * @return Map<Key, Value>
     */
    Map<Key, Value> multiLoad(Collection<Key> keys);

    /**
     * 判断是是否存在值
     * @param key key
     * @return boolean
     */
    boolean exists(Key key);

}
