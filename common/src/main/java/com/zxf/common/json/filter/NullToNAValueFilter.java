package com.zxf.common.json.filter;

import com.alibaba.fastjson.serializer.ValueFilter;

/**
 * 将 null值替换成 N/A
 *
 * @author zhuxiaofeng
 * @date 2023/8/24
 */
public class NullToNAValueFilter implements ValueFilter {
    @Override
    public Object process(Object object, String name, Object value) {
        if (value == null) {
            return "N/A";
        }
        return value;
    }
}
