package com.zxf.common.utils;

import org.junit.Assert;
import org.junit.Test;

import java.nio.MappedByteBuffer;

/**
 * 顺序读写测试类
 *
 * @author zhuxiaofeng
 * @date 2021/8/27
 */
public class SequentialAccessUtilTest {

    @Test
    public void fileWriteAndRead() {

        MappedByteBuffer mbb = SequentialAccessUtil.fileWrite("src\\test\\resources\\test.txt",
                "test", 0);

        System.out.println(mbb.position());

        mbb = SequentialAccessUtil.fileWrite("src\\test\\resources\\test.txt",
                "-test-2", mbb.position());

        String content = SequentialAccessUtil.fileRead("src\\test\\resources\\test.txt",
                11);

        content = content.trim();

        Assert.assertTrue("内容不符", "test-test-2".equals(content));
    }

}