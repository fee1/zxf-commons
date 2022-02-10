package com.zxf.cache.redis;


import com.zxf.test.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class RedisConfigTest extends BaseTest {

    @Autowired
    RedisProperties properties;

    @Test
    public void testPropertiesValue(){
        System.out.println("----------test redis config----------"+ properties.getHosts()[0]);
    }

}