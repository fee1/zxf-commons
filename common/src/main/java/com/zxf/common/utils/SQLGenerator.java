package com.zxf.common.utils;

import com.google.common.base.CaseFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SQLGenerator {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Map<String, Object> allParams;

    protected List<String> selectFields;

    protected Class<?> tableClass;

    public SQLGenerator(Class<?> tableClass) {
        oredCriteria = new ArrayList<Criteria>();
        selectFields = new ArrayList<>();
        allParams = new HashMap<>();
        this.tableClass = tableClass;;
    }

    public void select(String... fieldName) {
        this.selectFields = Arrays.asList(fieldName);
    }

    public void orderByDesc(String... fieldName) {
        this.orderByClause = String.join(",", fieldName) + " desc";
    }

    public void orderByAsc(String... fieldName) {
        this.orderByClause = String.join(",", fieldName) + " asc";
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public Map<String, Object> getAllParams() {
        return allParams;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        allParams.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected Map<String, Object> params;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
            params = new HashMap<>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        public Map<String, Object> getParams() {
            return params;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        private String simpleValueCondition(String fieldName, String operator) {
            return String.format("%s %s :%s", fieldName, operator, fieldName);
        }

        private void addCriterionAndParam(String fieldName, Object value, String operator) {
            addCriterion(this.simpleValueCondition(fieldName, operator));
            addParams(fieldName, value);
        }

        private void addCriterionAndParam(String fieldName, Object value1, Object value2,String operator) {
            addCriterion(this.betweenValueCondition(fieldName, operator));
            addParams(fieldName + "1", value1);
            addParams(fieldName + "2", value2);
        }

        private String matchCondition(String fieldName, String operator) {
            return String.format("%s %s:%s", fieldName, operator, fieldName);
        }

        private String multiValueCondition(String fieldName, String operator) {
            return String.format("%s %s:(%s)", fieldName, operator, fieldName);
        }

        private String betweenValueCondition(String fieldName, String operator) {
            return String.format("%s %s %s1 and %s2", fieldName, operator, fieldName, fieldName);
        }

        private void addParams(String fieldName, Object value) {
            this.params.put(fieldName, value);
        }

        public Criteria andIsNull(String fieldName) {
            addCriterion(fieldName + " is null");
            return (Criteria) this;
        }

        public Criteria andIsNotNull(String fieldName) {
            addCriterion(fieldName + " is not null");
            return (Criteria) this;
        }

        public Criteria andEqualTo(String fieldName, Object value) {
            addCriterionAndParam(fieldName, value, "=");
            return (Criteria) this;
        }

        public Criteria andNotEqualTo(String fieldName, Object value) {
            addCriterionAndParam(fieldName, value, "<>");
            return (Criteria) this;
        }

        public Criteria andGreaterThan(String fieldName, Object value) {
            addCriterionAndParam(fieldName, value, ">");
            return (Criteria) this;
        }

        public Criteria andGreaterThanOrEqualTo(String fieldName, Object value) {
            addCriterionAndParam(fieldName, value, ">=");
            return (Criteria) this;
        }

        public Criteria andLessThan(String fieldName,Object value) {
            addCriterionAndParam(fieldName, value, "<");
            return (Criteria) this;
        }

        public Criteria andLessThanOrEqualTo(String fieldName,Object value) {
            addCriterionAndParam(fieldName, value, "<=");
            return (Criteria) this;
        }

        public Criteria andLike(String fieldName,Object value) {
            addCriterionAndParam(fieldName, value, "like");
            return (Criteria) this;
        }

        public Criteria andNotLike(String fieldName,Object value) {
            addCriterionAndParam(fieldName, value, "not like");
            return (Criteria) this;
        }

        public Criteria andIn(String fieldName,List<Object> values) {
            addCriterionAndParam(fieldName, values, "in");
            return (Criteria) this;
        }

        public Criteria andNotIn(String fieldName, List<Object> values) {
            addCriterionAndParam(fieldName, values, "not in");
            return (Criteria) this;
        }

        public Criteria andBetween(String fieldName, Object value1, Object value2) {
            addCriterionAndParam(fieldName, value1, value2,"between");
            return (Criteria) this;
        }

        public Criteria andNotBetween(String fieldName, Object value1, Object value2) {
            addCriterionAndParam(fieldName, value1, value2,"not between");
            return (Criteria) this;
        }

    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }

    public String generatorSql(){
        //获取类型的泛型，获取类名
        String simpleName = tableClass.getSimpleName();
        //类名从驼峰变成下划线的方式
        String tableName = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, simpleName);
        String sql = "";
        if (!selectFields.isEmpty()) {
            sql = "select " + String.join(",", selectFields) + " from " + tableName;
        }else {
            sql = "select * from " + tableName;
        }
        List<String> conditionList = new ArrayList<>();
        for (Criteria criteria : oredCriteria) {
            if (criteria.isValid()){
                List<String> subCondition = criteria.getCriteria().stream().map(Criterion::getCondition).collect(Collectors.toList());
                String condition = String.join(" and ", subCondition);
                conditionList.add(" ( " + condition + " ) ");
                this.allParams.putAll(criteria.getParams());
            }
        }
        sql = sql + " where " + String.join(" or ", conditionList) + "order by " + getOrderByClause();
        return sql;
    }

}