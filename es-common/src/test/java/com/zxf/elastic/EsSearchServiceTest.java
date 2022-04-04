package com.zxf.elastic;

import com.alibaba.fastjson.JSONObject;
import com.zxf.elastic.builder.EsQueryStringBuilder;
import com.zxf.elastic.model.Search;
import com.zxf.elastic.model.User;
import com.zxf.test.BaseTest;
import lombok.SneakyThrows;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author zhuxiaofeng
 * @date 2022/3/3
 */
public class EsSearchServiceTest extends BaseTest {

    @Autowired
    private EsSearchService esSearchService;

    @Test
    @SneakyThrows
    public void testSearchOne(){
        String q = EsQueryStringBuilder.create().createCriteria()
                .andEq("age", 20).formatQuery();
        Search search = new Search("twitter", q, new String[]{"*"}, 0, 10, null);
        User user = this.esSearchService.searchOne(search, User.class);
        System.out.println(JSONObject.toJSONString(user));
    }

    @Test
    @SneakyThrows
    public void testSearch(){
        String q = EsQueryStringBuilder.create().createCriteria()
                .andEq("age", 20).formatQuery();

    }

}