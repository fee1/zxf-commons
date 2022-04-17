package com.zxf.elastic.config;

import com.google.gson.Gson;
import com.zxf.elastic.EsFastSearchServiceImpl;
import com.zxf.elastic.EsSearchServiceImpl;
import com.zxf.elastic.client.JestEsClient;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * es 引入配置类
 *
 * @author zhuxiaofeng
 * @date 2022/2/18
 */
@Configuration(proxyBeanMethods = false) //直接返回新实例对象
@EnableConfigurationProperties(JestEsProperties.class)
@ConditionalOnProperty(name= "elastic.auto.config.enable", matchIfMissing = true)
public class EsClientConfiguration {

    @Bean
    public JestEsClient jestEsClient(ObjectProvider<Gson> gsonObjectProvider, JestEsProperties jestEsProperties){
        Gson gson = gsonObjectProvider.getObject();
        if (gson == null){
            return new JestEsClient(jestEsProperties);
        }
        return new JestEsClient(gsonObjectProvider, jestEsProperties);
    }

    @Bean
    public EsFastSearchServiceImpl esFastSearchService(JestEsClient esClient){
        return new EsFastSearchServiceImpl(esClient);
    }

    @Bean
    public EsSearchServiceImpl esSearchService(JestEsClient esClient){
        return new EsSearchServiceImpl(esClient);
    }

}
