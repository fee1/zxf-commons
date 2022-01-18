package com.zxf.cache;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * @author 朱晓峰
 */
@Slf4j
public abstract class AbstractLoadingCache<K, V> implements LoadingCache<K, V>, InitializingBean {

    private static final int INT = 3600;
    @Autowired
    private CacheService cacheService;

    private ICache cache;

    public abstract String getCacheName();

    public String getCacheType(){
        return "";
    }

    @Override
    public int getTimeout() {
        return INT;
    }

    /**
     * key的规则转换，转换返回string类型的值做为key。功能由子类去实现，也可以不实现。
     * @param k key
     * @return string
     */
    protected String convertKey(K k){
        return String.valueOf(k);
    }

    private V getFormCache(String key){
        return this.cache.get(key);
    }

    @Override
    public void multiSet(Map<String, V> map) {
        this.cache.multiPut(map);
    }

    @Override
    @SneakyThrows
    public final V get(K k) {
        String keyStr = this.convertKey(k);
        V v =  this.getFormCache(keyStr);
        if (v != null) {
            return v;
        }
        //加锁，防止大量请求突然访问数据库导致数据库垮掉
        synchronized (this){
            V newV =  this.getFormCache(keyStr);
            if (newV == null) {
                newV = this.load(k);
            }
            return newV;
        }
    }
    
    @Override
    public List<V> multiGet(Collection<? extends K> ks, Function<? super V, ? extends K> function) {
        Map<String, K> keyMap = new LinkedHashMap<>(ks.size());
        ks.forEach(k -> keyMap.put(this.convertKey(k), k));

        List<V> foundVS = this.cache.multiGet(keyMap.keySet());
        ArrayList<V> result = new ArrayList<>();

        for (V foundV : foundVS) {
            if (foundV == null) {
                continue;
            }
            //移除成功找到值的key
            String key = this.convertKey(function.apply(foundV));
            keyMap.remove(key);
            result.add(foundV);
        }
        if (result.size() == ks.size()){
            return result;
        }
        //找到那些转换convertKey 以后找不到value的key
        Map<K, V> missValues = this.multiLoad(keyMap.values());
        return result;
    }

    @Override
    @SneakyThrows
    public final void set(String key, V v) {
        Assert.notNull(v, "缓存value不能为null");
        this.cache.put(key, v);
    }

    @Override
    public void multiPut(Map<K, V> map) {
        Map<String, V> valueMap = new LinkedHashMap<>();
        map.forEach((k, v) -> {
            String keyStr = this.convertKey(k);
            valueMap.put(keyStr, v);
        });
        this.cache.multiPut(valueMap);
    }

    @Override
    public final void remove(String... keys) {
        this.cache.removeKeys(keys);
    }

    protected abstract V doLoad(K k) throws Exception;

    protected Map<K, V> doMultiLoad(Collection<K> ks) throws Exception{
        LinkedHashMap<K, V> map = new LinkedHashMap<>();
        for (K k : ks) {
            V v = this.doLoad(k);
            if (v != null) {
                map.put(k, v);
            }
        }
        return map;
    }

    @SneakyThrows
    @Override
    public final V load(K k) {
        try {
            V v = this.doLoad(k);
            if (v != null) {
                this.set(this.convertKey(k), v);
            }
            return v;
        }catch (Exception e){
            throw this.onLoadError(k, e);
        }
    }

    @SneakyThrows
    @Override
    public final Map<K, V> multiLoad(Collection<K> ks) {
        try {
            Map<K, V> keyValueMap = this.doMultiLoad(ks);
            if (keyValueMap != null) {
                this.multiPut(keyValueMap);
            }
            return Optional.ofNullable(keyValueMap).orElse(new HashMap<>(0));
        }catch (Exception e){
            throw this.onMultiLoadError(ks, e);
        }
    }

    @Override
    public boolean exists(K k) {
        return this.cache.exists(this.convertKey(k));
    }

    private Exception onLoadError(K k, Exception e){
        log.error("failed load key:{} to cache:{}", k, this.getCacheName(), e);
        return new RuntimeException("加载缓存失败");
    }

    private Exception onMultiLoadError(Collection<K> ks, Exception e){
        log.error("failed load key:{} to cache:{}", ks, this.getCacheName(), e);
        return new RuntimeException("加载缓存失败");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.cache = cacheService.getCache(this.getCacheName(), this.getCacheType(), this.getTimeout());
    }
}
