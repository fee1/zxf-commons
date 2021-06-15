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
public abstract class AbstractLoadingCache<Key, Value> implements LoadingCache<Key, Value>, InitializingBean {

    @Autowired
    private CacheService cacheService;

    private ICache cache;

    protected abstract Value doLoad() throws Exception;

    public abstract String getCacheName();

    @Override
    public int getTimeout() {
        return 3600;
    }

    protected String convertKey(Key key){
        return String.valueOf(key);
    }

    private Value getFormCache(String key){
        return this.cache.get(key);
    }

    @Override
    @SneakyThrows
    public final Value get(Key key) {
        String keyStr = this.convertKey(key);
        Value value =  this.getFormCache(keyStr);
        if (value != null) {
            return value;
        }

        synchronized (this){
            Value newValue =  this.getFormCache(keyStr);
            if (newValue == null) {
                newValue = this.load(key);
            }
            return newValue;
        }
    }

    //todo 理解
    @Override
    public List<Value> multiGet(Collection<Key> keys, Function<Value, Key> function) {
        Map<String, Key> keyMap = new LinkedHashMap<>(keys.size());
        keys.forEach(key -> keyMap.put(this.convertKey(key), key));

        List<Value> foundValues = this.cache.multiGet(keyMap.keySet());
        ArrayList<Value> result = new ArrayList<>();

        for (Value foundValue : foundValues) {
            if (foundValue == null) {
                continue;
            }
            String key = this.convertKey(function.apply(foundValue));
            keyMap.remove(key);
            result.add(foundValue);
        }
        if (result.size() == keys.size()){
            return result;
        }

        Map<Key, Value> missValues = this.multiLoad(keyMap.values());
        return result;
    }

    @Override
    @SneakyThrows
    public final void set(String key, Value value) {
        Assert.notNull(value, "缓存value不能为null");
        this.cache.put(key, value);
    }

    //todo 理解
    @Override
    public void multiPut(Map<Key, Value> map) {
        Map<String, Value> valueMap = new LinkedHashMap<>();
        map.forEach((key, value) -> {
            String keyStr = this.convertKey(key);
            valueMap.put(keyStr, value);
        });
        this.cache.multiPut(valueMap);
    }

    @Override
    public final void remove(String... keys) {
        this.cache.removeKeys(keys);
    }

    protected abstract Value doLoad(Key key) throws Exception;

    protected Map<Key, Value> doMultiLoad(Collection<Key> keys) throws Exception{
        LinkedHashMap<Key, Value> map = new LinkedHashMap<>();
        for (Key key : keys) {
            Value value = this.doLoad(key);
            if (value != null) {
                map.put(key, value);
            }
        }
        return map;
    }

    @SneakyThrows
    @Override
    public final Value load(Key key) {
        try {
            Value value = this.doLoad(key);
            if (value != null) {
                this.set(this.convertKey(key), value);
            }
            return value;
        }catch (Exception e){
            throw this.onLoadError(key, e);
        }
    }

    @SneakyThrows
    @Override
    public final Map<Key, Value> multiLoad(Collection<Key> keys) {
        try {
            Map<Key, Value> keyValueMap = this.doMultiLoad(keys);
            if (keyValueMap != null) {
                this.multiPut(keyValueMap);
            }
            return Optional.ofNullable(keyValueMap).orElse(new HashMap<>(0));
        }catch (Exception e){
            throw this.onMultiLoadError(keys, e);
        }
    }

    @Override
    public boolean exists(Key key) {
        return this.cache.exists(this.convertKey(key));
    }

    private Exception onLoadError(Key key, Exception e){
        log.error("failed load key:{} to cache:{}", key, this.getCacheName(), e);
        return new RuntimeException("加载缓存失败");
    }

    private Exception onMultiLoadError(Collection<Key> keys, Exception e){
        log.error("failed load key:{} to cache:{}", keys, this.getCacheName(), e);
        return new RuntimeException("加载缓存失败");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.cache = cacheService.getCache(this.getCacheName(), "", this.getTimeout());
    }
}
