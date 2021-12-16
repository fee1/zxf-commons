package com.zxf.test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

/**
 * MVC web请求测试类
 *
 * @author zhuxiaofeng
 * @date 2021/12/16
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = BootStarterTest.class)
@AutoConfigureMockMvc
public abstract class MvcBaseTest {

    @Autowired
    public MockMvc mockMvc;

}