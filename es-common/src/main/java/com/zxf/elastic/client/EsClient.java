package com.zxf.elastic.client;

import io.searchbox.client.JestResult;
import io.searchbox.core.SearchResult;

import java.io.Closeable;
import java.io.IOException;
import java.util.Map;

/**
 * ES客户端接口类
 * @author zxf
 */
public interface EsClient extends Closeable {

    /**
     * 是否存在索引
     * @param indexName 索引名称
     * @return boolean
     * @throws IOException
     */
    boolean existsIndex(String indexName) throws IOException;

    /**
     * 是否存在索引名与别名
     * @param indexName 索引名称
     * @param includeAlias 别名
     * @return boolean
     * @throws IOException
     */
    boolean existsIndex(String indexName, boolean includeAlias)throws IOException;

    /**
     * 检查是否存在别名
     * @param aliasName 别名
     * @return boolean
     * @throws IOException
     */
    boolean existsAlias(String aliasName)throws IOException;

    /**
     * 添加别名
     * @param indexName 索引名称
     * @param aliasName 别名
     * @return boolean
     * @throws IOException
     */
    boolean addAlias(String indexName, String aliasName)throws IOException;

    /**
     * 获取别名
     * @param aliasName 索引
     * @return JestResult
     * @throws IOException
     */
    JestResult getAlias(String aliasName)throws IOException;

    /**
     * 创建索引
     * @param indexName 索引名称
     * @throws IOException
     */
    void createIndex(String indexName)throws IOException;

    /**
     * 删除索引
     * @param indexName 索引名称
     * @throws IOException
     */
    void deleteIndex(String indexName)throws IOException;

    /**
     * 搜索文档 使用 queryString 的方式
     * @param indexName index索引名称
     * @param q 查询条件
     * @param fields 返回的字段
     * @param from 起始页
     * @param size 页数
     * @return
     * @throws IOException
     */
    SearchResult searchByQs(String indexName, String q, String[] fields, int from, int size)throws IOException;

//    /**
//     * 搜索文档 使用 match 的方式
//     * @param indexName index索引名称
//     * @param match 匹配条件
//     * @param fields 返回的字段
//     * @param from 起始页
//     * @param size 页数
//     * @return
//     * @throws IOException
//     */
//    SearchResult searchByMatch(String indexName, Map<String, Object> match, String[] fields, int from, int size)throws IOException;

}
