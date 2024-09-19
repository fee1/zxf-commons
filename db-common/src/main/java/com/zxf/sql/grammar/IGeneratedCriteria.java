package com.zxf.sql.grammar;

import java.util.List;

/**
 * @author zhuxiaofeng
 * @date 2024/9/19
 */
public interface IGeneratedCriteria {

    ConnectSymbols isNull(String fieldName);

    ConnectSymbols isNotNull(String fieldName);

    <T> ConnectSymbols isNull(SFunction<T, ?> fieldName);

    <T> ConnectSymbols isNotNull(SFunction<T, ?> fieldName);

    ConnectSymbols equalTo(String fieldName, Object value);

    <T> ConnectSymbols equalTo(SFunction<T, ?> fieldName, Object value);

    <T> ConnectSymbols equalTo(boolean condition, SFunction<T, ?> fieldName, Object value);

    ConnectSymbols equalTo(boolean condition, String fieldName, Object value);

    ConnectSymbols notEqualTo(String fieldName, Object value);

    <T> ConnectSymbols notEqualTo(SFunction<T, ?> fieldName, Object value);

    <T> ConnectSymbols notEqualTo(boolean condition, SFunction<T, ?> fieldName, Object value);

    ConnectSymbols notEqualTo(boolean condition, String fieldName, Object value);

    ConnectSymbols greaterThan(String fieldName, Object value);

    <T> ConnectSymbols greaterThan(SFunction<T, ?> fieldName, Object value);

    <T> ConnectSymbols greaterThan(boolean condition, SFunction<T, ?> fieldName, Object value);

    ConnectSymbols greaterThan(boolean condition, String fieldName, Object value);

    ConnectSymbols greaterThanOrEqualTo(String fieldName, Object value);

    <T> ConnectSymbols greaterThanOrEqualTo(SFunction<T, ?> fieldName, Object value);

    <T> ConnectSymbols greaterThanOrEqualTo(boolean condition, SFunction<T, ?> fieldName, Object value);

    ConnectSymbols greaterThanOrEqualTo(boolean condition, String fieldName, Object value);

    ConnectSymbols lessThan(String fieldName, Object value);

    <T> ConnectSymbols lessThan(SFunction<T, ?> fieldName, Object value);

    <T> ConnectSymbols lessThan(boolean condition, SFunction<T, ?> fieldName, Object value);

    ConnectSymbols lessThan(boolean condition, String fieldName, Object value);

    ConnectSymbols lessThanOrEqualTo(String fieldName, Object value);

    <T> ConnectSymbols lessThanOrEqualTo(SFunction<T, ?> fieldName, Object value);

    <T> ConnectSymbols lessThanOrEqualTo(boolean condition, SFunction<T, ?> fieldName, Object value);

    ConnectSymbols lessThanOrEqualTo(boolean condition, String fieldName, Object value);

    ConnectSymbols between(String fieldName, Object value1, Object value2);

    <T> ConnectSymbols between(SFunction<T, ?> fieldName, Object value1, Object value2);

    <T> ConnectSymbols between(boolean condition, SFunction<T, ?> fieldName, Object value1, Object value2);

    ConnectSymbols between(boolean condition, String fieldName, Object value1, Object value2);

    ConnectSymbols notBetween(String fieldName, Object value1, Object value2);

    <T> ConnectSymbols notBetween(SFunction<T, ?> fieldName, Object value1, Object value2);

    <T> ConnectSymbols notBetween(boolean condition, SFunction<T, ?> fieldName, Object value1, Object value2);

    ConnectSymbols notBetween(boolean condition, String fieldName, Object value1, Object value2);

    ConnectSymbols in(String fieldName, List<?> values);

    <T> ConnectSymbols in(SFunction<T, ?> fieldName, List<?> values);

    <T> ConnectSymbols in(boolean condition, SFunction<T, ?> fieldName, List<?> values);

    ConnectSymbols in(boolean condition, String fieldName, List<?> values);

    ConnectSymbols notIn(String fieldName, List<?> values);

    <T> ConnectSymbols notIn(SFunction<T, ?> fieldName, List<?> values);

    <T> ConnectSymbols notIn(boolean condition, SFunction<T, ?> fieldName, List<?> values);

    ConnectSymbols notIn(boolean condition, String fieldName, List<?> values);

    ConnectSymbols like(String fieldName, Object value);

    <T> ConnectSymbols like(SFunction<T, ?> fieldName, Object value);

    <T> ConnectSymbols like(boolean condition, SFunction<T, ?> fieldName, Object value);

    ConnectSymbols like(boolean condition, String fieldName, Object value);

    ConnectSymbols notLike(String fieldName, Object value);

    <T> ConnectSymbols notLike(SFunction<T, ?> fieldName, Object value);

    <T> ConnectSymbols notLike(boolean condition, SFunction<T, ?> fieldName, Object value);

    ConnectSymbols notLike(boolean condition, String fieldName, Object value);

}
