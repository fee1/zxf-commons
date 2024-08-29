package com.zxf.common.utils.sql;

import java.io.Serializable;
import java.util.function.Function;

/**
 * @author zhuxiaofeng
 * @date 2024/8/28
 */
@FunctionalInterface
public interface SFunction<T, R> extends Function<T, R>, Serializable {
}

