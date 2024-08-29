package com.zxf.common.utils.sql;

import com.zxf.common.utils.sql.u.LambdaUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhuxiaofeng
 * @date 2024/8/28
 */
abstract class GeneratedCriteria<T> {
    protected List<Criterion<T>> criteria;

    protected Map<String, Object> params;

    protected GeneratedCriteria() {
        super();
        criteria = new ArrayList<Criterion<T>>();
        params = new HashMap<>();
    }

    public boolean isValid() {
        return criteria.size() > 0;
    }

    protected List<Criterion<T>> getCriteria() {
        return criteria;
    }

    protected Map<String, Object> getParams() {
        return params;
    }

    protected void addCriterion(String condition) {
        if (condition == null) {
            throw new RuntimeException("Value for condition cannot be null");
        }
        Criterion<T> criterion = new Criterion<T>(condition);
        Criteria<T> criteria = (Criteria<T>) this;
        criterion.setConnectSymbol(criteria.getConnectSymbol());
        this.criteria.add(criterion);
    }

    private String simpleValueCondition(String fieldName, String operator) {
        return String.format("%s %s :%s", fieldName, operator, fieldName);
    }

    private String multiValueCondition(String fieldName, String operator) {
        return String.format("%s %s:(%s)", fieldName, operator, fieldName);
    }

    private void addCriterionParam(boolean condition,String fieldName, Object value, String operator) {
        if (condition) {
            if (value instanceof List){
                addCriterion(this.multiValueCondition(fieldName, operator));
                addParams(fieldName, value);
            }else {
                addCriterion(this.simpleValueCondition(fieldName, operator));
                addParams(fieldName, value);
            }
        }
    }

