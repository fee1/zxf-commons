package com.zxf.elastic;

import com.zxf.elastic.model.Page;
import com.zxf.elastic.model.SearchModel;
import io.searchbox.core.SearchResult;

/**
 * @author zhuxiaofeng
 * @date 2022/4/17
 */
public interface SearchService {

    /**
     * 查询es
     * @param searchModel 查询类
     * @return SearchResult
     */
    SearchResult searchES(SearchModel searchModel);

    /**
     * 查询 _score 最高的一个结果
     * @param search 查询类
     * @param rClass 返回类型
     * @param <R> 返回类型
     * @return R
     */
    <R> R searchOne(SearchModel search, Class<R> rClass)throws Exception;

    /**
     * 返回匹配的结果, 默认排序按照 _score 分数从高到底排序
     * @param searchModel 查询类
     * @param rClass 返回的类型
     * @param <R> 返回的类型
     * @return 返回list
     */
    <R> Page<R> search(SearchModel searchModel, Class<R> rClass) throws Exception;

}
