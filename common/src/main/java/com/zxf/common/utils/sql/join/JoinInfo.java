package com.zxf.common.utils.sql.join;

import com.zxf.common.utils.sql.Criteria;
import com.zxf.common.utils.sql.e.JoinType;
import lombok.Getter;

/**
 * @author zhuxiaofeng
 * @date 2024/9/5
 */
@Getter
public class JoinInfo {

    private final Class<?> tableEntity;

    private final JoinType joinType;

    private final Criteria joinCondition;

    private String alias;

    public JoinInfo(Class<?> tableEntity, JoinType joinType, Criteria joinCondition) {
        this.tableEntity = tableEntity;
        this.joinType = joinType;
        this.joinCondition = joinCondition;
    }

    public JoinInfo(Class<?> tableEntity, String alias, JoinType joinType, Criteria joinCondition) {
        this.tableEntity = tableEntity;
        this.joinType = joinType;
        this.joinCondition = joinCondition;
        this.alias = alias;
    }
}