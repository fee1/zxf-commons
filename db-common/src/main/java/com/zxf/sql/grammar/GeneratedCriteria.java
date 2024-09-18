package com.zxf.sql.grammar;


import com.zxf.common.utils.sql.u.LambdaUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhuxiaofeng
 * @date 2024/8/28
 */
abstract class GeneratedCriteria {

    private final static String IN = "in";

    private final static String NOT_IN = "not in";

    protected List<Criterion> criteria;

    protected Map<String, Object> params;

    private boolean multiTableQuery = false;

    protected GeneratedCriteria(boolean multiTableQuery) {
        super();
        criteria = new ArrayList<Criterion>();
        params = new HashMap<>();
        this.multiTableQuery = multiTableQuery;
    }

    public boolean isValid() {
        return criteria.size() > 0;
    }

    protected List<Criterion> getCriteria() {
        return criteria;
    }

    protected Map<String, Object> getParams() {
        return params;
    }

    protected void addCriterion(String condition) {
        if (condition == null) {
            throw new RuntimeException("Value for condition cannot be null");
        }
        Criterion criterion = new Criterion(condition);
        Criteria criteria = (Criteria) this;
        criterion.setConnectSymbol(criteria.getConnectSymbol());
        this.criteria.add(criterion);
    }

    private String simpleValueCondition(String fieldName, String operator) {
        return String.format("%s %s :%s", fieldName, operator, fieldName);
    }

    private String multiValueCondition(String fieldName, String operator) {
        return String.format("%s %s(:%s)", fieldName, operator, fieldName);
    }

    private String joinOnCondition(String tFieldName1, String operator, String tFieldName2) {
        return String.format("%s %s %s", tFieldName1, operator, tFieldName2);
    }

//    protected void addJoinOnCriterionParam(boolean condition,String tFieldName1, Object tFieldName2, String operator) {
//        if (condition) {
//            addCriterion(this.joinOnCondition(tFieldName1, operator, tFieldName2));
//        }
//    }

    protected void addCriterionParam(boolean condition,String fieldName, Object value, String operator) {
        if (condition) {
            if (value instanceof List){
                addCriterion(this.multiValueCondition(fieldName, operator));
            }else {
                addCriterion(this.simpleValueCondition(fieldName, operator));
            }
            addParams(fieldName, value);
        }
    }

    protected void addCriterionParam(boolean condition,String fieldName, Object value1, Object value2,String operator) {
        if (condition) {
            addCriterion(this.betweenValueCondition(fieldName, operator));
            addParams(fieldName + "1", value1);
            addParams(fieldName + "2", value2);
        }
    }

    private String betweenValueCondition(String fieldName, String operator) {
        return String.format("%s %s %s1  %s2", fieldName, operator, fieldName, fieldName);
    }

    private void addParams(String fieldName, Object value) {
        this.params.put(fieldName, value);
    }

    public ConnectSymbols isNull(String fieldName) {
        addCriterion(fieldName + " is null");
        return new ConnectSymbols((Criteria) this);
    }

    public <T> ConnectSymbols isNull(SFunction<T, ?> fieldName) {
        addCriterion(LambdaUtil.columnToString(this.multiTableQuery,fieldName) + " is null");
        return new ConnectSymbols((Criteria) this);
    }

    public ConnectSymbols isNotNull(String fieldName) {
        addCriterion(fieldName + " is not null");
        return new ConnectSymbols((Criteria) this);
    }

    public <T> ConnectSymbols isNotNull(SFunction<T, ?> fieldName) {
        addCriterion(LambdaUtil.columnToString(this.multiTableQuery,fieldName) + " is not null");
        return new ConnectSymbols((Criteria) this);
    }

    public ConnectSymbols equalTo(boolean condition, String fieldName, Object value) {
        addCriterionParam(condition,fieldName, value, "=");
        return new ConnectSymbols((Criteria) this);
    }

