package com.zxf.elastic.client;

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
