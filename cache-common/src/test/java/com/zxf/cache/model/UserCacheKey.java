package com.zxf.cache.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author zhuxiaofeng
 * @date 2021/8/12
 */
@Data
//@AllArgsConstructor
public class UserCacheKey {

    private String name;

    private String age;

    public UserCacheKey(String name, String age){
        this.name = name;
        this.age = age;
    }

    public static UserCacheKey parseFromKey(String key){
        String[] keys = key.split("/");
        return new UserCacheKey(keys[0], keys[1]);
    }

    public static String toKey(String name, String age){
        return String.format("%s/%s",name, age);
    }

}
