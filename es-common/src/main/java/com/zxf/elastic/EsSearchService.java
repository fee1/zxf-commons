package com.zxf.elastic;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zxf.common.utils.CollectionUtil;
import com.zxf.elastic.client.JestEsClient;
import com.zxf.elastic.exception.SearchException;
import com.zxf.elastic.model.Hit;
import com.zxf.elastic.model.Page;
import com.zxf.elastic.model.SearchModel;
import io.searchbox.core.SearchResult;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhuxiaofeng
 * @date 2022/4/4
 */
@Slf4j
public class EsSearchService implements SearchService {

    private JestEsClient esClient;

    public EsSearchService(JestEsClient esClient){
        this.esClient = esClient;
    }

    /**
     * 查询es
     * @param searchModel 查询类
     * @return SearchResult
     */
    @Override
    public SearchResult searchES(SearchModel searchModel){
        try {
            return esClient.searchSource(searchModel);
        }catch (IOException e){
            throw new SearchException("查询异常", e);
        }
    }

    /**
     * 查询 _score 最高的一个结果
     * @param search 查询类
     * @param rClass 返回类型
     * @param <R> 返回类型
     * @return R
     */
    @Override
    public <R> R searchOne(SearchModel search, Class<R> rClass)throws Exception{
        SearchResult result = searchES(search);
        return result.getSourceAsObject(rClass, false);
    }

    @Override
    public <R> Page<R> search(SearchModel searchModel, Class<R> rClass) throws Exception {
        SearchResult result = searchES(searchModel);
        String jsonString = result.getJsonString();
        JSONObject jsonObject = JSON.parseObject(jsonString);
        JSONObject hits = jsonObject.getJSONObject("hits");
        List<R> rList = result.getSourceAsObjectList(rClass, false);
        Page<R> rPage = new Page<>();
        rPage.setData(rList);
        rPage.setFrom(searchModel.getFrom());
        rPage.setSize(searchModel.getSize());
        //总数
        JSONObject total = hits.getJSONObject("total");
        rPage.setTotal(total.getLong("value"));
        rPage.setTotalPage();
        return rPage;
    }


}
