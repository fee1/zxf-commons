package com.zxf.cache.redis.serializer;

import com.zxf.common.LazyValue;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

/**
 * 对象转json序列化方式
 * @author 朱晓峰
 */
public class JsonValueSerializer implements RedisSerializer<Object> {

    private static final LazyValue<JsonValueSerializer> INST = new LazyValue<>(JsonValueSerializer::new);

    public static JsonValueSerializer getInstance(){
        return INST.get();
    }

    @Override
    public byte[] serialize(Object s) throws SerializationException {
        return new byte[0];
    }

    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        return null;
    }
}
