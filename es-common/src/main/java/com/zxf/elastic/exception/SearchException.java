package com.zxf.elastic.exception;

/**
 * @author zhuxiaofeng
 * @date 2022/3/3
 */
public class SearchException extends RuntimeException {

    public SearchException(String errorMsg){
        super(errorMsg);
    }

    public SearchException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
