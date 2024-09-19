package com.zxf.sql.grammar;

/**
 * @author zhuxiaofeng
 * @date 2024/9/19
 */
public interface ISimpleGrammer {

    <T> IGrammer select(String... columns);

    <T> IGrammer select(String column, String alias);

}
