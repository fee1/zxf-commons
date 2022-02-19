package com.zxf.elastic.client;


import com.zxf.test.BaseTest;
import io.searchbox.client.JestClient;
import lombok.SneakyThrows;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author zhuxiaofeng
 * @date 2022/2/18
 */
public class JestEsClientTest extends BaseTest {

//    @Autowired
//    private JestClient jestClient;

    @Autowired
    private JestEsClient jestEsClient;

    @Test
    @SneakyThrows
    public void existsIndex() {
        boolean twitter = jestEsClient.existsIndex("twitter");
        System.out.println(twitter);
    }

    @Test
    public void findIndexList() {
    }

    @Test
    public void createIndex() {

    }

    @Test
    public void deleteIndex() {

    }
}