    public <T> ConnectSymbols equalTo(boolean condition, SFunction<T, ?> fieldName, Object value) {
        addCriterionParam(condition, LambdaUtil.columnToString(this.multiTableQuery,fieldName), value, "=");
        return new ConnectSymbols((Criteria) this);
    }

    public ConnectSymbols equalTo(String fieldName, Object value) {
        this.equalTo(true, fieldName, value);
        return new ConnectSymbols((Criteria) this);
    }

    public <T> ConnectSymbols equalTo(SFunction<T, ?> fieldName, Object value) {
        this.equalTo(true, fieldName, value);
        return new ConnectSymbols((Criteria) this);
    }

    // JOIN on 使用
    public ConnectSymbols equalTo(String tFieldName1, String tFieldName2){
        this.addCriterion(joinOnCondition(tFieldName1, "=", tFieldName2));
        return new ConnectSymbols((Criteria) this);
    }

    // JOIN on 使用
    public <T1, T2> ConnectSymbols equalTo(SFunction<T1, ?> tFieldName1, SFunction<T2, ?> tFieldName2){
        this.addCriterion(joinOnCondition(LambdaUtil.columnToString(true, tFieldName1), "=", LambdaUtil.columnToString(true,tFieldName2)));
        return new ConnectSymbols((Criteria) this);
    }

    public ConnectSymbols notEqualTo(boolean condition, String fieldName, Object value) {
        addCriterionParam(condition,fieldName, value, "<>");
        return new ConnectSymbols((Criteria) this);
    }

    public <T> ConnectSymbols notEqualTo(boolean condition, SFunction<T, ?> fieldName, Object value) {
        addCriterionParam(condition,LambdaUtil.columnToString(this.multiTableQuery,fieldName), value, "<>");
        return new ConnectSymbols((Criteria) this);
    }

    public ConnectSymbols notEqualTo(String fieldName, Object value) {
        this.notEqualTo(true, fieldName, value);
        return new ConnectSymbols((Criteria) this);
    }

    public <T> ConnectSymbols notEqualTo(SFunction<T, ?> fieldName, Object value) {
        this.notEqualTo(true, fieldName, value);
        return new ConnectSymbols((Criteria) this);
    }

    // JOIN on 使用
    public ConnectSymbols notEqualTo(String tFieldName1, String tFieldName2) {
        this.notEqualTo(true, tFieldName1, tFieldName2);
        return new ConnectSymbols((Criteria) this);
    }

    // JOIN on 使用
    public <T> ConnectSymbols notEqualTo(SFunction<T, ?> tFieldName1, SFunction<T, ?> tFieldName2) {
        this.notEqualTo(LambdaUtil.columnToString(this.multiTableQuery,tFieldName1), LambdaUtil.columnToString(this.multiTableQuery,tFieldName2));
        return new ConnectSymbols((Criteria) this);
    }

    public ConnectSymbols greaterThan(boolean condition, String fieldName, Object value) {
        addCriterionParam(condition,fieldName, value, ">");
        return new ConnectSymbols((Criteria) this);
    }

    public <T> ConnectSymbols greaterThan(boolean condition, SFunction<T, ?> fieldName, Object value) {
        addCriterionParam(condition,LambdaUtil.columnToString(this.multiTableQuery,fieldName), value, ">");
        return new ConnectSymbols((Criteria) this);
    }

    public ConnectSymbols greaterThan(String fieldName, Object value) {
        this.greaterThan(true, fieldName, value);
        return new ConnectSymbols((Criteria) this);
    }

    public <T> ConnectSymbols greaterThan(SFunction<T, ?> fieldName, Object value) {
        this.greaterThan(true, fieldName, value);
        return new ConnectSymbols((Criteria) this);
    }

    public ConnectSymbols greaterThanOrEqualTo(boolean condition, String fieldName, Object value) {
        addCriterionParam(condition,fieldName, value, ">=");
        return new ConnectSymbols((Criteria) this);
    }

