package com.zxf.wrapper;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * json 格式请求包装类
 * @author zhuxiaofeng
 * @date 2021/7/5
 */
public class JsonRequestWrapper extends RequestWrapper {

    private JSONObject bodyJson;

    public JsonRequestWrapper(HttpServletRequest request) {
        super(request);
        String bodyStr = super.getBodyStr();
        this.bodyJson = StringUtils.isNotBlank(bodyStr) ? JSONObject.parseObject(bodyStr) : new JSONObject();
    }


}

