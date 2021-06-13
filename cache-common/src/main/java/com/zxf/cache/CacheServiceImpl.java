package com.zxf.cache;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 朱晓峰
 */
@Service
public class CacheServiceImpl implements CacheService{

    private Map<String, CacheFactory> factoryMap = new HashMap<>();

    @Value("${cache.type:redis}")
    private String defaultCacheType;

    public CacheServiceImpl(@Autowired CacheFactory[] cacheFactories){
        for (CacheFactory cacheFactory : cacheFactories) {
            this.factoryMap.put(cacheFactory.getCacheType(), cacheFactory);
        }
    }

    private CacheFactory getCacheFactory(String cacheType){
        String type = StringUtils.isEmpty(cacheType) ? this.defaultCacheType : cacheType;
        CacheFactory cacheFactory = this.factoryMap.get(cacheType);
        Assert.notNull(cacheFactory, "没有找到对应type的cacheFactory工厂："+ cacheType);
        return cacheFactory;
    }

    @Override
    public CacheFactory getDefaultCacheFactory() {
        return this.getCacheFactory(this.defaultCacheType);
    }

    @Override
    public ICache getCache(String name) {
        return getDefaultCacheFactory().doGetCache(name);
    }

    @Override
    public ICache getCache(String name, String cacheType) {
        return getCacheFactory(cacheType).doGetCache(name);
    }

    @Override
    public ICache getCache(String name, boolean withoutTimeout) {
        return getDefaultCacheFactory().doGetCache(name, withoutTimeout);
    }

    @Override
    public ICache getCache(String name, String cacheType, boolean withoutTimeout) {
        return getCacheFactory(cacheType).doGetCache(name, withoutTimeout);
    }

    @Override
    public ICache getCache(String name, int timeout) {
        return getDefaultCacheFactory().doGetCache(name, timeout);
    }

    @Override
    public ICache getCache(String name, String cacheType, int timeout) {
        return getCacheFactory(cacheType).doGetCache(name, timeout);
    }
}
