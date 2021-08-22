package com.zxf.common.utils;

import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * bean 转换工具类
 * @author 朱晓峰
 */
public class BeanUtil {

    /**
     * map to bean
     * @return T
     */
    public static <T> T mapToBean(Map<String, Object> map, Class<T> clazz){
        JSONObject jsonObject = new JSONObject(map);
        return jsonObject.toJavaObject(clazz);
    }

    /**
     * maps to beans
     * @return List<T></T>
     */
    public static <T> List<T> mapsToBeans(List<? extends Map<String, Object>> list, Class<T> clazz){
        List<T> result = new LinkedList<>();
        list.forEach(item -> {
            JSONObject jsonObject = new JSONObject(item);
            result.add(jsonObject.toJavaObject(clazz));
        });
        return result;
    }

    public static <T> T newInstance(Class<T> clazz){
        try {
            Constructor<T> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            return constructor.newInstance();
        }catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | InstantiationException var2){
            throw new RuntimeException(String.format("%s , 请添加无参构造器! ", clazz.getName()));
        }
    }

}
