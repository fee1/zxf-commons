package com.zxf.client;

import com.google.gson.Gson;
import com.zxf.EsConfig;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import org.springframework.beans.factory.ObjectProvider;

import java.io.IOException;

/**
 * ElasticSearch客户端链接类
 * @author zxf
 */
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


    public JestEsClient(ObjectProvider<Gson> gsonObjectProvider, EsConfig esConfig){
        this.gson = gsonObjectProvider.getObject();
        this.esConfig = esConfig;
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

    @Override
    public boolean existsIndex(String indexName) throws IOException {
        return false;
    }

    @Override
    public boolean existsIndex(String indexName, boolean includeAlias) throws IOException {
        return false;
    }

    @Override
    public void createIndex(String indexName) throws IOException {

    }

    @Override
    public void deleteIndex(String indexName) throws IOException {

    }

    @Override
    public void close() throws IOException {

    }
}
