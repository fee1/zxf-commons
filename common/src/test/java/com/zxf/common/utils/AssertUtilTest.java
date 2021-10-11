package com.zxf.common.utils;

import org.junit.Test;

/**
 * @author zhuxiaofeng
 * @date 2021/10/11
 */
public class AssertUtilTest {

    @Test
    public void isTrue() {

        AssertUtil.isTrue(false, RuntimeException.class, "断言异常抛出测试");

    }
}