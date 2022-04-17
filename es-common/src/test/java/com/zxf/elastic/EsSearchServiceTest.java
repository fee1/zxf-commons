package com.zxf.elastic;

import com.zxf.elastic.builder.EsQueryStringBuilder;
import com.zxf.elastic.model.Page;
import com.zxf.elastic.model.SearchModel;
import com.zxf.elastic.model.User;
import com.zxf.test.BaseTest;
import lombok.SneakyThrows;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author zhuxiaofeng
 * @date 2022/4/17
 */
public class EsSearchServiceTest extends BaseTest {

    @Autowired
    private EsSearchService searchService;

    @Test
    @SneakyThrows
    public void testSearchOne(){
        String q = EsQueryStringBuilder.create().createCriteria()
                .andEq("age", 20).formatQuery();
        SearchModel search = new SearchModel("twitter", q, new String[]{"*"}, 0, 10);
        User user = searchService.searchOne(search, User.class);
        System.out.println(user);
    }

    @Test
    @SneakyThrows
    public void testSearch(){
        String q = EsQueryStringBuilder.create().createCriteria()
                .andEq("country", "中国").formatQuery();
        SearchModel search = new SearchModel("twitter", q, new String[]{"*"}, 0, 10);
        Page<User> users = searchService.search(search, User.class);
        System.out.println(users);
    }

}
