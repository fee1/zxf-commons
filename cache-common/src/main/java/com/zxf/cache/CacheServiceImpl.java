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

    /**
     * 更具类型获取对应的工厂
     * @param cacheType 类型
     * @return CacheFactory
     */
    private CacheFactory getCacheFactory(String cacheType){
        String type = StringUtils.isEmpty(cacheType) ? this.defaultCacheType : cacheType;
        CacheFactory cacheFactory = this.factoryMap.get(cacheType);
        Assert.notNull(cacheFactory, "没有找到对应type的cacheFactory工厂："+ cacheType);
        return cacheFactory;
    }

    /**
     * 默认的cacheFactory
     * @return cacheFactory
     */
    @Override
    public CacheFactory getDefaultCacheFactory() {
        return this.getCacheFactory(this.defaultCacheType);
    }

    /**
     * factory更具名字获取信息的cache对象
     * @param name 名字
     * @return ICache
     */
    @Override
    public ICache getCache(String name) {
        return getDefaultCacheFactory().doGetCache(name);
    }

    /**
     * factory根据类型与缓存类型获取一个cache对象
     * @param name 名字
     * @param cacheType 缓存的类型
     * @return ICache
     */
    @Override
    public ICache getCache(String name, String cacheType) {
        return getCacheFactory(cacheType).doGetCache(name);
    }

    /**
     * factory获取一个缓存
     * @param name 名字
     * @param withoutTimeout 是否有超时时间
     * @return ICache
     */
    @Override
    public ICache getCache(String name, boolean withoutTimeout) {
        return getDefaultCacheFactory().doGetCache(name, withoutTimeout);
    }

    /**
     * factory 获取缓存
     * @param name 名字
     * @param cacheType cache 类型
     * @param withoutTimeout 是否有超时时间
     * @return ICache
     */
    @Override
    public ICache getCache(String name, String cacheType, boolean withoutTimeout) {
        return getCacheFactory(cacheType).doGetCache(name, withoutTimeout);
    }

    /**
     * factory 获取缓存
     * @param name 名字
     * @param timeout 超时时间
     * @return ICache
     */
    @Override
    public ICache getCache(String name, int timeout) {
        return getDefaultCacheFactory().doGetCache(name, timeout);
    }

    /**
     * 获取缓存
     * @param name 名字
     * @param cacheType cache 类型
     * @param timeout 超时时间
     * @return ICache
     */
    @Override
    public ICache getCache(String name, String cacheType, int timeout) {
        return getCacheFactory(cacheType).doGetCache(name, timeout);
    }
}
