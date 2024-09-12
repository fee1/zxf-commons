package com.zxf.common.utils.model;

import lombok.Data;

/**
 * @author zhuxiaofeng
 * @date 2022/11/30
 */
@Data
public class ResponseDTO {

    /**
     * 是否成功，true是，false否
     */
    private Boolean success;

    /**
     * 信息
     */
    private String message;

}
