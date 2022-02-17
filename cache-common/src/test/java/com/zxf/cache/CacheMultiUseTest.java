package com.zxf.cache;

import com.zxf.cache.domain.cache.UserGuavaCache;
import com.zxf.cache.domain.cache.UserRedisCache;
import com.zxf.cache.domain.model.UserModel;
import com.zxf.test.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author zhuxiaofeng
 * @date 2022/2/11
 */
public class CacheMultiUseTest extends BaseTest {

    @Autowired
    private UserGuavaCache guavaCache;

    @Autowired
    private UserRedisCache redisCache;

    @Test
    public void multiCacheUseTest(){
        //guava 缓存使用测试
        guavaCache.set("test", new UserModel("xiaoming", "18"));
        System.out.println(guavaCache.get("test"));

        //redis 缓存使用测试
        redisCache.set("test", new UserModel("xiaoming", "18"));
        System.out.println(redisCache.get("test"));
    }


}
