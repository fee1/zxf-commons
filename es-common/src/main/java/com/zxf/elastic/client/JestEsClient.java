package com.zxf.elastic.client;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.zxf.elastic.config.JestEsProperties;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.JestResult;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.Cat;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.DeleteIndex;
import io.searchbox.indices.IndicesExists;
import io.searchbox.indices.aliases.AddAliasMapping;
import io.searchbox.indices.aliases.AliasExists;
import io.searchbox.indices.aliases.GetAliases;
import io.searchbox.indices.aliases.ModifyAliases;
import io.searchbox.params.Parameters;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * gson json格式化
     */
    private final Gson gson;

    /**
     * ES基本配置信息类
     */
    private final JestEsProperties jestEsProperties;

    private static final String DEFAULT_DOC_TYPE = "_doc";


    public JestEsClient(ObjectProvider<Gson> gsonObjectProvider, JestEsProperties jestEsProperties){
        this.gson = gsonObjectProvider.getObject();
        this.jestEsProperties = jestEsProperties;
        this.buildClient();
    }

    public JestEsClient(JestEsProperties jestEsProperties){
        this.jestEsProperties = jestEsProperties;
        this.gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        this.buildClient();
    }

    /**
     * 建立es连接
     */
    protected final void buildClient(){
        HttpClientConfig clientConfig = new HttpClientConfig.Builder(this.jestEsProperties.getUris())
                .discoveryEnabled(false)
                .gson(this.gson)
                .multiThreaded(this.jestEsProperties.isMultiThreaded())
                .connTimeout((int)this.jestEsProperties.getConnectionTimeout().toMillis())
                .readTimeout((int)this.jestEsProperties.getReadTimeout().toMillis())
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
     * Head http url: http://192.168.137.135:9200/twitter
     * response body: responseCode = 200
     *
     * 查找字段名 true(查询其为别名) false(查询)
     * @param indexName 索引名称
     * @param includeAlias 别名
     * @return boolean
     * @throws IOException
     */
    @Override
    public boolean existsIndex(String indexName, boolean includeAlias) throws IOException {
        boolean indexNameExists = false;
        IndicesExists.Builder builder = new IndicesExists.Builder(indexName);
        JestResult result = this.jestClient.execute(builder.build());
        int responseCode = result.getResponseCode();
        if (responseCode == 200){
            indexNameExists = true;
        }
        boolean aliasNameExists = this.existsAlias(indexName);
        return indexNameExists || aliasNameExists;
    }

    /**
     * 检查是否存在别名
     * Head http url: http://192.168.137.135:9200/_alias/test_create_index1
     * response body: responseCode = 200
     *
     * @param aliasName 别名
     * @return
     * @throws IOException
     */
    @Override
    public boolean existsAlias(String aliasName) throws IOException {
        AliasExists.Builder builder = new AliasExists.Builder().alias(aliasName);
        JestResult jestResult = this.jestClient.execute(builder.build());
        return jestResult.getResponseCode() == 200;
    }


    /**
     * 添加别名
     *
     * Post http url: http://192.168.137.135:9200/_aliases
     * request body:
     * {
     *     "actions": [
     *         {
     *             "add": {
     *                 "index": "test_create_index",
     *                 "alias": "test_create_index1"
     *             }
     *         }
     *     ]
     * }
     * response body:
     * {"acknowledged":true}
     *
     *
     *
     * @param indexName 索引名称
     * @param aliasName 别名
     * @return boolean
     * @throws IOException
     */
    @Override
    public boolean addAlias(String indexName, String aliasName) throws IOException {
        AddAliasMapping.Builder addAliasBuild = new AddAliasMapping.Builder(indexName, aliasName);
        ModifyAliases.Builder builder = new ModifyAliases.Builder(addAliasBuild.build());
        JestResult result = this.jestClient.execute(builder.build());
        return result.isSucceeded();
    }

    /**
     * 获取别名 (相同别名的索引可能会有多个， 但是别名不能和索引名称重复)
     *
     * get http url: http://192.168.137.135:9200/_alias/test_create_index1
     * response body:
     * {
     *     "twitter": {
     *         "aliases": {
     *             "test_create_index1": {}
     *         }
     *     },
     *     "test_create_index": {
     *         "aliases": {
     *             "test_create_index1": {}
     *         }
     *     }
     * }
     *
     * get http url: http://192.168.137.135:9200/test_create_index/_alias
     * response body:
     * {
     *     "test_create_index": {
     *         "aliases": {
     *             "test_create_index1": {},
     *             "test_create_index2": {}
     *         }
     *     }
     * }
     *
     * @param aliasName 索引
     * @return JestResult
     * @throws IOException
     */
    @Override
    public JestResult getAlias(String aliasName) throws IOException {
        GetAliases.Builder getAliasBuild = new GetAliases.Builder().addAlias(aliasName);
        return this.jestClient.execute(getAliasBuild.build());
    }

    /**
     * 查找ES字段列
     * Get http url: http://192.168.137.135:9200/_cat/indices/twitter
     * response body:
     * [
     *     {
     *         "health": "yellow",
     *         "status": "open",
     *         "index": "twitter",
     *         "uuid": "31D3WIsZROSS6eMNinj-bw",
     *         "pri": "1",
     *         "rep": "1",
     *         "docs.count": "6",
     *         "docs.deleted": "0",
     *         "store.size": "12.2kb",
     *         "pri.store.size": "12.2kb"
     *     }
     * ]
     *
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

    /**
     * 创建索引
     *
     * Put http url: http://192.168.137.135:9200/test_create_index
     * response body:
     * {
     *     "acknowledged": true,
     *     "shards_acknowledged": true,
     *     "index": "test_create_index"
     * }
     *
     * @param indexName 索引名称
     * @return boolean
     * @throws IOException
     */
    @Override
    public void createIndex(String indexName) throws IOException {
        CreateIndex createIndex = new CreateIndex.Builder(indexName).build();
        JestResult result = this.jestClient.execute(createIndex);
        log.info("es 创建索引结果-> {}", result);
    }

    /**
     * 删除索引
     *
     * Delete http url: http://192.168.137.135:9200/test_create_index
     * response body: {"acknowledged":true}
     *
     *
     * @param indexName 索引名称
     * @throws IOException
     */
    @Override
    public void deleteIndex(String indexName) throws IOException {
        DeleteIndex deleteIndex = new DeleteIndex.Builder(indexName).build();
        JestResult result = this.jestClient.execute(deleteIndex);
        log.info("es 删除索引结果-> {}", result);
    }

    /**
     * 搜索文档 使用 queryString 的方式
     * Post http url: http://192.168.137.135:9200/twitter/_search
     * request body:
     * {
     *     "size": 10,
     *     "query": {
     *         "query_string": {
     *             "query": "age:20"
     *         }
     *     },
     *     "_source": false,
     *     "from": 0,
     *     "fields": [
     *         "*"
     *     ]
     * }
     *
     * response body:
     * {
     *     "took": 6,
     *     "timed_out": false,
     *     "_shards": {
     *         "total": 1,
     *         "successful": 1,
     *         "skipped": 0,
     *         "failed": 0
     *     },
     *     "hits": {
     *         "total": {
     *             "value": 1,
     *             "relation": "eq"
     *         },
     *         "max_score": 1.0,
     *         "hits": [
     *             {
     *                 "_index": "twitter",
     *                 "_id": "1",
     *                 "_score": 1.0,
     *                 "_source": {},
     *                 "fields": {
     *                     "country": [
     *                         "中国"
     *                     ],
     *                     ...
     *                 }
     *             }
     *         ]
     *     }
     * }
     *
     * @param indexName index索引名称
     * @param q 查询条件
     * @param fields 返回的字段
     * @param from 起始页
     * @param size 页数
     * @return
     * @throws IOException
     */
    @Override
    public SearchResult searchByQs(String indexName, String q, String[] fields, int from, int size) throws IOException {
        String queryString = this.buildQueryString(q, fields, from, size);
        return this.search(indexName, queryString);
    }

//    /**
//     * 搜索文档使用match的方式
//     * Post http url: http://192.168.137.135:9200/twitter/_search
//     * request body:
//     * {
//     *     "size": 10,
//     *     "query": {
//     *         "match": {
//     *             "age": 20
//     *         }
//     *     },
//     *     "_source": false,
//     *     "from": 0,
//     *     "fields": [
//     *         "*"
//     *     ]
//     * }
//     *
//     * response body:
//     * {
//     *     "took": 1,
//     *     "timed_out": false,
//     *     "_shards": {
//     *         "total": 1,
//     *         "successful": 1,
//     *         "skipped": 0,
//     *         "failed": 0
//     *     },
//     *     "hits": {
//     *         "total": {
//     *             "value": 1,
//     *             "relation": "eq"
//     *         },
//     *         "max_score": 1.0,
//     *         "hits": [
//     *             {
//     *                 "_index": "twitter",
//     *                 "_id": "1",
//     *                 "_score": 1.0,
//     *                 "fields": {
//     *                     "country": [
//     *                         "中国"
//     *                     ],
//     *                     ...
//     *                 }
//     *             }
//     *         ]
//     *     }
//     * }
//     *
//     * @param indexName index索引名称
//     * @param match 匹配条件
//     * @param fields 返回的字段
//     * @param from 起始页
//     * @param size 页数
//     * @return
//     * @throws IOException
//     */
//    @Override
//    public SearchResult searchByMatch(String indexName, Map<String, Object> match, String[] fields, int from, int size) throws IOException {
//        String jsonBody = this.buildQueryMatchBody(match, fields, from, size);
//        return this.search(indexName, jsonBody);
//    }

    /**
     * 搜索文档 使用queryString 的方式
     *
     * @param indexName index索引名称
     * @param jsonBody 查询jsonString
     * @return JestResult
     * @throws IOException
     */
    private SearchResult search(String indexName, String jsonBody) throws IOException {
        Search.Builder search = new Search.Builder(jsonBody)
                .addIndex(indexName);
        return this.jestClient.execute(search.build());
    }

    /**
     * 构建查询ES的 DSL - query string
     *
     * 我们可以使用 fields 来指定返回的字段，而不用 _source。这样做更加高效。
     *
     * @param q 查询语句
     * @param fields 返回字段
     * @param from 起始位置
     * @param size 页数
     * @return string
     */
    private String buildQueryString(String q, String[] fields, int from, int size){
        Map<String, Object> root = new HashMap<>();
        Map<String, Object> query = new HashMap<>();
        Map<String, Object> queryString = new HashMap<>();
        root.put("query", queryString);
        queryString.put("query_string", query);
        query.put("query", q);
        root.put("_source", false);
        if (fields == null || fields.length == 0) {
            fields = new String[]{"*"};
        }
        root.put("fields", fields);

        if (size >= 0) {
            root.put(Parameters.FROM, from);
            root.put(Parameters.SIZE, size);
        }
        return JSON.toJSONString(root);
    }

//    /**
//     * 构建es 查询使用的 macth json
//     *
//     * 我们可以使用 fields 来指定返回的字段，而不用 _source。这样做更加高效。
//     *
//     * @param match 匹配条件
//     * @param fields 返回字段
//     * @param from 起始位置
//     * @param size 页数
//     * @return
//     */
//    private String buildQueryMatchBody(Map<String, Object> match, String[] fields, int from, int size){
//        Map<String, Object> root = new HashMap<>();
//        Map<String, Object> query = new HashMap<>();
//        root.put("query", query);
//        query.put("match", match);
//        root.put("_source", false);
//        if (fields == null || fields.length == 0) {
//            fields = new String[]{"*"};
//        }
//        root.put("fields", fields);
//
//        if (size >= 0) {
//            root.put("from", from);
//            root.put("size", size);
//        }
//        return JSON.toJSONString(root);
//    }

    // todo 理解
    private static class FixIndicesBuilder extends Cat.IndicesBuilder {
        @Override
        public Cat build() {
            return super.build();
        }
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
    }
}
