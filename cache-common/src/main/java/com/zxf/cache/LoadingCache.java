package com.zxf.cache;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @author 朱晓峰
 */
public interface LoadingCache<K, V> {

    int getTimeout();

    /**
     * 获取缓存
     * @param k key
     * @return value
     */
    V get(K k);

    /**
     * 获取多个缓存值
     * @param ks
     * @param function
     * @return
     */
    List<V> multiGet(Collection<? extends K> ks, Function<? super V, ? extends K> function);

    default List<V> multiGet(Collection<K> ks){
        return this.multiGet(ks, null);
    }

    /**
     * 设置缓存
     * @param key key
     * @param v value
     */
    void set(String key, V v);

    /**
     * 批量设置缓存
     * @param map map
     */
    void multiSet(Map<String, V> map);

    /**
     * 批量设置缓存
     * @param map map
     */
    void multiPut(Map<K, V> map);

    /**
     * 批量移除缓存
     * @param keys keys
     */
    void remove(String... keys);

    /**
     * 加载缓存
     * @param k key
     * @return Value
     */
    V load(K k);

    /**
     * 加载缓存
     * @param ks keys
     * @return Map<Key, Value>
     */
    Map<K, V> multiLoad(Collection<K> ks);

    /**
     * 判断是是否存在值
     * @param k key
     * @return boolean
     */
    boolean exists(K k);

}
