package com.zxf.common.utils.sql.m;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zhuxiaofeng
 * @date 2024/8/29
 */
@Data
@AllArgsConstructor
public class ColumnCache implements Serializable {

    private static final long serialVersionUID = -4586291538088403456L;

    /**
     * 使用 column
     */
    private String column;
    /**
     * 查询 column
     */
    private String columnSelect;
}
