package com.zxf.common.id;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zhuxiaofeng
 * @date 2022/10/24
 */
public class UUID {

    private static final Pattern COMPILE = Pattern.compile("-", Pattern.LITERAL);

    public synchronized String builder(){
        return COMPILE.matcher(java.util.UUID.randomUUID().toString()).replaceAll(Matcher.quoteReplacement(""));
    }

}
