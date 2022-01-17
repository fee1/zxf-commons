package com.zxf.cache.domain.service;

import com.zxf.cache.domain.cache.UserCache;
import com.zxf.cache.domain.model.UserModel;
import com.zxf.cache.infrastructure.mapper.UserMapper;
import com.zxf.test.BaseTest;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * cache 使用测试
 *
 *
 * @author zhuxiaofeng
 * @date 2022/1/14
 */
public class CacheServiceTest extends BaseTest {

    @Mock
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @Test
    public void useCacheKeyTest() throws Exception {
        Mockito.doReturn(new UserModel("xiaoming","18" ))
                .when(this.userMapper.selectUser(Mockito.anyString(), Mockito.anyString()));
        UserModel userModel = this.userService.searchUser("xiaoming", "18");
        System.out.println(userModel);
    }

}
