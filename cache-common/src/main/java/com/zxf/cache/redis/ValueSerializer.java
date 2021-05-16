package com.zxf.cache.redis;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

/**
 * 序列化值字符串redis存储
 * @author 朱晓峰
 */
public class ValueSerializer implements RedisSerializer<String> {
    @Override
    public byte[] serialize(String s) throws SerializationException {
        return new byte[0];
    }

    @Override
    public String deserialize(byte[] bytes) throws SerializationException {
        return null;
    }
}
