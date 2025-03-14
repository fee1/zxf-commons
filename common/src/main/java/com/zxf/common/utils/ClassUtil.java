package com.zxf.common.utils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * 类工具
 *
 * @author zhuxiaofeng
 * @date 2022/4/3
 */
public class ClassUtil {

    /**
     * 判断是否是一些简单的类型
     * @param type 类型
     * @return boolean
     */
    public static boolean isSimpleType(Class<?> type){
        return String.class.equals(type) || ClassUtil.isBaseType(type);
    }

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

    /**
     * 判断两个类型是否是同一个类型
     * @param sourceClass 源
     * @param tagetClass 目标类型
     * @return
     */
    public static boolean equals(Class<?> sourceClass, Class<?> tagetClass){
        return sourceClass.equals(tagetClass);
    }

    /**
     * 判断是否是某个类的实现类、继承类
     * @param superClass 超类
     * @param sourceClass 源类
     * @return
     */
    public static boolean isSubclass(Class<?> superClass, Class<?> sourceClass){
        return superClass.isAssignableFrom(sourceClass);
    }

    /**
     * 获取包下所有类
     * @param packageName 包名 例：cn.mezeron.jianzan.domain
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static List<Class<?>> find(String packageName) throws IOException, ClassNotFoundException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<Class<?>> classes = new ArrayList<>();

        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            File file = new File(resource.getFile());

            if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (File child : files) {
                    if (child.isDirectory()){
                        classes.addAll(find(packageName + "." + child.getName()));
                    }
                    if (child.getName().endsWith(".class")) {
                        String className = packageName + '.' + child.getName().substring(0, child.getName().length() - 6);
                        Class<?> aClass = Class.forName(className);
//                        if (!aClass.isAnnotationPresent(Entity.class)){
//                            continue;
//                        }
                        classes.add(aClass);
                    }
                }
            }
        }

        return classes;
    }

}
