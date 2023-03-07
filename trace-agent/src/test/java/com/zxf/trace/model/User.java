package com.zxf.trace.model;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhuxiaofeng
 * @date 2023/2/15
 */
@Slf4j
public class User {

    public void getName(){
        log.debug("HH");
//        getException();
        getAge();
    }

    void getException(){
        throw new RuntimeException("hh guyide");
    }

     void getAge(){
        log.debug("19");
    }

}
