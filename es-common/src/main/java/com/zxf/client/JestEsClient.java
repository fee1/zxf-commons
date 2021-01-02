package com.zxf.client;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.zxf.config.EsConfig;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.JestResult;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.Cat;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * ElasticSearch客户端链接类
 * @author zxf
 */
@Slf4j
public class JestEsClient implements EsClient{

    /**
     * 连接ElasticSearch类
     */
    private JestClient jestClient;

    /**
     * gson final ??
     */
    private final Gson gson;

    /**
     * ES基本配置信息类
     */
    private final EsConfig esConfig;

    private static final String DEFAULT_DOC_TYPE = "_doc";


    public JestEsClient(ObjectProvider<Gson> gsonObjectProvider, EsConfig esConfig){
        this.gson = gsonObjectProvider.getObject();
        this.esConfig = esConfig;
        this.buildClient();
    }

    public JestEsClient(EsConfig esConfig){
        this.esConfig = esConfig;
        this.gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        this.buildClient();
    }

    /**
     * 建立es连接
     */
    protected void buildClient(){
        HttpClientConfig clientConfig = new HttpClientConfig.Builder(this.esConfig.getUris())
                .discoveryEnabled(false)
                .gson(this.gson)
                .multiThreaded(this.esConfig.isMultiThreaded())
                .connTimeout((int)this.esConfig.getConnectionTimeout().toMillis())
                .readTimeout((int)this.esConfig.getReadTimeout().toMillis())
                .build();

        JestClientFactory factory = new JestClientFactory();
        factory.setHttpClientConfig(clientConfig);
        this.jestClient = factory.getObject();
    }

    /**
     * 是否存在索引
     * @param indexName 索引名称
     * @return boolean
     * @throws IOException
     */
    @Override
    public boolean existsIndex(String indexName) throws IOException {
        return this.existsIndex(indexName, true);
    }

    /**
     * 查找字段名 true(查询其为别名) false(查询)
     * @param indexName 索引名称
     * @param includeAlias 别名
     * @return boolean
     * @throws IOException
     */
    @Override
    public boolean existsIndex(String indexName, boolean includeAlias) throws IOException {
        if (includeAlias) {
            String queryString = this.buildQueryString("*:*", 0, 0);
            Search.Builder builder = new Search.Builder(queryString)
                    .addIndex(indexName).addType(DEFAULT_DOC_TYPE)
                    .addSourceIncludePattern("_id");
            SearchResult result = this.jestClient.execute(builder.build());
            return result.getResponseCode() != 404;
        } else {
            List<String> indexList = this.findIndexList(indexName);
            return indexList.contains(indexName);
        }
    }

    /**
     * 查找ES字段列
     * @param indexNamePattern indexNamePattern
     * @return list<String>
     * @throws IOException IOException
     */
    public List<String> findIndexList(String indexNamePattern) throws IOException{
        Cat action = new FixIndicesBuilder().addIndex(indexNamePattern).build();
        JestResult jestResult = this.jestClient.execute(action);

        if (!jestResult.isSucceeded()){
            return new ArrayList<String>(0);
        }

        JsonArray jsonArray = jestResult.getJsonObject().getAsJsonArray("result");
        ArrayList<String> indexList = new ArrayList<>(jsonArray.size());
        jsonArray.forEach(item -> indexList.add(((JsonObject)item).get("index").getAsString()));
        return indexList;
    }

    private static class FixIndicesBuilder extends Cat.IndicesBuilder {
        @Override
        public Cat build() {
            return super.build();
        }
    }

    /**
     * 构建查询ES的json
     * @param q 查询语句
     * @param from 起始位置
     * @param size 页数
     * @return string
     */
    private String buildQueryString(String q, int from, int size){
        HashMap<String, Object> root = new HashMap<>();
        HashMap<String, Object> queryString = new HashMap<>();
        HashMap<String, Object> query = new HashMap<>();
        root.put("root", queryString);

        if (size >= 0) {
            root.put("from", from);
            root.put("size", size);
        }
        queryString.put("query_string", query);
        query.put("query", q);
        return JSON.toJSONString(root);
    }

    /**
     * 创建索引
     * @param indexName 索引名称
     * @throws IOException
     */
    @Override
    public void createIndex(String indexName) throws IOException {

    }

    /**
     * 删除索引
     * @param indexName 索引名称
     * @throws IOException
     */
    @Override
    public void deleteIndex(String indexName) throws IOException {

    }

    /**
     * 关闭资源
     * @throws IOException
     */
    @Override
    public void close() throws IOException {
        try {
            if (this.jestClient != null) {
                this.jestClient.close();
            }
        } catch (IOException e) {
            log.error("es客户端连接关闭失败，cause："+e.getMessage());
        }
//        IOUtils.closeQuietly(this.jestClient);
    }
}
