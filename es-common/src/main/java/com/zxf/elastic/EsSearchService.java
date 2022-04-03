package com.zxf.elastic;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zxf.common.utils.ClassUtil;
import com.zxf.common.utils.ObjectReflectUtil;
import com.zxf.elastic.client.JestEsClient;
import com.zxf.elastic.exception.SearchException;
import com.zxf.elastic.model.Hit;
import com.zxf.elastic.model.Search;
import io.searchbox.core.SearchResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * @author zhuxiaofeng
 * @date 2022/2/18
 */
@Slf4j
public class EsSearchService {

    private JestEsClient esClient;

    public EsSearchService(JestEsClient esClient){
        this.esClient = esClient;
    }

    /**
     * 查询业务
     * @param search 查询类
     * @param rClass 返回类型
     * @param <R> 返回类型
     * @return R
     */
    public <R> R searchOne(Search search, Class<R> rClass){
        SearchResult result = null;
        try {
            result = this.esClient.searchByQs(search.getIndexName(), search.getQ(),
                    search.getFields(), search.getFrom(), search.getSize());
        }catch (IOException e){
            throw new SearchException("查询异常", e);
        }
        if (!result.isSucceeded() || result.getResponseCode() != 200){
            throw new SearchException("查询异常，response code = "+result.getResponseCode());
        }
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
        Set<Map.Entry<String, Object>> entries = fields.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            String key = entry.getKey();
            String[] fieldNames = key.split("\\.");
            try {
                Field declaredField = rClass.getDeclaredField(fieldNames[0]);
                JSONArray esField = (JSONArray) entry.getValue();
                setFieldValue(r, fieldNames, declaredField, esField.get(0));
            } catch (NoSuchFieldException e) {
                log.error("没有这个字段: {}", e.getMessage());
            } catch (Exception e) {
                log.error("字段赋值异常，{}", e.getMessage() + ":" + e.getCause());
            }
        }
        return r;
    }

    /**
     * 返回对象设置值
     * @param r 返回类型
     * @param fieldNames 字段名
     * @param field 字段
     * @param value 字段值
     * @throws Exception 异常
     */
    private void setFieldValue(Object r, String[] fieldNames, Field field, Object value)throws Exception{
        Class<?> type = field.getType();
        if (isSimpleType(type)) {
            ObjectReflectUtil.setFieldValue(r, fieldNames[0], value);
        }else {
            Object o = ObjectReflectUtil.getFieldValue(r, fieldNames[0]);
            if (ObjectUtils.isEmpty(o)){
                o = type.getConstructor().newInstance();
            }
            for (String fieldName : fieldNames) {
                if (!fieldName.equals(o.getClass().getSimpleName().toLowerCase(Locale.ENGLISH))){
                    Field declaredField = o.getClass().getDeclaredField(fieldName);
                    Class<?> childType = declaredField.getType();
                    if (isSimpleType(childType)){
                        ObjectReflectUtil.setFieldValue(o, fieldName, value);
                    }else {
                        String[] childFieldNames = Arrays.copyOfRange(fieldNames, 1, fieldNames.length);
                        setFieldValue(r, childFieldNames, declaredField, value);
                    }
                }
                ObjectReflectUtil.setFieldValue(r, fieldNames[0], o);
            }
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
