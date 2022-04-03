package com.zxf.elastic.model;

import lombok.Data;

/**
 * @author zhuxiaofeng
 * @date 2022/3/3
 */
@Data
public class User {

    private String user;

    private String message;

    private Integer uid;

    private Integer age;

    private String city;

    private String province;

    private String country;

    private String address;

    private Location location;

}
