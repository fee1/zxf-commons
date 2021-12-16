package com.zxf;

import com.alibaba.fastjson.JSONObject;
import com.zxf.common.utils.BeanUtil;
import lombok.Data;
import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public class BeanUtilTest {

    @Test
    public void mapToBean() {
        Map map = new HashMap();
        map.put("name", "xxx");
        map.put("age", 18);
        Persion persion = BeanUtil.mapToBean(map, Persion.class);
        System.out.println(persion.toString());
    }

    @Test
    public void mapsToBeans() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "xxx");
        map.put("age", 18);
        Map map2 = new HashMap();
        map2.put("name", "xxx2");
        map2.put("age", 18);
        List<Map<String, Object>> list = new LinkedList<>();
        list.add(map);
        list.add(map2);
        List<Persion> persions = BeanUtil.mapsToBeans(list, Persion.class);
        System.out.println(JSONObject.toJSONString(persions));
    }

    @Test
    public void newInstance() {
        Persion persion = BeanUtil.newInstance(Persion.class);

        try {
            Student student = BeanUtil.newInstance(Student.class);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Data
    public static class Persion{
        private int age;
        private String name;
    }

    public static class Student extends Persion{
        public Student(String name, int age){
        }
    }
}