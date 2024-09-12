package com.zxf.common.utils.sql.e;

import lombok.Getter;

/**
 * @author zhuxiaofeng
 * @date 2024/9/5
 */
@Getter
public enum JoinType {
    /**
     * 左连接
     */
    LEFT_JOIN("LEFT JOIN"),
    /**
     * 右连接
     */
    RIGHT_JOIN("RIGHT JOIN"),
    /**
     * 内连接
     */
    INNER_JOIN("INNER JOIN");

    private final String name;

    JoinType(String joinType) {
        this.name = joinType;
    }
}