    public <T> ConnectSymbols greaterThanOrEqualTo(boolean condition, SFunction<T, ?> fieldName, Object value) {
        addCriterionParam(condition, LambdaUtil.columnToString(this.multiTableQuery,fieldName), value, ">=");
        return new ConnectSymbols((Criteria) this);
    }

    public ConnectSymbols greaterThanOrEqualTo(String fieldName, Object value) {
        this.greaterThanOrEqualTo(true, fieldName, value);
        return new ConnectSymbols((Criteria) this);
    }

    public <T> ConnectSymbols greaterThanOrEqualTo(SFunction<T, ?> fieldName, Object value) {
        this.greaterThanOrEqualTo(true, fieldName, value);
        return new ConnectSymbols((Criteria) this);
    }

    public ConnectSymbols lessThan(boolean condition, String fieldName, Object value) {
        addCriterionParam(condition,fieldName, value, "<");
        return new ConnectSymbols((Criteria) this);
    }

    public <T> ConnectSymbols lessThan(boolean condition, SFunction<T, ?> fieldName, Object value) {
        addCriterionParam(condition,LambdaUtil.columnToString(this.multiTableQuery,fieldName), value, "<");
        return new ConnectSymbols((Criteria) this);
    }

    public ConnectSymbols lessThan(String fieldName, Object value) {
        this.lessThan(true, fieldName, value);
        return new ConnectSymbols((Criteria) this);
    }

    public <T> ConnectSymbols lessThan(SFunction<T, ?> fieldName, Object value) {
        this.lessThan(true, fieldName, value);
        return new ConnectSymbols((Criteria) this);
    }

    public ConnectSymbols lessThanOrEqualTo(boolean condition, String fieldName, Object value) {
        addCriterionParam(condition,fieldName, value, "<=");
        return new ConnectSymbols((Criteria) this);
    }

    public <T> ConnectSymbols lessThanOrEqualTo(boolean condition, SFunction<T, ?> fieldName, Object value) {
        addCriterionParam(condition,LambdaUtil.columnToString(this.multiTableQuery,fieldName), value, "<=");
        return new ConnectSymbols((Criteria) this);
    }

    public ConnectSymbols lessThanOrEqualTo(String fieldName, Object value) {
        this.lessThanOrEqualTo(true, fieldName, value);
        return new ConnectSymbols((Criteria) this);
    }

    public <T> ConnectSymbols lessThanOrEqualTo(SFunction<T, ?> fieldName, Object value) {
        this.lessThanOrEqualTo(true, fieldName, value);
        return new ConnectSymbols((Criteria) this);
    }

    public ConnectSymbols like(boolean condition, String fieldName, Object value) {
        addCriterionParam(condition,fieldName, value, "like");
        return new ConnectSymbols((Criteria) this);
    }

    public <T> ConnectSymbols like(boolean condition, SFunction<T, ?> fieldName, Object value) {
        addCriterionParam(condition,LambdaUtil.columnToString(this.multiTableQuery,fieldName), value, "like");
        return new ConnectSymbols((Criteria) this);
    }

    public ConnectSymbols like(String fieldName, Object value) {
        this.like(true, fieldName, value);
        return new ConnectSymbols((Criteria) this);
    }

    public <T> ConnectSymbols like(SFunction<T, ?> fieldName, Object value) {
        this.like(true, fieldName, value);
        return new ConnectSymbols((Criteria) this);
    }

    public ConnectSymbols notLike(boolean condition, String fieldName, Object value) {
        addCriterionParam(condition,fieldName, value, "not like");
        return new ConnectSymbols((Criteria) this);
    }

    public <T> ConnectSymbols notLike(boolean condition, SFunction<T, ?> fieldName, Object value) {
        addCriterionParam(condition,LambdaUtil.columnToString(this.multiTableQuery,fieldName), value, "not like");
        return new ConnectSymbols((Criteria) this);
    }

