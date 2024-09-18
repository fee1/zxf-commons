package com.zxf.sql.grammar;

/**
 * @author zhuxiaofeng
 * @date 2024/9/18
 */
public abstract class AbstractGrammer implements IGrammer{

    @Override
    public <T> IGrammer select(String... columns) {
        return this;
    }

    @Override
    public <T> IGrammer select(String column, String alias) {
        return this;
    }

}
