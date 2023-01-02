package com.zxf.common.id;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zhuxiaofeng
 * @date 2022/10/24
 */
public class UUID {

    /**
     * 一个静态方法,用于快速匹配字符串,该方法适合用于只匹配一次,且匹配全部字符串
     */
    private static final Pattern COMPILE = Pattern.compile("-", Pattern.LITERAL);

    public synchronized String builder(){
        return COMPILE.matcher(java.util.UUID.randomUUID().toString()).replaceAll(Matcher.quoteReplacement(""));
    }

}
