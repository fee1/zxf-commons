package com.zxf.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author 朱晓峰
 */
@Slf4j
public abstract class AbstractLoadingCache<KEY, VALUE> implements LoadingCache<KEY, VALUE> {

    @Autowired
    private CacheService cacheService;

    private ICache cache;

    protected abstract VALUE doLoad() throws Exception;

    public abstract String getCacheName();

}
