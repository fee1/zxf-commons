package com.zxf.cache;

/**
 * 拓展理解缓存，自定义key
 *
 * @author zhuxiaofeng
 * @date 2025/3/28
 */
public interface CacheKey {

    /**
     * 将字符串key解析成自定义的Cachekey对象
     * @param key
     * @return
     */
    CacheKey parseFromKey(String key);

    /**
     * 将Cachekey对象转成字符串key
     * @return
     */
    String toKey();

}
