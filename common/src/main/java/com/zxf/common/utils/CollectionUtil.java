package com.zxf.common.utils;

import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Map;

/**
 * 集合工具类
 *
 * @author zhuxiaofeng
 * @date 2021/11/12
 */
public class CollectionUtil extends CollectionUtils {

    /**
     * 集合不为空
     * @param collection 集合
     * @return boolean
     */
    public static boolean isNotEmpty(@Nullable Collection<?> collection){
        return !isEmpty(collection);
    }

    /**
     * 集合不为空
     * @param map 集合
     * @return boolean
     */
    public static boolean isNotEmpty(@Nullable Map<?, ?> map){
        return !isEmpty(map);
    }

}
