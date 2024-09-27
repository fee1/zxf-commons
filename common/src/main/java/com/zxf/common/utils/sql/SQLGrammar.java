package com.zxf.common.utils.sql;

import com.google.common.base.CaseFormat;
import com.zxf.common.utils.sql.e.JoinType;
import com.zxf.common.utils.sql.join.JoinInfo;
import com.zxf.common.utils.sql.u.EntityUtil;
import com.zxf.common.utils.sql.u.LambdaUtil;
import org.apache.commons.lang3.AnnotationUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

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
 * TODO 时间问题 and 复杂度问题 待定 left join,right join 函数嵌套函数的操作，多了到底是封装的用着方便，还是手写的sql方便
 *
 * @author zhuxiaofeng
 * @date 2024/8/28
 */
public class SQLGrammar {

    private static final String SELECT = "SELECT ";

    private static final String FROM = " FROM ";

    private static final String WHERE = " WHERE";

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

    private Class<?> entityClass;

    private List<String> selects;

    private List<Criteria> oredCriteria;

    private List<String> orderByClauseList;

    private Map<String, Object> allParams;

    private String tableName;

    private String groupByClause;

    private Integer limit;

    private boolean multiTableQuery = false;

    private List<JoinInfo> joinInfos;

    public SQLGrammar(){
        this.selects = new ArrayList<>();
        this.oredCriteria = new ArrayList<>();
        this.allParams = new HashMap<>();
        orderByClauseList = new ArrayList<>();
        joinInfos = new ArrayList<>();
    }

    public SQLGrammar(Class<?> entityClass){
        this();
        this.entityClass = entityClass;
    }

    /**
     * 查询的字段或使用函数操作列
     * @param columns
     * @return
     */
    public SQLGrammar select(String... columns){
        this.selects.addAll(Arrays.asList(columns));
        return this;
    }

    public <T> SQLGrammar select(SFunction<T, ?> column, String alias){
        this.selects.add(String.format("%s AS %s", LambdaUtil.columnToString(true,column), alias));
        return this;
    }

    /**
     * 查询的字段或使用函数操作列
     * @return
     */
    @SafeVarargs
    public final <T> SQLGrammar select(SFunction<T, ?>... columns) {
        List<String> columnStringList =
                Arrays.stream(columns).map(item -> LambdaUtil.columnToString(true, item)).collect(Collectors.toList());
        this.selects.addAll(columnStringList);
        return this;
    }

    public SQLGrammar count(String column){
        this.selects.add(String.format("COUNT(%s)", column));
        return this;
    }

    public <T> SQLGrammar count(SFunction<T, ?> column){
        this.selects.add(String.format("COUNT(%s)", LambdaUtil.columnToString(true, column)));
        return this;
    }

    public SQLGrammar avg(String column) {
        this.selects.add(String.format("AVG(%s)", column));
        return this;
    }

    public <T> SQLGrammar avg(SFunction<T, ?> column) {
        this.selects.add(String.format("AVG(%s)", LambdaUtil.columnToString(true,column)));
        return this;
    }

    /**
     * 查询的表，表明默认为类名转下划线
     * @param entityClass
     * @return
     */
    public SQLGrammar from(Class entityClass){
        this.entityClass = entityClass;
        return this;
    }

    /**
     * 查询的表，表明默认为类名转下划线
     * @return
     */
    public SQLGrammar from(String tableName){
        this.tableName = tableName;
        return this;
    }

    /**
     *
     * 使用样例
     * JoinInfo.builder().tableEntity(TableB.class).joinType(JoinType.LEFT_JOIN).joinCondition(new Criteria<>().equalTo(TableA::getId, TableB::getId));
     *
     * 生成效果
     * select table_a.*,table_b.* from table_a  left join table_b  on table_a.id = table_b.id
     *
     * @return
     */
    public SQLGrammar join(JoinType joinType, Class<?> tableEntity, Criteria criteria){
        this.multiTableQuery = true;
        JoinInfo joinInfo = new JoinInfo(tableEntity, joinType, criteria);
        this.joinInfos.add(joinInfo);
        return this;
    }

