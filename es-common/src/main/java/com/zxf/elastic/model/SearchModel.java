package com.zxf.elastic.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author zhuxiaofeng
 * @date 2022/3/3
 */
@Data
@NoArgsConstructor
public class SearchModel {

    /**
     * 索引名称
     */
    private String indexName;

    /**
     * 查询条件
     */
    String q;

    /**
     * 返回字段
     */
    String[] fields;

    /**
     * 页
     */
    int from;

    /**
     * 返回条数
     */
    int size;

    /**
     * 排序
     */
    List<Sort> sortList;

    /**
     * 设置高亮的字段
     */
    List<String> highlightField;


    public SearchModel(String indexName, String q, String[] fields){
        this.indexName = indexName;
        this.q = q;
        this.fields = fields;
    }

    public SearchModel(String indexName, String q, String[] fields, int from, int size){
        this.indexName = indexName;
        this.q = q;
        this.fields = fields;
        this.from = from;
        this.size = size;
    }

}
