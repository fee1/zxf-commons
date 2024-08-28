package com.zxf.common.utils.sql;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhuxiaofeng
 * @date 2024/8/28
 */
abstract class GeneratedCriteria {
    protected List<cn.mezeron.jianzan.utils.sql.Criterion> criteria;

    protected Map<String, Object> params;

    protected GeneratedCriteria() {
        super();
        criteria = new ArrayList<cn.mezeron.jianzan.utils.sql.Criterion>();
        params = new HashMap<>();
    }

    public boolean isValid() {
        return criteria.size() > 0;
    }

    protected List<cn.mezeron.jianzan.utils.sql.Criterion> getCriteria() {
        return criteria;
    }

    protected Map<String, Object> getParams() {
        return params;
    }

    protected void addCriterion(String condition) {
        if (condition == null) {
            throw new RuntimeException("Value for condition cannot be null");
        }
        cn.mezeron.jianzan.utils.sql.Criterion criterion = new cn.mezeron.jianzan.utils.sql.Criterion(condition);
        cn.mezeron.jianzan.utils.sql.Criteria criteria = (cn.mezeron.jianzan.utils.sql.Criteria) this;
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

    public cn.mezeron.jianzan.utils.sql.ConnectSymbols isNull(String fieldName) {
        addCriterion(fieldName + " is null");
        return new cn.mezeron.jianzan.utils.sql.ConnectSymbols((cn.mezeron.jianzan.utils.sql.Criteria) this);
    }

    public cn.mezeron.jianzan.utils.sql.ConnectSymbols isNotNull(String fieldName) {
        addCriterion(fieldName + " is not null");
        return new cn.mezeron.jianzan.utils.sql.ConnectSymbols((cn.mezeron.jianzan.utils.sql.Criteria) this);
    }

    public cn.mezeron.jianzan.utils.sql.ConnectSymbols equalTo(boolean condition, String fieldName, Object value) {
        addCriterionParam(condition,fieldName, value, "=");
        return new cn.mezeron.jianzan.utils.sql.ConnectSymbols((cn.mezeron.jianzan.utils.sql.Criteria) this);
    }

    public cn.mezeron.jianzan.utils.sql.ConnectSymbols equalTo(String fieldName, Object value) {
        this.equalTo(true, fieldName, value);
        return new cn.mezeron.jianzan.utils.sql.ConnectSymbols((cn.mezeron.jianzan.utils.sql.Criteria) this);
    }

    public cn.mezeron.jianzan.utils.sql.ConnectSymbols notEqualTo(boolean condition, String fieldName, Object value) {
        addCriterionParam(condition,fieldName, value, "<>");
        return new cn.mezeron.jianzan.utils.sql.ConnectSymbols((cn.mezeron.jianzan.utils.sql.Criteria) this);
    }

    public cn.mezeron.jianzan.utils.sql.ConnectSymbols notEqualTo(String fieldName, Object value) {
        this.notEqualTo(true, fieldName, value);
        return new cn.mezeron.jianzan.utils.sql.ConnectSymbols((cn.mezeron.jianzan.utils.sql.Criteria) this);
    }

    public cn.mezeron.jianzan.utils.sql.ConnectSymbols greaterThan(boolean condition, String fieldName, Object value) {
        addCriterionParam(condition,fieldName, value, ">");
        return new cn.mezeron.jianzan.utils.sql.ConnectSymbols((cn.mezeron.jianzan.utils.sql.Criteria) this);
    }

    public cn.mezeron.jianzan.utils.sql.ConnectSymbols greaterThan(String fieldName, Object value) {
        this.greaterThan(true, fieldName, value);
        return new cn.mezeron.jianzan.utils.sql.ConnectSymbols((cn.mezeron.jianzan.utils.sql.Criteria) this);
    }

    public cn.mezeron.jianzan.utils.sql.ConnectSymbols greaterThanOrEqualTo(boolean condition, String fieldName, Object value) {
        addCriterionParam(condition,fieldName, value, ">=");
        return new cn.mezeron.jianzan.utils.sql.ConnectSymbols((cn.mezeron.jianzan.utils.sql.Criteria) this);
    }

    public cn.mezeron.jianzan.utils.sql.ConnectSymbols greaterThanOrEqualTo(String fieldName, Object value) {
        this.greaterThanOrEqualTo(true, fieldName, value);
        return new cn.mezeron.jianzan.utils.sql.ConnectSymbols((cn.mezeron.jianzan.utils.sql.Criteria) this);
    }

    public cn.mezeron.jianzan.utils.sql.ConnectSymbols lessThan(boolean condition, String fieldName, Object value) {
        addCriterionParam(condition,fieldName, value, "<");
        return new cn.mezeron.jianzan.utils.sql.ConnectSymbols((cn.mezeron.jianzan.utils.sql.Criteria) this);
    }

    public cn.mezeron.jianzan.utils.sql.ConnectSymbols lessThan(String fieldName, Object value) {
        this.lessThan(true, fieldName, value);
        return new cn.mezeron.jianzan.utils.sql.ConnectSymbols((cn.mezeron.jianzan.utils.sql.Criteria) this);
    }

    public cn.mezeron.jianzan.utils.sql.ConnectSymbols lessThanOrEqualTo(boolean condition, String fieldName, Object value) {
        addCriterionParam(condition,fieldName, value, "<=");
        return new cn.mezeron.jianzan.utils.sql.ConnectSymbols((cn.mezeron.jianzan.utils.sql.Criteria) this);
    }

    public cn.mezeron.jianzan.utils.sql.ConnectSymbols lessThanOrEqualTo(String fieldName, Object value) {
        this.lessThanOrEqualTo(true, fieldName, value);
        return new cn.mezeron.jianzan.utils.sql.ConnectSymbols((cn.mezeron.jianzan.utils.sql.Criteria) this);
    }

    public cn.mezeron.jianzan.utils.sql.ConnectSymbols like(boolean condition, String fieldName, Object value) {
        addCriterionParam(condition,fieldName, value, "like");
        return new cn.mezeron.jianzan.utils.sql.ConnectSymbols((cn.mezeron.jianzan.utils.sql.Criteria) this);
    }

    public cn.mezeron.jianzan.utils.sql.ConnectSymbols like(String fieldName, Object value) {
        this.like(true, fieldName, value);
        return new cn.mezeron.jianzan.utils.sql.ConnectSymbols((cn.mezeron.jianzan.utils.sql.Criteria) this);
    }

    public cn.mezeron.jianzan.utils.sql.ConnectSymbols notLike(boolean condition, String fieldName, Object value) {
        addCriterionParam(condition,fieldName, value, "not like");
        return new cn.mezeron.jianzan.utils.sql.ConnectSymbols((cn.mezeron.jianzan.utils.sql.Criteria) this);
    }

    public cn.mezeron.jianzan.utils.sql.ConnectSymbols notLike(String fieldName, Object value) {
        this.notLike(true, fieldName, value);
        return new cn.mezeron.jianzan.utils.sql.ConnectSymbols((cn.mezeron.jianzan.utils.sql.Criteria) this);
    }

    public cn.mezeron.jianzan.utils.sql.ConnectSymbols in(boolean condition, String fieldName, List<Object> values) {
        addCriterionParam(condition,fieldName, values, "in");
        return new cn.mezeron.jianzan.utils.sql.ConnectSymbols((cn.mezeron.jianzan.utils.sql.Criteria) this);
    }

    public cn.mezeron.jianzan.utils.sql.ConnectSymbols in(String fieldName, List<Object> values) {
        this.in(true, fieldName, values);
        return new cn.mezeron.jianzan.utils.sql.ConnectSymbols((cn.mezeron.jianzan.utils.sql.Criteria) this);
    }

    public cn.mezeron.jianzan.utils.sql.ConnectSymbols notIn(boolean condition, String fieldName, List<Object> values) {
        addCriterionParam(condition,fieldName, values, "not in");
        return new cn.mezeron.jianzan.utils.sql.ConnectSymbols((cn.mezeron.jianzan.utils.sql.Criteria) this);
    }

    public cn.mezeron.jianzan.utils.sql.ConnectSymbols notIn(String fieldName, List<Object> values) {
        this.notIn(true, fieldName, values);
        return new cn.mezeron.jianzan.utils.sql.ConnectSymbols((cn.mezeron.jianzan.utils.sql.Criteria) this);
    }

    public cn.mezeron.jianzan.utils.sql.ConnectSymbols between(boolean condition, String fieldName, Object value1, Object value2) {
        addCriterionParam(condition,fieldName, value1, value2,"between");
        return new cn.mezeron.jianzan.utils.sql.ConnectSymbols((cn.mezeron.jianzan.utils.sql.Criteria) this);
    }

    public cn.mezeron.jianzan.utils.sql.ConnectSymbols between(String fieldName, Object value1, Object value2) {
        this.between(true, fieldName, value1, value2);
        return new cn.mezeron.jianzan.utils.sql.ConnectSymbols((cn.mezeron.jianzan.utils.sql.Criteria) this);
    }

    public cn.mezeron.jianzan.utils.sql.ConnectSymbols notBetween(boolean condition, String fieldName, Object value1, Object value2) {
        addCriterionParam(condition,fieldName, value1, value2,"not between");
        return new cn.mezeron.jianzan.utils.sql.ConnectSymbols((cn.mezeron.jianzan.utils.sql.Criteria) this);
    }

    public cn.mezeron.jianzan.utils.sql.ConnectSymbols notBetween(String fieldName, Object value1, Object value2) {
        this.notBetween(true, fieldName, value1, value2);
        return new cn.mezeron.jianzan.utils.sql.ConnectSymbols((cn.mezeron.jianzan.utils.sql.Criteria) this);
    }

}