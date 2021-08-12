package com.zxf.cache;

import com.zxf.cache.BaseLoadingCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * 获取缓存对象，在Service使用
 * @author zhuxiaofeng
 * @date 2021/8/12
 */
public class UserCache extends BaseLoadingCache<UserCache> {

//    @Autowired
//    private Repository repository;

    //获取缓存 key名
    @Override
    public String getCacheName() {
        return null;
    }

    //
    @Override
    protected UserCache doLoad(String s) throws Exception {

//        return repository.select;
    }

}
