package com.zxf.elastic.model;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * @author zhuxiaofeng
 * @date 2022/3/3
 */
@Data
public class Hit {

    private String _index;

    private String _id;

    private String _score;

    private JSONObject fields;

    private JSONObject highlight;

}
