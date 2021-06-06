package com.zxf.cache.redis.serializer;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.StandardCharsets;

/**
 * 前缀化序列化字符串redis存储
 *
 * @author 朱晓峰
 */
public class PrefixKeySerializer implements RedisSerializer<String> {

    private final String prefixKey;

    public PrefixKeySerializer(String prefixKey) {
        this.prefixKey = prefixKey;
    }

    /**
     * 序列化
     *
     * @param s 字符串
     * @return byte[] 数组
     * @throws SerializationException
     */
    @Override
    public byte[] serialize(String s) throws SerializationException {
        return String.format("%s%s", this.prefixKey, s).getBytes(StandardCharsets.UTF_8);
    }

    /**
     * 反序列化
     *
     * @param bytes 数组
     * @return String 字符串
     * @throws SerializationException
     */
    @Override
    public String deserialize(byte[] bytes) throws SerializationException {
        return new String(bytes, StandardCharsets.UTF_8).substring(prefixKey.length());
    }
}
