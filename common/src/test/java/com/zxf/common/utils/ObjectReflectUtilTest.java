package com.zxf.common.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author zhuxiaofeng
 * @date 2021/7/8
 */
public class ObjectReflectUtilTest {

    @Test
    public void getFieldValue() {

    }

    @Test
    public void setSimpleFieldValue() {

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class Student extends People{

        private String IdCard;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class People{

        private String name;

        private int age;

    }
}