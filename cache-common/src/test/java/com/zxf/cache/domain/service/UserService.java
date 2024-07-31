package com.zxf.cache.domain.service;

import com.zxf.cache.domain.cache.UserCache;
import com.zxf.cache.domain.cache.key.UserCacheKey;
import com.zxf.cache.domain.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhuxiaofeng
 * @date 2022/1/14
 */
@Service
public class UserService {

    @Autowired
    private UserCache userCache;


    public UserModel searchUser(String name, String age) throws Exception {
//        this.userCache.set();
//        this.userCache.remove();
        return this.userCache.get(UserCacheKey.toKey(name, age));
    }

}