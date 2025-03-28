package com.zxf.cache.domain.cache;


import com.zxf.cache.BaseExtLoadingCache;
import com.zxf.cache.CacheKey;
import com.zxf.cache.domain.cache.key.UserExtCacheKey;
import com.zxf.cache.domain.model.UserModel;
import org.springframework.stereotype.Service;

/**
 * @author zhuxiaofeng
 * @date 2025/3/28
 */
@Service
public class UserCacheExt extends BaseExtLoadingCache<UserExtCacheKey, UserModel> {


    @Override
    public String getCacheName() {
        return "userExt";
    }

    @Override
    protected UserModel doLoad(UserExtCacheKey key) throws Exception {
        key.getAge();
        key.getName();
        return new UserModel("xiaoming", "18");
    }
}
