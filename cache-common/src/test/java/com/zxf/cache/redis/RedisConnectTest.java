package com.zxf.cache.redis;

import com.zxf.cache.domain.cache.UserCache;
import com.zxf.cache.domain.model.UserModel;
import com.zxf.test.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * redis 连接 设值测试
 *
 * @author zhuxiaofeng
 * @date 2022/1/15
 */
public class RedisConnectTest extends BaseTest {

    @Autowired
    private UserCache userCache;

    /**
     * 测试设置值
     */
    @Test
    public void testSetValue(){
        userCache.set("test", new UserModel("xiaoming", "18"));
    }

    /**
     * 测试获取值
     */
    @Test
    public void testGetValue(){
        System.out.println(userCache.get("test"));
    }

    /**
     * 测试删除值
     */
    @Test
    public void testDelValue(){

    }

}
