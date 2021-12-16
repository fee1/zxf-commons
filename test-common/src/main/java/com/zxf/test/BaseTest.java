package com.zxf.test;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 逻辑测试基类
 *
 * @author zxf
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = BootStarterTest.class)
public abstract class BaseTest {
}
