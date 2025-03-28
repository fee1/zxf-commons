package com.zxf.cache.domain.cache.key;

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

    public static String toKey(String name, Object age){
        return String.format("%s/%s",name, age);
    }

}
