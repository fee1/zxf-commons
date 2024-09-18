package com.zxf.sql.grammar;

import java.io.Serializable;
import java.util.function.Function;

/**
 * @author zhuxiaofeng
 * @date 2024/8/28
 */
@FunctionalInterface
public interface SFunction<T, R> extends Function<T, R>, Serializable {
}

