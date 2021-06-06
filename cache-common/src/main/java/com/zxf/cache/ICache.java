package com.zxf.cache;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 缓存产品类
 *
 * @author 朱晓峰
 */
public interface ICache {

    /**
     * 插入缓存
     *
     * @param key   缓存key
     * @param value 缓存值
     */
    void put(String key, Object value);

    /**
     * 插入缓存并且设置过期时间
     *
     * @param key      缓存key
     * @param value    缓存值
     * @param timeout  超时时间
     * @param timeUnit 时间单位
     */
    void put(String key, Object value, long timeout, TimeUnit timeUnit);

    /**
     * 插入并且更新缓存时间
     *
     * @param key   缓存key
     * @param value 缓存值
     */
    void putAndRefreshTimeout(String key, Object value);

    /**
     * 插入缓存
     *
     * @param key     缓存key
     * @param hashMap 缓存值
     */
    void putMap(String key, Map<String, Object> hashMap);

    /**
     * 插入缓存
     *
     * @param key     缓存key
     * @param hashKey 缓存hashkey-key
     * @param value   缓存值
     */
    void putMap(String key, String hashKey, Object value);

    /**
     * 获取hash中的所有key
     *
     * @param key 缓存key
     */
    Map<String, Object> getEntries(String key);

    /**
     * 删除hash中的key对应的值
     *
     * @param key     缓存key
     * @param hashKey 缓存hashkey
     */
    void removeInMap(String key, String hashKey);

    /**
     * 获取缓存并且刷新
     *
     * @param key 缓存key
     * @param <T> 返回类型
     * @return T T
     */
    <T> T getAndRefreshTimeout(String key);

    /**
     * 获取hash类型缓存并且刷新
     *
     * @param key     缓存key
     * @param hashKey 缓存hashkey
     * @param <T>     返回类型
     * @return T T
     */
    <T> T getFromHash(String key, String hashKey);

    /**
     * 根据key设置缓存过期时间
     *
     * @param key      缓存key
     * @param timeout  超时时间
     * @param timeUnit 时间单位
     * @return boolean
     */
    boolean setTimeout(String key, long timeout, TimeUnit timeUnit);

    /**
     * 获取缓存带默认值
     *
     * @param key          缓存key
     * @param defaultValue 默认值
     * @param <T>          标注为泛型方法 返回值类型
     * @return T
     */
    default <T> T get(String key, T defaultValue) {
        T value = this.get(key);
        return Optional.ofNullable(value).orElse(defaultValue);
    }

    /**
     * 获取缓存
     *
     * @param key 缓存key
     * @param <T> 标注为泛型方法 返回值类型
     * @return T
     */
    <T> T get(String key);

    /**
     * 多个key获取值
     *
     * @param keys keys
     * @param <T>  返回类型
     * @return T
     */
    default <T> List<T> multiGet(String... keys) {
        HashSet<String> set = new HashSet<>(keys.length);
        Collections.addAll(set, keys);
        return this.multiGet(set);
    }

    /**
     * 多个key获取值
     *
     * @param keys keys
     * @param <T>  返回类型
     * @return T
     */
    default <T> List<T> multiGet(Set<String> keys) {
        ArrayList<T> items = new ArrayList<>(keys.size());
        keys.forEach(key -> {
            T item = this.get(key);
            if (null != item) {
                items.add(item);
            }
        });
        return items;
    }

    /**
     * 多个值放缓存
     *
     * @param map map
     * @param <T> 类型
     */
    default <T> void multiPut(Map<String, T> map) {
        map.forEach((k, v) -> {
            this.put(k, v);
        });
    }

    /**
     * 删除key对应的缓存
     *
     * @param key key
     */
    void remove(String key);

    /**
     * 批量移除缓存
     *
     * @param keys keys
     */
    void removeKeys(String... keys);

    /**
     * 判断key是否存在
     *
     * @param key key
     * @return boolean
     */
    boolean exists(String key);

    /**
     * 清理缓存
     */
    void clear();

}
