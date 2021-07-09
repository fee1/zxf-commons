package com.zxf.common.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

/**
 * 反射工具类
 *
 * @author zhuxiaofeng
 * @date 2021/7/7
 */
public class ObjectReflectUtil {

    /**
     * 反射获取对象属性值
     *
     * @param o         对象
     * @param fieldName 属性名
     * @return object
     */
    public static Object getFieldValue(Object o, String fieldName) throws Exception {
        Class<?> aClass = o.getClass();
        //获取所有字段，包括：私有、受保护、默认、公有
        Field field = aClass.getDeclaredField(fieldName);
        return field.get(o);
    }

    /**
     * 反射设置对象属性值
     *
     * @param o         对象
     * @param fieldName 属性名
     */
    public static void setSimpleFieldValue(Object o, String fieldName, Object fieldValue) throws Exception {
        Class<?> aClass = o.getClass();
        Field field = aClass.getDeclaredField(fieldName);
        //暴力反射，解除私有限定,禁用安全检查提升性能
        field.setAccessible(true);
        field.set(o, fieldValue);
    }

    /**
     * 反射获取对象父类属性值
     *
     * @param o         对象
     * @param fieldName 属性名
     * @return object
     */
    public static Object getSuperFieldValue(Object o, String fieldName) throws Exception {
        Class<?> superclass = o.getClass().getSuperclass();
        Field field = superclass.getDeclaredField(fieldName);
        return field.get(o);
    }

    /**
     * 设置父类属性值
     *
     * @param o          对象
     * @param fieldName  名称
     * @param fieldValue 值
     * @throws Exception ex
     */
    public static void setSuperFieldValue(Object o, String fieldName, Object fieldValue) throws Exception {
        Class<?> superclass = o.getClass().getSuperclass();
        Field field = superclass.getField(fieldName);
        field.setAccessible(true);
        field.set(o, fieldValue);
    }

    /**
     * 通过公开的构造器其实例化对象
     *
     * @param tClass 类型
     * @param <T>    返回类型
     * @return T
     * @throws Exception ex
     */
    public static <T> T newPubilcInstanceWithoutParam(Class<T> tClass) throws Exception {
        Constructor<T> declaredConstructor = tClass.getConstructor();
        declaredConstructor.setAccessible(true);
        return declaredConstructor.newInstance();
    }

    /**
     * 通私有的构造器其实例化对象
     *
     * @param tClass 类型
     * @param <T> 返回类型
     * @return T
     * @throws Exception ex
     */
    public static <T> T newPrivateInstanceWithoutParam(Class<T> tClass) throws Exception {
        Constructor<T> declaredConstructor = tClass.getDeclaredConstructor();
        declaredConstructor.setAccessible(true);
        return declaredConstructor.newInstance();
    }

    /**
     * 通过公开有参数的构造器实例化对象
     */

    /**
     * 通过私有有参数的构造器实例化对象
     */

    /**
     * 反射获取方法
     */

}
