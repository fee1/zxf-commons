package com.zxf.cache.domain.cache;

import com.zxf.cache.BaseLoadingCache;
import com.zxf.cache.CacheType;
import com.zxf.cache.domain.model.UserModel;
import org.springframework.stereotype.Service;

/**
 * @author zhuxiaofeng
 * @date 2022/2/11
 */
@Service
public class UserGuavaCache extends BaseLoadingCache<UserModel> {
    @Override
    public String getCacheName() {
        return "user";
    }

    @Override
    protected UserModel doLoad(String s) throws Exception {
        return null;
    }

    @Override
    public String getCacheType() {
        return CacheType.guava.name();
    }
}
