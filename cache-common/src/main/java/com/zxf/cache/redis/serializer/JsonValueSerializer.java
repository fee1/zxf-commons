package com.zxf.cache.redis.serializer;

import com.alibaba.fastjson.JSONObject;
import com.zxf.common.LazyValue;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.StandardCharsets;

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
    public byte[] serialize(Object o) throws SerializationException {
        if (o == null){
            return null;
        }
        return JSONObject.toJSONString(o).getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null || bytes.length == 0){
            return null;
        }
        byte[] o = new byte[ bytes.length - 1 ];
        System.arraycopy(bytes, 1, o, 0, bytes.length - 1 );
        String jsonStr = new String(o);
        return JSONObject.parse(jsonStr);
    }
}
