package com.zxf.cache.redis.serializer;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.Assert;
import org.junit.jupiter.api.Test;


public class JsonValueSerializerTest {

    @Test
    public void serializeAnDdeserialize() {
        JsonValueSerializer instance = JsonValueSerializer.getInstance();
        People xiaoming = new People("xiaoming", "22");
        byte[] serialize = instance.serialize(xiaoming);
        System.out.println("-------serialize: "+ serialize);

        Object deserialize = instance.deserialize(serialize);
        Assert.assertEquals("{\"name\":\"xiaoming\",\"age\":\"22\"}", JSONObject.toJSONString(deserialize));
    }

    @Data
    @AllArgsConstructor
    public static class People{
        private String name;

        private String age;
    }

}