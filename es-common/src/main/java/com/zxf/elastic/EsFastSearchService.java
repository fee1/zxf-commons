package com.zxf.elastic;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zxf.common.utils.ClassUtil;
import com.zxf.common.utils.CollectionUtil;
import com.zxf.common.utils.ObjectReflectUtil;
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
 * @date 2022/2/18
 */
@Slf4j
public class EsFastSearchService implements SearchService {

    private JestEsClient esClient;

    public EsFastSearchService(JestEsClient esClient){
        this.esClient = esClient;
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
        String jsonString = result.getJsonString();
        JSONObject jsonObject = JSON.parseObject(jsonString);
        JSONObject hits = jsonObject.getJSONObject("hits");
        JSONArray hitsHits = hits.getJSONArray("hits");
        Hit hit  = hitsHits.getObject(0, Hit.class);
        JSONObject fields = hit.getFields();
        //都是设置的数组值
//        R r1 = fields.toJavaObject(rClass);
        R r = null;
        try {
            r = rClass.getConstructor().newInstance();
        } catch (Exception e){
            throw new SearchException("实例化异常", e);
        }
        for (Field declaredField : rClass.getDeclaredFields()) {
            if (CollectionUtil.isNotEmpty(hit.getHighlight())){
                if (CollectionUtil.isNotEmpty(hit.getHighlight().getJSONArray(declaredField.getName()))){
                    this.setFieldValue(r, new ArrayList<>(), declaredField, hit.getHighlight());
                }else {
                    this.setFieldValue(r, new ArrayList<>(), declaredField, fields);
                }
            }else {
                this.setFieldValue(r, new ArrayList<>(), declaredField, fields);
            }
        }
        return r;
    }

    /**
     * 返回匹配的结果, 默认排序按照 _score 分数从高到底排序
     * @param searchModel 查询类
     * @param rClass 返回的类型
     * @param <R> 返回的类型
     * @return 返回list
     */
    @Override
    public <R> Page<R> search(SearchModel searchModel, Class<R> rClass) throws Exception{
        SearchResult result = searchES(searchModel);
        String jsonString = result.getJsonString();
        JSONObject jsonObject = JSON.parseObject(jsonString);
        JSONObject hits = jsonObject.getJSONObject("hits");
        JSONArray hitsHits = hits.getJSONArray("hits");
        List<R> rList = new ArrayList<>();
        for (Object hitsHit : hitsHits) {
            JSONObject hitJson = (JSONObject) hitsHit;
            Hit hit = hitJson.toJavaObject(Hit.class);
            JSONObject fields = hit.getFields();
            R r = null;
            try {
                r = rClass.getConstructor().newInstance();
            } catch (Exception e){
                throw new SearchException("实例化异常", e);
            }
            for (Field declaredField : rClass.getDeclaredFields()) {
                if (CollectionUtil.isNotEmpty(hit.getHighlight())){
                    if (CollectionUtil.isNotEmpty(hit.getHighlight().getJSONArray(declaredField.getName()))){
                        this.setFieldValue(r, new ArrayList<>(), declaredField, hit.getHighlight());
                    }else {
                        this.setFieldValue(r, new ArrayList<>(), declaredField, fields);
                    }
                }else {
                    this.setFieldValue(r, new ArrayList<>(), declaredField, fields);
                }
            }
            rList.add(r);
        }
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

    /**
     * 查询es
     * @param searchModel 查询类
     * @return SearchResult
     */
    @Override
    public SearchResult searchES(SearchModel searchModel){
        try {
            return esClient.searchFields(searchModel);
        }catch (IOException e){
            throw new SearchException("查询异常", e);
        }
    }

    private void setFieldValue(Object r, List<String> parentNames, Field field, JSONObject fieldsValue)throws Exception {
        Class<?> type = field.getType();
        if (isSimpleType(type)){
            parentNames.add(field.getName());
            String key = String.join(".", parentNames);
            ObjectReflectUtil.setFieldValue(r, field.getName(), fieldsValue.getJSONArray(key).get(0));
        }else {
            parentNames.add(field.getName());
            Object o = type.getConstructor().newInstance();
            Field[] declaredFields = type.getDeclaredFields();
            for (Field declaredField : declaredFields) {
                if (isSimpleType(declaredField.getType())){
                    List<String> strings = new ArrayList<>(parentNames);
                    strings.add(declaredField.getName());
                    String key = String.join(".", strings);
                    ObjectReflectUtil.setFieldValue(o, declaredField.getName(), fieldsValue.getJSONArray(key).get(0));
                }else {
                    List<String> parentNamesChilds = new ArrayList<>(parentNames);
                    this.setFieldValue(o, parentNamesChilds, declaredField, fieldsValue);
                }
            }
            ObjectReflectUtil.setFieldValue(r, field.getName(), o);
        }
    }

    /**
     * 判断是否是一些简单的类型
     * @param type 类型
     * @return boolean
     */
    private boolean isSimpleType(Class<?> type){
        return String.class.equals(type) || ClassUtil.isBaseType(type);
    }

}
