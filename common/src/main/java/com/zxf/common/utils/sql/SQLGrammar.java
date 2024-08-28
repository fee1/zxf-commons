package com.zxf.common.utils.sql;


import com.google.common.base.CaseFormat;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhuxiaofeng
 * @date 2024/8/28
 */
public class SQLGrammar {

    private static final String SELECT_SQL = "SELECT ";

    private static final String FROM_SQL = " FROM ";

    private static final String WHERE_SQL = " WHERE ";

    private static final String AND_SQL = " AND ";

    private static final String OR_SQL = " OR ";

    private static final String GROUP_BY_SQL = " GROUP BY ";

    private static final String ORDER_BY_SQL = " ORDER BY ";

    private static final String HAVING_SQL = " HAVING ";

    private static final String LIMIT_SQL = " LIMIT ";

    private static final String OFFSET_SQL = " OFFSET ";

    private static final String AS_SQL = " AS ";

    private static final String DISTINCT_SQL = " DISTINCT ";

    private static final String COUNT_SQL = " COUNT ";

    private static final String COUNT_ALL_SQL = " COUNT(*) ";

    private static final String ASC_SQL = " ASC ";

    private static final String DESC_SQL = " DESC ";

    private static final String IN_SQL = " IN ";

    private Class<?> entityClass;

    private List<String> selects;

    private List<Criteria> oredCriteria;

    private String orderByClause;

    private Map<String, Object> allParams;

    public SQLGrammar(){
        this.selects = new ArrayList<>();
        this.oredCriteria = new ArrayList<>();
        this.allParams = new HashMap<>();
        orderByClause = "";
    }

    public SQLGrammar select(String... selects){
        this.selects = Arrays.asList(selects);
        return this;
    }

    public SQLGrammar from(Class<?> entityClass){
        this.entityClass = entityClass;
        return this;
    }

    public Criteria where(){
        Criteria criteria = createCriteria();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    protected String getOrderByClause() {
        return orderByClause;
    }

    public String generatorSql(){
        String simpleName = entityClass.getSimpleName();
        //类名从驼峰变成下划线的方式
        String tableName = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, simpleName);
        String sql = "";
        if (!selects.isEmpty()) {
            sql = "select " + String.join(",", selects) + " from " + tableName;
        }else {
            sql = "select * from " + tableName;
        }
        List<String> conditionList = new ArrayList<>();
        for (Criteria criteria : oredCriteria) {
            if (criteria.isValid()){
//                String condition = criteria.getCriteria().stream().map(Criterion::getCondition).collect(Collectors.joining(" and "));
                StringBuilder condition = new StringBuilder();
                for (Criterion criterion : criteria.getCriteria()) {
                    String symbol = criterion.getConnectSymbol() == null ? ConnectSymbols.SPACE : criterion.getConnectSymbol().getSymbol();
                    condition.append(" ").append(symbol).append(" ").append(criterion.getCondition());
                }
                conditionList.add(" ( " + condition + " ) ");
                this.allParams.putAll(criteria.getParams());
            }
        }
        if (!conditionList.isEmpty()) {
            sql = sql + " where " + String.join(" or ", conditionList);
        }
        if (StringUtils.isNotEmpty(orderByClause)){
            sql = sql + "order by " + orderByClause;
        }
        return sql;
    }

    public Map<String, Object> getAllParams() {
        return allParams;
    }
}
