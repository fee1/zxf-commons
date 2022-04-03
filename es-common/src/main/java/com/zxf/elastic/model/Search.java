package com.zxf.elastic.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhuxiaofeng
 * @date 2022/3/3
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Search {

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

}