    public ConnectSymbols notLike(String fieldName, Object value) {
        this.notLike(true, fieldName, value);
        return new ConnectSymbols((Criteria) this);
    }

    public <T> ConnectSymbols notLike(SFunction<T, ?> fieldName, Object value) {
        this.notLike(true, fieldName, value);
        return new ConnectSymbols((Criteria) this);
    }

    public ConnectSymbols in(boolean condition, String fieldName, List<?> values) {
        addCriterionParam(condition,fieldName, values, IN);
        return new ConnectSymbols((Criteria) this);
    }

    public <T> ConnectSymbols in(boolean condition, SFunction<T, ?> fieldName, List<?> values) {
        addCriterionParam(condition,LambdaUtil.columnToString(this.multiTableQuery,fieldName), values, IN);
        return new ConnectSymbols((Criteria) this);
    }

    public ConnectSymbols in(String fieldName, List<?> values) {
        this.in(true, fieldName, values);
        return new ConnectSymbols((Criteria) this);
    }

    public <T> ConnectSymbols in(SFunction<T, ?> fieldName, List<?> values) {
        this.in(true, fieldName, values);
        return new ConnectSymbols((Criteria) this);
    }

    public ConnectSymbols notIn(boolean condition, String fieldName, List<?> values) {
        addCriterionParam(condition,fieldName, values, NOT_IN);
        return new ConnectSymbols((Criteria) this);
    }

    public <T> ConnectSymbols notIn(boolean condition, SFunction<T, ?> fieldName, List<?> values) {
        addCriterionParam(condition,LambdaUtil.columnToString(this.multiTableQuery,fieldName), values, NOT_IN);
        return new ConnectSymbols((Criteria) this);
    }

    public ConnectSymbols notIn(String fieldName, List<?> values) {
        this.notIn(true, fieldName, values);
        return new ConnectSymbols((Criteria) this);
    }

    public <T> ConnectSymbols notIn(SFunction<T, ?> fieldName, List<?> values) {
        this.notIn(true, fieldName, values);
        return new ConnectSymbols((Criteria) this);
    }

    public ConnectSymbols between(boolean condition, String fieldName, Object value1, Object value2) {
        addCriterionParam(condition,fieldName, value1, value2,"between");
        return new ConnectSymbols((Criteria) this);
    }

    public <T> ConnectSymbols between(boolean condition, SFunction<T, ?> fieldName, Object value1, Object value2) {
        addCriterionParam(condition,LambdaUtil.columnToString(this.multiTableQuery,fieldName), value1, value2,"between");
        return new ConnectSymbols((Criteria) this);
    }

    public ConnectSymbols between(String fieldName, Object value1, Object value2) {
        this.between(true, fieldName, value1, value2);
        return new ConnectSymbols((Criteria) this);
    }

    public <T> ConnectSymbols between(SFunction<T, ?> fieldName, Object value1, Object value2) {
        this.between(true, fieldName, value1, value2);
        return new ConnectSymbols((Criteria) this);
    }

    public ConnectSymbols notBetween(boolean condition, String fieldName, Object value1, Object value2) {
        addCriterionParam(condition,fieldName, value1, value2,"not between");
        return new ConnectSymbols((Criteria) this);
    }

    public <T> ConnectSymbols notBetween(boolean condition, SFunction<T, ?> fieldName, Object value1, Object value2) {
        addCriterionParam(condition,LambdaUtil.columnToString(this.multiTableQuery,fieldName), value1, value2,"not between");
        return new ConnectSymbols((Criteria) this);
    }

    public ConnectSymbols notBetween(String fieldName, Object value1, Object value2) {
        this.notBetween(true, fieldName, value1, value2);
        return new ConnectSymbols((Criteria) this);
    }

    public <T> ConnectSymbols notBetween(SFunction<T, ?> fieldName, Object value1, Object value2) {
        this.notBetween(true, fieldName, value1, value2);
        return new ConnectSymbols((Criteria) this);
    }

}