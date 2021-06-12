package com.zxf.cache.redis;


import com.zxf.test.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class RedisConfigTest extends BaseTest {

    @Autowired
    RedisConfig redisConfig;

    @Test
    public void testPropertiesValue(){
        System.out.println("----------test redis config----------"+redisConfig.getHosts()[1]);
    }

}