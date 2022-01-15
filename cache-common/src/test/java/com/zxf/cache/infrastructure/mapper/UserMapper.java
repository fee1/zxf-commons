package com.zxf.cache.infrastructure.mapper;

import com.zxf.cache.domain.model.UserModel;
import org.springframework.stereotype.Repository;

/**
 * @author zhuxiaofeng
 * @date 2022/1/14
 */
@Repository
public interface UserMapper {

    UserModel selectUser(String name, String age);

}
