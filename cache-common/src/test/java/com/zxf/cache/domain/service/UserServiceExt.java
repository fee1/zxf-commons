package com.zxf.cache.domain.service;

import com.zxf.cache.domain.cache.UserCacheExt;
import com.zxf.cache.domain.cache.key.UserCacheKey;
import com.zxf.cache.domain.cache.key.UserExtCacheKey;
import com.zxf.cache.domain.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhuxiaofeng
 * @date 2025/3/28
 */
@Service
public class UserServiceExt {

    @Autowired
    private UserCacheExt userCacheExt;

    public UserModel searchUser(String name, String age) throws Exception {
        UserExtCacheKey userExtCacheKey = new UserExtCacheKey(name, Integer.valueOf(age));
        return userCacheExt.get(userExtCacheKey);
    }

}
