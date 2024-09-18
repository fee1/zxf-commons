package com.zxf.sql.grammar;

/**
 * @author zhuxiaofeng
 * @date 2024/9/18
 */
public interface IGrammer {

    <T> IGrammer select(SFunction<T, ?>... columns);

    <T> IGrammer select(SFunction<T, ?> column, String alias);

    <T> IGrammer select(String... columns);

    <T> IGrammer select(String column, String alias);

    IGrammer from(Class<?> rootTable);

    IGrammer join(JoinType joinType, Class<?> joinTable, Criteria criteria);

}
