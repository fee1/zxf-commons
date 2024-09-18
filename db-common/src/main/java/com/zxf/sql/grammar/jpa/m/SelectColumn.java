package com.zxf.sql.grammar.jpa.m;

import com.zxf.sql.grammar.SFunction;
import lombok.Data;

/**
 * @author zhuxiaofeng
 * @date 2024/9/18
 */
@Data
public class SelectColumn<T> {

    private SFunction<T, ?> column;

    private String alias;

}
