package com.zxf.common.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 反射工具类  you can use spring ReflectUtils
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
        field.setAccessible(true);
        return field.get(o);
    }

    /**
     * 反射设置对象属性值
     *
     * @param o         对象
     * @param fieldName 属性名
     */
    public static void setFieldValue(Object o, String fieldName, Object fieldValue) throws Exception {
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
        Constructor<T> constructor = tClass.getConstructor();
        constructor.setAccessible(true);
        return constructor.newInstance();
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
        Constructor<T> constructor = tClass.getDeclaredConstructor();
        constructor.setAccessible(true);
        return constructor.newInstance();
    }

    /**
     * 通过公开有参数的构造器实例化对象
     * @param <T> 返回类型
     * @param tClass 实例化的类型
     * @param params 构造器参数
     */
    public static <T> T newPubilcInstanceWithParam(Class<T> tClass, Object... params) throws Exception {
        Class<?>[] paramsClass = new Class[params.length];
        for (int i = 0; i < params.length; i++) {
            paramsClass[i] = params[i].getClass();
        }
        Constructor<T> constructor = tClass.getConstructor(paramsClass);
        constructor.setAccessible(true);
        return constructor.newInstance(params);
    }

    /**
     * 通过私有有参数的构造器实例化对象
     * @param <T> 返回类型
     * @param tClass 实例化的类型
     * @param params 构造器参数
     */
    public static <T> T newPrivateInstanceWithParam(Class<T> tClass, Object... params) throws Exception {
        Class<?>[] paramsClass = new Class[params.length];
        for (int i = 0; i < params.length; i++) {
            paramsClass[i] = params[i].getClass();
        }
        Constructor<T> constructor = tClass.getDeclaredConstructor(paramsClass);
        constructor.setAccessible(true);
        return constructor.newInstance(params);
    }

    /**
     * 获取对象定义的泛型，泛型会被擦除成Object类型，只能通过泛型new出来的类型去获取实际的类型
     */

}
