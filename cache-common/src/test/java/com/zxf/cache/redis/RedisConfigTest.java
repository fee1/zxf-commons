package com.zxf.cache.redis;


import com.zxf.test.BaseTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class RedisConfigTest extends BaseTest {

    @Autowired
    RedisProperties properties;

    @Test
    public void testPropertiesValue(){
        Assert.assertEquals("192.168.137.135:6379", properties.getHosts()[0]);
    }

}