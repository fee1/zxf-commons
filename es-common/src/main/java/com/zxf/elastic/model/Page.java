package com.zxf.elastic.model;

import lombok.Data;

import java.util.List;

/**
 * @author zhuxiaofeng
 * @date 2022/4/4
 */
@Data
public class Page<T> {

    private List<T> data;

    private Integer from;

    private Integer size;

    private Integer total;

    private Integer totalPage;

    public void setTotalPage() {
        this.totalPage = total.intValue() / size.intValue() + (total.intValue() % size.intValue() > 0 ? 1 : 0);
    }

}