    private void addCriterionParam(boolean condition,String fieldName, Object value1, Object value2,String operator) {
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

//    public Criteria isNull(String fieldName) {
//        addCriterion(fieldName + " is null");
//        return (Criteria) this;
//    }
//
//    public Criteria isNotNull(String fieldName) {
//        addCriterion(fieldName + " is not null");
//        return (Criteria) this;
//    }
//
//    public Criteria equalTo(boolean condition, String fieldName, Object value) {
//        addCriterionParam(condition,fieldName, value, "=");
//        return (Criteria) this;
//    }
//
//    public Criteria equalTo(String fieldName, Object value) {
//        return this.equalTo(true, fieldName, value);
//    }
//
//    public Criteria notEqualTo(boolean condition, String fieldName, Object value) {
//        addCriterionParam(condition,fieldName, value, "<>");
//        return (Criteria) this;
//    }
//
//    public Criteria notEqualTo(String fieldName, Object value) {
//        return this.notEqualTo(true, fieldName, value);
//    }
//
//    public Criteria greaterThan(boolean condition, String fieldName, Object value) {
//        addCriterionParam(condition,fieldName, value, ">");
//        return (Criteria) this;
//    }
//
//    public Criteria greaterThan(String fieldName, Object value) {
//        this.greaterThan(true, fieldName, value);
//        return (Criteria) this;
//    }
//
//    public Criteria greaterThanOrEqualTo(boolean condition, String fieldName, Object value) {
//        addCriterionParam(condition,fieldName, value, ">=");
//        return (Criteria) this;
//    }
//
//    public Criteria greaterThanOrEqualTo(String fieldName, Object value) {
//        this.greaterThanOrEqualTo(true, fieldName, value);
//        return (Criteria) this;
//    }
//
//    public Criteria lessThan(boolean condition, String fieldName, Object value) {
//        addCriterionParam(condition,fieldName, value, "<");
//        return (Criteria) this;
//    }
//
//    public Criteria lessThan(String fieldName, Object value) {
//        this.lessThan(true, fieldName, value);
//        return (Criteria) this;
//    }
//
//    public Criteria lessThanOrEqualTo(boolean condition, String fieldName, Object value) {
//        addCriterionParam(condition,fieldName, value, "<=");
//        return (Criteria) this;
//    }
//
//    public Criteria lessThanOrEqualTo(String fieldName, Object value) {
//        this.lessThanOrEqualTo(true, fieldName, value);
//        return (Criteria) this;
//    }
//
//    public Criteria like(boolean condition, String fieldName, Object value) {
//        addCriterionParam(condition,fieldName, value, "like");
//        return (Criteria) this;
//    }
//
//    public Criteria like(String fieldName, Object value) {
//        this.like(true, fieldName, value);
//        return (Criteria) this;
//    }
//
//    public Criteria notLike(boolean condition, String fieldName, Object value) {
//        addCriterionParam(condition,fieldName, value, "not like");
//        return (Criteria) this;
//    }
//
//    public Criteria notLike(String fieldName, Object value) {
//        this.notLike(true, fieldName, value);
//        return (Criteria) this;
//    }
//
//    public Criteria in(boolean condition, String fieldName, List<Object> values) {
//        addCriterionParam(condition,fieldName, values, "in");
//        return (Criteria) this;
//    }
//
//    public Criteria in(String fieldName, List<Object> values) {
//        this.in(true, fieldName, values);
//        return (Criteria) this;
//    }
//
//    public Criteria notIn(boolean condition, String fieldName, List<Object> values) {
//        addCriterionParam(condition,fieldName, values, "not in");
//        return (Criteria) this;
//    }
//
//    public Criteria notIn(String fieldName, List<Object> values) {
//        this.notIn(true, fieldName, values);
//        return (Criteria) this;
//    }
//
//    public Criteria between(boolean condition, String fieldName, Object value1, Object value2) {
//        addCriterionParam(condition,fieldName, value1, value2,"between");
//        return (Criteria) this;
//    }
//
//    public Criteria between(String fieldName, Object value1, Object value2) {
//        this.between(true, fieldName, value1, value2);
//        return (Criteria) this;
//    }
//
//    public Criteria notBetween(boolean condition, String fieldName, Object value1, Object value2) {
//        addCriterionParam(condition,fieldName, value1, value2,"not between");
//        return (Criteria) this;
//    }
//
//    public Criteria notBetween(String fieldName, Object value1, Object value2) {
//        this.notBetween(true, fieldName, value1, value2);
//        return (Criteria) this;
//    }

    public ConnectSymbols<T> isNull(String fieldName) {
        addCriterion(fieldName + " is null");
        return new ConnectSymbols<T>((Criteria<T>) this);
    }

    public ConnectSymbols<T> isNull(SFunction<T, ?> fieldName) {
        addCriterion(LambdaUtil.columnToString(fieldName) + " is null");
        return new ConnectSymbols<T>((Criteria<T>) this);
    }

    public ConnectSymbols<T> isNotNull(String fieldName) {
        addCriterion(fieldName + " is not null");
        return new ConnectSymbols<T>((Criteria<T>) this);
    }

    public ConnectSymbols<T> isNotNull(SFunction<T, ?> fieldName) {
        addCriterion(LambdaUtil.columnToString(fieldName) + " is not null");
        return new ConnectSymbols<T>((Criteria<T>) this);
    }

    public ConnectSymbols<T> equalTo(boolean condition, String fieldName, Object value) {
        addCriterionParam(condition,fieldName, value, "=");
        return new ConnectSymbols<T>((Criteria<T>) this);
    }

    public ConnectSymbols<T> equalTo(boolean condition, SFunction<T, ?> fieldName, Object value) {
        addCriterionParam(condition, LambdaUtil.columnToString(fieldName), value, "=");
        return new ConnectSymbols<T>((Criteria<T>) this);
    }

    public ConnectSymbols<T> equalTo(String fieldName, Object value) {
        this.equalTo(true, fieldName, value);
        return new ConnectSymbols<T>((Criteria<T>) this);
    }

    public ConnectSymbols<T> equalTo(SFunction<T, ?> fieldName, Object value) {
        this.equalTo(true, fieldName, value);
        return new ConnectSymbols<T>((Criteria<T>) this);
    }


    public ConnectSymbols<T> notEqualTo(boolean condition, String fieldName, Object value) {
        addCriterionParam(condition,fieldName, value, "<>");
        return new ConnectSymbols<T>((Criteria<T>) this);
    }

    public ConnectSymbols<T> notEqualTo(boolean condition, SFunction<T, ?> fieldName, Object value) {
        addCriterionParam(condition,LambdaUtil.columnToString(fieldName), value, "<>");
        return new ConnectSymbols<T>((Criteria<T>) this);
    }

    public ConnectSymbols<T> notEqualTo(String fieldName, Object value) {
        this.notEqualTo(true, fieldName, value);
        return new ConnectSymbols<T>((Criteria<T>) this);
    }

    public ConnectSymbols<T> notEqualTo(SFunction<T, ?> fieldName, Object value) {
        this.notEqualTo(true, fieldName, value);
        return new ConnectSymbols<T>((Criteria<T>) this);
    }

    public ConnectSymbols<T> greaterThan(boolean condition, String fieldName, Object value) {
        addCriterionParam(condition,fieldName, value, ">");
        return new ConnectSymbols<T>((Criteria<T>) this);
    }

    public ConnectSymbols<T> greaterThan(boolean condition, SFunction<T, ?> fieldName, Object value) {
        addCriterionParam(condition,LambdaUtil.columnToString(fieldName), value, ">");
        return new ConnectSymbols<T>((Criteria<T>) this);
    }

    public ConnectSymbols<T> greaterThan(String fieldName, Object value) {
        this.greaterThan(true, fieldName, value);
        return new ConnectSymbols<T>((Criteria<T>) this);
    }

    public ConnectSymbols<T> greaterThan(SFunction<T, ?> fieldName, Object value) {
        this.greaterThan(true, fieldName, value);
        return new ConnectSymbols<T>((Criteria<T>) this);
    }

    public ConnectSymbols<T> greaterThanOrEqualTo(boolean condition, String fieldName, Object value) {
        addCriterionParam(condition,fieldName, value, ">=");
        return new ConnectSymbols<T>((Criteria<T>) this);
    }

    public ConnectSymbols<T> greaterThanOrEqualTo(boolean condition, SFunction<T, ?> fieldName, Object value) {
        addCriterionParam(condition, LambdaUtil.columnToString(fieldName), value, ">=");
        return new ConnectSymbols<T>((Criteria<T>) this);
    }

    public ConnectSymbols<T> greaterThanOrEqualTo(String fieldName, Object value) {
        this.greaterThanOrEqualTo(true, fieldName, value);
        return new ConnectSymbols<T>((Criteria<T>) this);
    }

    public ConnectSymbols<T> greaterThanOrEqualTo(SFunction<T, ?> fieldName, Object value) {
        this.greaterThanOrEqualTo(true, fieldName, value);
        return new ConnectSymbols<T>((Criteria<T>) this);
    }

    public ConnectSymbols<T> lessThan(boolean condition, String fieldName, Object value) {
        addCriterionParam(condition,fieldName, value, "<");
        return new ConnectSymbols<T>((Criteria<T>) this);
    }

    public ConnectSymbols<T> lessThan(boolean condition, SFunction<T, ?> fieldName, Object value) {
        addCriterionParam(condition,LambdaUtil.columnToString(fieldName), value, "<");
        return new ConnectSymbols<T>((Criteria<T>) this);
    }

    public ConnectSymbols<T> lessThan(String fieldName, Object value) {
        this.lessThan(true, fieldName, value);
        return new ConnectSymbols<T>((Criteria<T>) this);
    }

    public ConnectSymbols<T> lessThan(SFunction<T, ?> fieldName, Object value) {
        this.lessThan(true, fieldName, value);
        return new ConnectSymbols<T>((Criteria<T>) this);
    }

    public ConnectSymbols<T> lessThanOrEqualTo(boolean condition, String fieldName, Object value) {
        addCriterionParam(condition,fieldName, value, "<=");
        return new ConnectSymbols<T>((Criteria<T>) this);
    }

    public ConnectSymbols<T> lessThanOrEqualTo(boolean condition, SFunction<T, ?> fieldName, Object value) {
        addCriterionParam(condition,LambdaUtil.columnToString(fieldName), value, "<=");
        return new ConnectSymbols<T>((Criteria<T>) this);
    }

    public ConnectSymbols<T> lessThanOrEqualTo(String fieldName, Object value) {
        this.lessThanOrEqualTo(true, fieldName, value);
        return new ConnectSymbols<T>((Criteria<T>) this);
    }

    public ConnectSymbols<T> lessThanOrEqualTo(SFunction<T, ?> fieldName, Object value) {
        this.lessThanOrEqualTo(true, fieldName, value);
        return new ConnectSymbols<T>((Criteria<T>) this);
    }

    public ConnectSymbols<T> like(boolean condition, String fieldName, Object value) {
        addCriterionParam(condition,fieldName, value, "like");
        return new ConnectSymbols<T>((Criteria<T>) this);
    }

    public ConnectSymbols<T> like(boolean condition, SFunction<T, ?> fieldName, Object value) {
        addCriterionParam(condition,LambdaUtil.columnToString(fieldName), value, "like");
        return new ConnectSymbols<T>((Criteria<T>) this);
    }

    public ConnectSymbols<T> like(String fieldName, Object value) {
        this.like(true, fieldName, value);
        return new ConnectSymbols<T>((Criteria<T>) this);
    }

    public ConnectSymbols<T> like(SFunction<T, ?> fieldName, Object value) {
        this.like(true, fieldName, value);
        return new ConnectSymbols<T>((Criteria<T>) this);
    }

    public ConnectSymbols<T> notLike(boolean condition, String fieldName, Object value) {
        addCriterionParam(condition,fieldName, value, "not like");
        return new ConnectSymbols<T>((Criteria<T>) this);
    }

    public ConnectSymbols<T> notLike(boolean condition, SFunction<T, ?> fieldName, Object value) {
        addCriterionParam(condition,LambdaUtil.columnToString(fieldName), value, "not like");
        return new ConnectSymbols<T>((Criteria<T>) this);
    }

    public ConnectSymbols<T> notLike(String fieldName, Object value) {
        this.notLike(true, fieldName, value);
        return new ConnectSymbols<T>((Criteria<T>) this);
    }

    public ConnectSymbols<T> notLike(SFunction<T, ?> fieldName, Object value) {
        this.notLike(true, fieldName, value);
        return new ConnectSymbols<T>((Criteria<T>) this);
    }

    public ConnectSymbols<T> in(boolean condition, String fieldName, List<Object> values) {
        addCriterionParam(condition,fieldName, values, "in");
        return new ConnectSymbols<T>((Criteria<T>) this);
    }

    public ConnectSymbols<T> in(boolean condition, SFunction<T, ?> fieldName, List<Object> values) {
        addCriterionParam(condition,LambdaUtil.columnToString(fieldName), values, "in");
        return new ConnectSymbols<T>((Criteria<T>) this);
    }

    public ConnectSymbols<T> in(String fieldName, List<Object> values) {
        this.in(true, fieldName, values);
        return new ConnectSymbols<T>((Criteria<T>) this);
    }

    public ConnectSymbols<T> in(SFunction<T, ?> fieldName, List<Object> values) {
        this.in(true, fieldName, values);
        return new ConnectSymbols<T>((Criteria<T>) this);
    }

    public ConnectSymbols<T> notIn(boolean condition, String fieldName, List<Object> values) {
        addCriterionParam(condition,fieldName, values, "not in");
        return new ConnectSymbols<T>((Criteria<T>) this);
    }

    public ConnectSymbols<T> notIn(boolean condition, SFunction<T, ?> fieldName, List<Object> values) {
        addCriterionParam(condition,LambdaUtil.columnToString(fieldName), values, "not in");
        return new ConnectSymbols<T>((Criteria<T>) this);
    }

    public ConnectSymbols<T> notIn(String fieldName, List<Object> values) {
        this.notIn(true, fieldName, values);
        return new ConnectSymbols<T>((Criteria<T>) this);
    }

    public ConnectSymbols<T> notIn(SFunction<T, ?> fieldName, List<Object> values) {
        this.notIn(true, fieldName, values);
        return new ConnectSymbols<T>((Criteria<T>) this);
    }

    public ConnectSymbols<T> between(boolean condition, String fieldName, Object value1, Object value2) {
        addCriterionParam(condition,fieldName, value1, value2,"between");
        return new ConnectSymbols<T>((Criteria<T>) this);
    }

    public ConnectSymbols<T> between(boolean condition, SFunction<T, ?> fieldName, Object value1, Object value2) {
        addCriterionParam(condition,LambdaUtil.columnToString(fieldName), value1, value2,"between");
        return new ConnectSymbols<T>((Criteria<T>) this);
    }

    public ConnectSymbols<T> between(String fieldName, Object value1, Object value2) {
        this.between(true, fieldName, value1, value2);
        return new ConnectSymbols<T>((Criteria<T>) this);
    }

    public ConnectSymbols<T> between(SFunction<T, ?> fieldName, Object value1, Object value2) {
        this.between(true, fieldName, value1, value2);
        return new ConnectSymbols<T>((Criteria<T>) this);
    }

    public ConnectSymbols<T> notBetween(boolean condition, String fieldName, Object value1, Object value2) {
        addCriterionParam(condition,fieldName, value1, value2,"not between");
        return new ConnectSymbols<T>((Criteria<T>) this);
    }

    public ConnectSymbols<T> notBetween(boolean condition, SFunction<T, ?> fieldName, Object value1, Object value2) {
        addCriterionParam(condition,LambdaUtil.columnToString(fieldName), value1, value2,"not between");
        return new ConnectSymbols<T>((Criteria<T>) this);
    }

    public ConnectSymbols<T> notBetween(String fieldName, Object value1, Object value2) {
        this.notBetween(true, fieldName, value1, value2);
        return new ConnectSymbols<T>((Criteria<T>) this);
    }

    public ConnectSymbols<T> notBetween(SFunction<T, ?> fieldName, Object value1, Object value2) {
        this.notBetween(true, fieldName, value1, value2);
        return new ConnectSymbols<T>((Criteria<T>) this);
    }

}