package com.zxf.common.utils.sql;


import com.google.common.base.CaseFormat;
import com.zxf.common.utils.sql.u.LambdaUtil;
import org.apache.commons.lang3.StringUtils;

import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * SQL 生成器规则，只做sql语言的拼接，目前拼装方式属于hibernate的sql语法，不支持mybatis的sql语法
 *
 * TODO 自动获取表名，无需传入
 *
 * @author zhuxiaofeng
 * @date 2024/8/28
 */
public class SQLGrammar<T> {

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

    private Class<T> entityClass;

    private List<String> selects;

    private List<Criteria<T>> oredCriteria;

    private String orderByClause;

    private Map<String, Object> allParams;

    private String tableName;

    public SQLGrammar(){
        this.selects = new ArrayList<>();
        this.oredCriteria = new ArrayList<>();
        this.allParams = new HashMap<>();
        orderByClause = "";
    }

    /**
     * 查询的字段或使用函数操作列
     * @param selects
     * @return
     */
    public SQLGrammar<T> select(String... selects){
        this.selects = Arrays.asList(selects);
        return this;
    }

    /**
     * 查询的字段或使用函数操作列
     * @return
     */
    @SafeVarargs
    public final SQLGrammar<T> select(SFunction<T, ?>... columns) {
        List<String> columnStringList =
                Arrays.stream(columns).map(LambdaUtil::columnToString).collect(Collectors.toList());
        this.selects.addAll(columnStringList);
        return this;
    }

    /**
     * 查询的表，表明默认为类名转下划线
     * @param entityClass
     * @return
     */
    public SQLGrammar<T> from(Class<T> entityClass){
        this.entityClass = entityClass;
        return this;
    }

    /**
     * 获取查询添加编辑器
     * @return
     */
    public Criteria<T> where(){
        Criteria<T> criteria = createCriteria();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    public SQLGrammar<T> orderByAsc(String... fileName){
        this.orderByClause = String.join(",", fileName) + ASC;
        return this;
    }

    public SQLGrammar<T> orderByAsc(SFunction<T, ?>... fileName){
        List<String> fileNameList =
                Arrays.stream(fileName).map(LambdaUtil::columnToString).collect(Collectors.toList());
        this.orderByClause = String.join(",", fileNameList) + ASC;
        return this;
    }

    public SQLGrammar<T> orderByDesc(String... fileName){
        this.orderByClause = String.join(",", fileName) + DESC;
        return this;
    }

    public SQLGrammar<T> orderByDesc(SFunction<T, ?>... fileName){
        List<String> fileNameList =
                Arrays.stream(fileName).map(LambdaUtil::columnToString).collect(Collectors.toList());
        this.orderByClause = String.join(",", fileNameList) + DESC;
        return this;
    }

    public Map<String, Object> getAllParams() {
        return allParams;
    }

    /**
     * 创造一个查询条件编辑器
     * @return
     */
    public Criteria<T> createCriteria() {
        Criteria<T> criteria = new Criteria<T>();
        return criteria;
    }

    public String generatorSql(){
        String simpleName = tableName;
        if (entityClass != null) {
            simpleName = entityClass.getSimpleName();
        }
        //类名从驼峰变成下划线的方式
        tableName = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, simpleName);
        String sql = "";
        if (!selects.isEmpty()) {
            sql = SELECT + String.join(",", selects) + FROM + tableName;
        }else {
            sql = SELECT + " * " + FROM + tableName;
        }
        List<String> conditionList = new ArrayList<>();
        for (Criteria<T> criteria : oredCriteria) {
            if (criteria.isValid()){
//                String condition = criteria.getCriteria().stream().map(Criterion::getCondition).collect(Collectors.joining(" and "));
                StringBuilder condition = new StringBuilder();
                for (Criterion<T> criterion : criteria.getCriteria()) {
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

}
