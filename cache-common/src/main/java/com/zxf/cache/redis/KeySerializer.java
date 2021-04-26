package com.zxf.cache.redis;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.StandardCharsets;

/**
 * redis 存储对象
 * @author 朱晓峰
 */
public class KeySerializer implements RedisSerializer<String> {

    private final String prefixKey;

    public KeySerializer(String prefixKey) {
        this.prefixKey = prefixKey;
    }

    @Override
    public byte[] serialize(String s) throws SerializationException {
        return String.format("%s%s", this.prefixKey, s).getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public String deserialize(byte[] bytes) throws SerializationException {
        return new String(bytes, StandardCharsets.UTF_8).substring(prefixKey.length());
    }
}
