package com.zxf.elastic.client;

import io.searchbox.client.JestResult;

import java.io.Closeable;
import java.io.IOException;

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

}
