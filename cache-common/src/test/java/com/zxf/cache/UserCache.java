package com.zxf.cache;

import com.zxf.cache.entity.UserModel;


/**
 * 获取缓存对象，在Service使用
 * @author zhuxiaofeng
 * @date 2021/8/12
 */
public class UserCache extends BaseLoadingCache<UserModel> {

//    @Autowired
//    private Repository repository;

    //获取缓存 key名
    @Override
    public String getCacheName() {
        return null;
    }

    //
    @Override
    protected UserModel doLoad(String s) throws Exception {
        return null;
//        return repository.select;
    }

}
