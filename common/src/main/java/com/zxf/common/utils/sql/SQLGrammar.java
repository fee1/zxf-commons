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

    private static final String SELECT = "SELECT ";

    private static final String FROM = " FROM ";

    private static final String WHERE = " WHERE ";

    private static final String AND = " AND ";

    private static final String OR = " OR ";

    private static final String GROUP_BY = " GROUP BY ";

    private static final String ORDER_BY = " ORDER BY ";

    private static final String HAVING = " HAVING ";

    private static final String LIMIT = " LIMIT ";

    private static final String OFFSET = " OFFSET ";

    private static final String AS = " AS ";

    private static final String DISTINCT = " DISTINCT ";

    private static final String COUNT = " COUNT ";

    private static final String COUNT_ALL = " COUNT(*) ";

    private static final String ASC = " ASC ";

    private static final String DESC = " DESC ";

    private static final String IN = " IN ";

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
            sql = SELECT + String.join(",", selects) + FROM + tableName;
        }else {
            sql = SELECT + " * " + FROM + tableName;
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
            sql = sql + WHERE + String.join(OR, conditionList);
        }
        if (StringUtils.isNotEmpty(orderByClause)){
            sql = sql + ORDER_BY + orderByClause;
        }
        return sql;
    }

    public Map<String, Object> getAllParams() {
        return allParams;
    }
}
