package com.zxf.elastic;

import com.alibaba.fastjson.JSONObject;
import com.zxf.elastic.builder.EsQueryStringBuilder;
import com.zxf.elastic.model.Page;
import com.zxf.elastic.model.SearchModel;
import com.zxf.elastic.model.User;
import com.zxf.test.BaseTest;
import lombok.SneakyThrows;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author zhuxiaofeng
 * @date 2022/3/3
 */
public class EsFastSearchModelServiceTest extends BaseTest {

    @Autowired
    private EsFastSearchService esFastSearchService;

    @Test
    @SneakyThrows
    public void testSearchOne(){
        String q = EsQueryStringBuilder.create().createCriteria()
                .andEq("age", 20).formatQuery();
        SearchModel search = new SearchModel("twitter", q, new String[]{"*"}, 0, 10, null);
        User user = this.esFastSearchService.searchOne(search, User.class);
        System.out.println(JSONObject.toJSONString(user));
    }

    @Test
    @SneakyThrows
    public void testSearch(){
        String q = EsQueryStringBuilder.create().createCriteria()
                .andEq("country", "中国").formatQuery();
        SearchModel search = new SearchModel("twitter", q, new String[]{"*"}, 0, 10, null);
        Page<User> users = esFastSearchService.search(search, User.class);
        System.out.println(JSONObject.toJSONString(users));
    }

}