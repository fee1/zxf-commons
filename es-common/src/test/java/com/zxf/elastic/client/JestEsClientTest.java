package com.zxf.elastic.client;


import com.alibaba.fastjson.JSONObject;
import com.zxf.test.BaseTest;
import io.searchbox.client.JestClient;
import lombok.SneakyThrows;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


/**
 * @author zhuxiaofeng
 * @date 2022/2/18
 */
public class JestEsClientTest extends BaseTest {

    @Autowired
    private JestEsClient jestEsClient;

    @Test
    @SneakyThrows
    public void existsIndex() {
        boolean existsIndex = this.jestEsClient.existsIndex("twitter");
        Assert.assertTrue("twitter这个索引应该存在才对", existsIndex);
    }

    @Test
    @SneakyThrows
    public void findIndexList() {
        List<String> twitter = this.jestEsClient.findIndexList("twitter");
        Assert.assertTrue("twitter这个索引应该存在才对", !twitter.isEmpty());
    }

    @Test
    @SneakyThrows
    public void createIndex() {
        this.jestEsClient.createIndex("test_create_index");
        boolean existsIndex = this.jestEsClient.existsIndex("test_create_index");
        Assert.assertTrue("test_create_index这个索引应该存在才对", existsIndex);
    }

    @Test
    @SneakyThrows
    public void addAlias(){
        boolean isSuccessed = this.jestEsClient.addAlias("test_create_index", "test_create_index1");
        Assert.assertTrue("添加索引别名没有成功", isSuccessed);
    }

    @Test
    @SneakyThrows
    public void existsAlias(){
        boolean existsAlias = this.jestEsClient.existsAlias("test_create_index1");
        Assert.assertTrue("索引别名应该存在才对", existsAlias);
    }

    @Test
    @SneakyThrows
    public void getAlias(){
        Assert.assertEquals(this.jestEsClient.getAlias("test_create_index1").getJsonObject().toString(),
                "{\"test_create_index\":{\"aliases\":{\"test_create_index1\":{}}}}");
    }

    @Test
    @SneakyThrows
    public void deleteIndex() {
        this.jestEsClient.deleteIndex("test_create_index");
        boolean existsIndex = this.jestEsClient.existsIndex("test_create_index");
        Assert.assertTrue("test_create_index这个索引应该已经删除了才对", !existsIndex);
    }

}