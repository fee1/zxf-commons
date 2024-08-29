package com.zxf.common.utils.sql;

import java.util.List;

/**
 * @author zhuxiaofeng
 * @date 2024/8/28
 */
public class Criterion<T> {
    private String condition;

    private Object value;

    private Object secondValue;

    private boolean noValue;

    private boolean singleValue;

    private boolean betweenValue;

    private boolean listValue;

    private ConnectSymbols<T> connectSymbol;

    private String typeHandler;

    public ConnectSymbols<T> getConnectSymbol() {
        return connectSymbol;
    }

    public void setConnectSymbol(ConnectSymbols<T> connectSymbol) {
        this.connectSymbol = connectSymbol;
    }

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