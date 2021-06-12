package com.zxf.cache.redis.serializer;

import com.alibaba.fastjson.JSONObject;
import com.zxf.cache.CacheObject;
import com.zxf.common.utils.LazyValue;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.StandardCharsets;

/**
 * 对象转json序列化方式
 *
 * @author 朱晓峰
 */
public class JsonValueSerializer implements RedisSerializer<Object> {

    private static final LazyValue<JsonValueSerializer> INST = new LazyValue<>(JsonValueSerializer::new);

    /**
     * 获取实例
     *
     * @return JsonValueSerializer JsonValueSerializer
     */
    public static JsonValueSerializer getInstance() {
        return INST.get();
    }

    private JsonValueSerializer(){}

    /**
     * 序列化
     *
     * @param o 对象
     * @return byte[] 数组
     * @throws SerializationException
     */
    @Override
    public byte[] serialize(Object o) throws SerializationException {
        if (o == null) {
            return null;
        }
        return JSONObject.toJSONString(o).getBytes(StandardCharsets.UTF_8);
    }

    /**
     * 反序列化
     *
     * @param bytes 数组
     * @return Object 对象
     * @throws SerializationException
     */
    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        byte[] o = new byte[bytes.length];
        System.arraycopy(bytes, 0, o, 0, bytes.length);
        String jsonStr = new String(o);
        return JSONObject.parse(jsonStr);
    }


}
