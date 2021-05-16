package com.zxf.cache.redis;


import com.zxf.test.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class RedisConfigTest extends BaseTest {

    @Autowired
    RedisConfig redisConfig;

    @Test
    public void testProperties(){
        System.out.println(redisConfig.getHost());
        System.out.println(redisConfig.getHosts()[1]);
    }

}