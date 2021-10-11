package com.zxf.common.utils;

import lombok.SneakyThrows;
import org.springframework.util.Assert;

import java.lang.reflect.Constructor;

/**
 * 断言工具
 *
 * @author zhuxiaofeng
 * @date 2021/9/4
 */
public class AssertUtil extends Assert {

    /**
     * 自定义异常抛出
     * @param expression 表达式
     * @param exceptionClass 抛出异常类型
     * @param message 提示信息
     * @param <T> 类型
     */
    @SneakyThrows
    public static <T extends Exception> void isTrue(boolean expression, Class<T> exceptionClass, String message) {
        if (!expression) {
            Constructor<T> constructor = exceptionClass.getConstructor(String.class);
            throw constructor.newInstance(message);
        }
    }

}
