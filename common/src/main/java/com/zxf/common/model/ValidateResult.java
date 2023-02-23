package com.zxf.common.model;

import lombok.Data;

/**
 * @author zhuxiaofeng
 * @date 2023/2/23
 */
@Data
public class ValidateResult {

    /**
     * 校验不通过字段名
     */
    private String fieldName;

    /**
     * 校验不通过字段信息信息
     */
    private String message;


}
