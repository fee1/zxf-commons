package com.zxf.cache.redis.serializer;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AutoTypeValueSerializerTest {

    @Test
    public void serializeAndDeserialize () {
        AutoTypeValueSerializer instance = AutoTypeValueSerializer.getInstance();
        byte b = 'b';
        byte[] serialize = instance.serialize(b);
        Assert.assertEquals(new Byte("98"), instance.deserialize(serialize));
        System.out.println("byte: "+ instance.deserialize(serialize));

        String s = "abc";
        byte[] serialize1 = instance.serialize(s);
        Assert.assertEquals("abc", (String) instance.deserialize(serialize1));
        System.out.println("string: "+  (String) instance.deserialize(serialize1));

        Long l = 123L;
        byte[] serialize2 = instance.serialize(l);
        Assert.assertEquals(new Long(123L), (Long)instance.deserialize(serialize2));
        System.out.println("long: "+ (Long)instance.deserialize(serialize2));

        Integer i = 123;
        byte[] serialize3 = instance.serialize(i);
        Assert.assertEquals(new Integer(123), (Integer)instance.deserialize(serialize3));
        System.out.println("Integer: "+(Integer)instance.deserialize(serialize3));

        People xiaoming = new People("xiaoming", "23");
        byte[] serialize4 = instance.serialize(xiaoming);
        Object deserialize = instance.deserialize(serialize4);
        Assert.assertEquals(xiaoming, deserialize);
        System.out.println("People class: "+ deserialize);
    }

    @Data
    @AllArgsConstructor
    public static class People{
        private String name;

        private String age;
    }

}