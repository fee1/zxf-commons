package com.zxf.elastic.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author zhuxiaofeng
 * @date 2022/4/14
 */
@Data
@AllArgsConstructor
public class Sort {

    private String fieldName;

    private Sorting order;

    public enum Sorting {
        ASC("asc"),
        DESC("desc");

        private final String name;

        private Sorting(String s) {
            name = s;
        }

        @Override
        public String toString() {
            return name;
        }
    }

}
