package com.zxf.cache.domain.cache.key;

import com.zxf.cache.CacheKey;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author zhuxiaofeng
 * @date 2025/3/28
 */
@Data
@AllArgsConstructor
public class UserExtCacheKey implements CacheKey {

    private String name;

    private Integer age;

    @Override
    public UserExtCacheKey parseFromKey(String key) {
        String[] keys = key.split("/");
        return new UserExtCacheKey(keys[0], Integer.valueOf(keys[1]));
    }

    @Override
    public String toKey() {
        return String.format("%s/%s", name, age);
    }
}
