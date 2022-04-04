package com.zxf.elastic;

import com.zxf.elastic.client.JestEsClient;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhuxiaofeng
 * @date 2022/4/4
 */
@Slf4j
public class EsSearchService {

    private JestEsClient esClient;

    public EsSearchService(JestEsClient esClient){
        this.esClient = esClient;
    }

}
