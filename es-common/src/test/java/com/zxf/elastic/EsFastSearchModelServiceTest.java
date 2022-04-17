package com.zxf.elastic;

import com.alibaba.fastjson.JSONObject;
import com.zxf.elastic.builder.EsQueryStringBuilder;
import com.zxf.elastic.model.Page;
import com.zxf.elastic.model.SearchModel;
import com.zxf.elastic.model.Sort;
import com.zxf.elastic.model.User;
import com.zxf.test.BaseTest;
import lombok.SneakyThrows;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

/**
 * @author zhuxiaofeng
 * @date 2022/3/3
 */
public class EsFastSearchModelServiceTest extends BaseTest {

    @Autowired
    private EsFastSearchServiceImpl esFastSearchServiceImpl;

    /**
     * 测试简单查询
     */
    @Test
    @SneakyThrows
    public void testSearchOne(){
        String q = EsQueryStringBuilder.create().createCriteria()
                .andEq("age", 20).formatQuery();
        SearchModel search = new SearchModel("twitter", q, new String[]{"*"}, 0, 10);
        User user = this.esFastSearchServiceImpl.searchOne(search, User.class);
        System.out.println(JSONObject.toJSONString(user));
    }

    /**
     * 测试复杂查询
     */
    @Test
    @SneakyThrows
    public void testSearch(){
        String q = EsQueryStringBuilder.create().createCriteria()
                .andEq("country", "中国").formatQuery();
        SearchModel search = new SearchModel("twitter", q, new String[]{"*"}, 0, 10);
        Page<User> users = esFastSearchServiceImpl.search(search, User.class);
        System.out.println(JSONObject.toJSONString(users));
    }

    /**
     * 测试排序
     */
    @Test
    @SneakyThrows
    public void testSearchSort(){
        String q = EsQueryStringBuilder.create().createCriteria()
                .andEq("country", "中国").formatQuery();
        Sort age = new Sort("age", Sort.Sorting.DESC);
        Sort uid = new Sort("uid", Sort.Sorting.ASC);
        SearchModel search = new SearchModel("twitter", q, new String[]{"*"}, 0, 10);
        search.setSortList(Arrays.asList(age, uid));
        Page<User> users = esFastSearchServiceImpl.search(search, User.class);
        System.out.println(JSONObject.toJSONString(users));
    }

    /**
     * 测试高亮
     */
    @Test
    @SneakyThrows
    public void testHighlight(){
        String q = EsQueryStringBuilder.create().createCriteria()
                .andEq("country", "中国").formatQuery();
        SearchModel search = new SearchModel("twitter", q, new String[]{"*"}, 0, 10);
        search.setHighlightField(Arrays.asList("country", "address"));
        Page<User> users = esFastSearchServiceImpl.search(search, User.class);
        System.out.println(users);
    }

}