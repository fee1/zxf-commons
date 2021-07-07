package com.zxf.common.utils;

import java.lang.reflect.Field;

/**
 * 反射工具类
 * @author zhuxiaofeng
 * @date 2021/7/7
 */
public class ObjectReflectUtil {

    /**
     * 反射获取对象属性值
     * @param o 对象
     * @param fieldName 属性名
     * @return object
     */
    public static Object getFiledValue(Object o, String fieldName) throws Exception {
        Class<?> aClass = o.getClass();
        Field field = aClass.getField(fieldName);
        return field.get(o);
    }

    /**
     * 反射设置对象属性值
     * @param o 对象
     * @param fieldName 属性名
     */
    public static void setSimpleFiledValue(Object o, String fieldName, Object fieldValue) throws Exception {
        Class<?> aClass = o.getClass();
        Field field = aClass.getField(fieldName);
        field.setAccessible(true);
        field.set(o, fieldValue);
    }

    /**
     * 反射获取对象父类属性值
     * @param o 对象
     * @param fieldName 属性名
     * @return object
     */
    public static Object getSuperFiledValue(Object o, String fieldName) throws Exception {
        Class<?> superclass = o.getClass().getSuperclass();
        Field field = superclass.getField(fieldName);
        return field.get(o);
    }


}