    /**
     * 获取查询添加编辑器
     * @return
     */
    public Criteria where(){
        Criteria criteria = createCriteria();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    public SQLGrammar orderByAsc(String... fileName){
        for (String name : fileName) {
            this.orderByClauseList.add(name + ASC);
        }
        return this;
    }

    @SafeVarargs
    public final <T> SQLGrammar orderByAsc(SFunction<T, ?>... fileName){
        List<String> fileNameList =
                Arrays.stream(fileName).map(item -> LambdaUtil.columnToString(true, item)).collect(Collectors.toList());
        for (String name : fileNameList) {
            this.orderByClauseList.add(name + ASC);
        }
        return this;
    }

    public SQLGrammar orderByDesc(String... fileName){
        for (String name : fileName) {
            this.orderByClauseList.add(name + DESC);
        }
        return this;
    }

    @SafeVarargs
    public final <T> SQLGrammar orderByDesc(SFunction<T, ?>... fileName){
        List<String> fileNameList =
                Arrays.stream(fileName).map(item -> LambdaUtil.columnToString(true, item)).collect(Collectors.toList());
        for (String name : fileNameList) {
            this.orderByClauseList.add(name + DESC);
        }
        return this;
    }

    public SQLGrammar orderBy(String expression){
        this.orderByClauseList.add(expression);
        return this;
    }

    public void groupBy(String... groupBy) {
        this.groupByClause = String.join(",", groupBy);
    }

    @SafeVarargs
    public final <T> void groupBy(SFunction<T, ?>... groupBy) {
        List<String> fileNameList =
                Arrays.stream(groupBy).map(item -> LambdaUtil.columnToString(true, item)).collect(Collectors.toList());
        this.groupByClause = String.join(",", fileNameList);
    }

    public void limit(Integer limit){
        this.limit = limit;
    }

    public Map<String, Object> getAllParams() {
        return allParams;
    }

    /**
     * 创造一个查询条件编辑器
     * @return
     */
    public Criteria createCriteria() {
        Criteria criteria = new Criteria(this.multiTableQuery);
        return criteria;
    }

    public String generatorSql(){
        String simpleName = "";
        if (StringUtils.isNotEmpty(tableName)){
            simpleName = tableName;
        }else{
            simpleName = EntityUtil.getTableName(entityClass);
        }
        //类名从驼峰变成下划线的方式
        tableName = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, simpleName);
        StringBuilder sql = new StringBuilder();
        if (!selects.isEmpty()) {
            sql = new StringBuilder(SELECT + String.join(",", selects) + FROM + tableName);
        }else {
            sql = new StringBuilder(SELECT + " * " + FROM + tableName);
        }
        if (!joinInfos.isEmpty()){
            for (JoinInfo joinInfo : joinInfos) {
                String joinType = joinInfo.getJoinType().getName();
                String joinTableName = EntityUtil.getTableName(joinInfo.getTableEntity());
                Criteria joinCondition = joinInfo.getJoinCondition();
                StringBuilder condition = new StringBuilder();
                for (Criterion criterion : joinCondition.getCriteria()) {
                    String symbol = criterion.getConnectSymbol() == null ? ConnectSymbols.SPACE : criterion.getConnectSymbol().getSymbol();
                    condition.append(" ").append(symbol).append(" ").append(criterion.getCondition());
                }

                sql.append(" ").append(joinType).append(" ").append(joinTableName).append(" ON ").append(condition.toString());
            }
        }
        List<String> conditionList = new ArrayList<>();
        for (Criteria criteria : oredCriteria) {
            if (criteria.isValid()){
//                String condition = criteria.getCriteria().stream().map(Criterion::getCondition).collect(Collectors.joining(" and "));
                StringBuilder condition = new StringBuilder();
                for (Criterion criterion : criteria.getCriteria()) {
                    String symbol = criterion.getConnectSymbol() == null ? "" : criterion.getConnectSymbol().getSymbol();
                    condition.append(" ").append(symbol).append(" ").append(criterion.getCondition());
                }
                conditionList.add(" (" + condition + ")");
                this.allParams.putAll(criteria.getParams());
            }
        }
        if (!conditionList.isEmpty()) {
            sql.append(WHERE).append(String.join(OR, conditionList));
        }
        if (StringUtils.isNotEmpty(groupByClause)){
            sql.append(GROUP_BY).append(groupByClause);
        }
        if (!CollectionUtils.isEmpty(orderByClauseList)){
            sql.append(ORDER_BY).append(String.join(",", orderByClauseList));
        }
        if (limit != null){
            sql.append(LIMIT).append(limit);
        }
        return sql.toString();
    }

}

