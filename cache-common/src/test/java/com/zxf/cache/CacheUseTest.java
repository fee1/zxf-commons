package com.zxf.cache;

import com.zxf.cache.domain.cache.UserCache;
import com.zxf.cache.domain.model.UserModel;
import com.zxf.test.BaseTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * redis 连接 设值测试
 *
 * @author zhuxiaofeng
 * @date 2022/1/15
 */
public class CacheUseTest extends BaseTest {

    @Autowired
    private UserCache userCache;

    /**
     * 测试设置值
     */
    @Test
    public void testRedisSetValue(){
        userCache.set("test", new UserModel("xiaoming", "18"));
    }

    /**
     * 测试获取值
     */
    @Test
    public void testRedisGetValue(){
        Assert.assertEquals("UserModel(name=xiaoming, age=18)", userCache.get("test").toString());
    }

    /**
     * 测试guava 缓存存取值
     */
    @Test
    public void testGuavaGetAndSetValue(){
        userCache.set("test", new UserModel("xiaoming", "18"));
        Assert.assertEquals("UserModel(name=xiaoming, age=18)", userCache.get("test").toString());
    }

}
