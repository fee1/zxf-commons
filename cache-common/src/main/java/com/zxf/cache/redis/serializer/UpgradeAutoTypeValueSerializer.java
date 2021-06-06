package com.zxf.cache.redis.serializer;

import com.zxf.common.LazyValue;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

/**
 * 升级版自动序列化对象
 *
 * @author 朱晓峰
 */
public class UpgradeAutoTypeValueSerializer implements RedisSerializer<Object> {

    private final static LazyValue<UpgradeAutoTypeValueSerializer> INST = new LazyValue<>(UpgradeAutoTypeValueSerializer::new);

    private static UpgradeAutoTypeValueSerializer getInstance() {
        return INST.get();
    }

    private JsonValueSerializer jsonValueSerializer;

    public UpgradeAutoTypeValueSerializer() {
        this.jsonValueSerializer = new JsonValueSerializer();
    }

    @Override
    public byte[] serialize(Object o) throws SerializationException {
        if (o == null) {
            return new byte[0];
        }
        Class<?> aClass = o.getClass();
//        aClass.
        return new byte[0];
    }

    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        return null;
    }
}
