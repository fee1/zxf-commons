package com.zxf.common.utils.sql;

import lombok.Data;

/**
 * @author zhuxiaofeng
 * @date 2024/8/28
 */
@Data
public class JoinTable {

    private String tableName;
    private String alias;
    private String joinType;
    private String joinCondition;
    private String joinOn;
    private String joinTable;
    private String joinAlias;
    private String joinJoinType;
    private String joinJoinCondition;
    private String joinJoinOn;
    private String joinJoinTable;
    private String joinJoinAlias;
    private String joinJoinJoinType;
    private String joinJoinJoinCondition;
    private String joinJoinJoinOn;
    private String joinJoinJoinTable;

}
