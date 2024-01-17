package com.zxf.common.utils;


import org.junit.Test;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author zhuxiaofeng
 * @date 2021/9/29
 */
public class ExcelToolUtilTest {

    @Test
    public void produceExcelFile() throws FileNotFoundException {
        Map<String, Object> map1 = new HashMap<>();
        map1.put("name", "小明");
        map1.put("age", "20");
        Map<String, Object> map2 = new HashMap<>();
        map2.put("name", "小聪");
        map2.put("age", "20");
        List<Map<String, Object>> list = new LinkedList<>();
        list.add(map1);
        list.add(map2);
        Map<String, String> fieldMapping = new LinkedHashMap<>();
        fieldMapping.put("name", "姓名");
        fieldMapping.put("age", "年龄");
        URL resource = ExcelToolUtil.class.getClassLoader().getResource("");
        ExcelToolUtil.produceExcelFile(list, fieldMapping, "test.xls", resource.getPath());
    }

    @Test
    public void produceExcelFileOverMillion() {

    }
}