package com.zxf.cache.domain.cache;

import com.zxf.cache.BaseLoadingCache;
import com.zxf.cache.CacheType;
import com.zxf.cache.domain.cache.key.UserCacheKey;
import com.zxf.cache.domain.model.UserModel;
import com.zxf.cache.infrastructure.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 获取缓存对象，在Service使用
 * @author zhuxiaofeng
 * @date 2021/8/12
 */
@Service
public class UserCache extends BaseLoadingCache<UserModel> {

//    @Autowired
//    private UserMapper userMapper;


    @Override
    public int getTimeout() {
        return 84600;
    }

    /**
     * 指定使用哪一种缓存
     * @return
     */
    @Override
    public String getCacheType() {
        return CacheType.redis.name();
    }

    /**
     * 本缓存的名称
     * @return
     */
    @Override
    public String getCacheName() {
        return "user";
    }

    //
    @Override
    public UserModel doLoad(String s) throws Exception {
        // 这里去把key解析成对象，然后获取入参去数据库查询
//        UserCacheKey userCacheKey = UserCacheKey.parseFromKey(s);
//        userMapper.findByNameAndAge(userCacheKey.getName(), userCacheKey.getAge());
        return new UserModel("xiaoming", "18");
    }



}
