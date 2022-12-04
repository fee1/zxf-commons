package com.zxf.common.utils;

import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.CallbackFilter;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.NoOp;

/**
 * spring cglib增强类工具
 *
 * @author zhuxiaofeng
 * @date 2022/12/4
 */
public class SpringEnhancerUtil {

    /**
     * 类型增强
     * @param superclass 需要增强的类
     * @param filter 筛选出需要增强的方法，实现accept方法
     * @param callbackTypes 回调方法的类型
     * @return
     */
    public static Object enhancer(Class superclass, CallbackFilter filter, Class[] callbackTypes){
        for (Class callbackType : callbackTypes) {
            if (!ClassUtil.equals(callbackType, Callback.class)){
                throw new RuntimeException("CallbackTypes parameter type must be Callback.class");
            }
        }
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(superclass);
        enhancer.setCallbackFilter(filter);
        enhancer.setCallbackTypes(callbackTypes);
        return (Class<?>) enhancer.createClass();
    }

    /**
     * 简单的类型增强
     *
     * @param superclass 需要增强的类
     * @param filter 筛选出需要增强的方法，实现accept方法
     * @param callbackTypes 回调方法的类型
     * @return
     */
    public static Object simpleEnhancer(Class superclass, CallbackFilter filter, Class callbackTypes){
        return enhancer(superclass, filter, new Class[]{callbackTypes, NoOp.class});
    }

}
