package com.zxf.method.trace.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * uuid重复几率很小，基于mac地址，可能会暴露mac地址
 * 不是纯数字，不适用于数据库的插入、删除和修改，不好维护索引和排序
 *
 * @author zhuxiaofeng
 * @date 2022/10/24
 */
public class UUID {

    /**
     * 一个静态方法,用于快速匹配字符串,该方法适合用于只匹配一次,且匹配全部字符串
     */
    private static final Pattern COMPILE = Pattern.compile("-", Pattern.LITERAL);

    public String builder(){
        return COMPILE.matcher(java.util.UUID.randomUUID().toString()).replaceAll(Matcher.quoteReplacement(""));
    }

}
