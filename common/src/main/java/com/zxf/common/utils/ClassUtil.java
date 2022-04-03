package com.zxf.common.utils;

/**
 * 类工具
 *
 * @author zhuxiaofeng
 * @date 2022/4/3
 */
public class ClassUtil {

    /**
     * 是否是基本类型
     * @param clazz 类型
     * @return boolean
     */
    public static boolean isBaseType(Class<?> clazz){
        return Byte.class.equals(clazz) || Short.class.equals(clazz)
                || Character.class.equals(clazz) || Integer.class.equals(clazz) || Long.class.equals(clazz)
                || Float.class.equals(clazz) || Double.class.equals(clazz) || Boolean.class.equals(clazz);
    }

}
