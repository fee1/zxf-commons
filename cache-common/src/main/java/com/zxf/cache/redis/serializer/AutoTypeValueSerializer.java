package com.zxf.cache.redis.serializer;

import com.zxf.common.LazyValue;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.StandardCharsets;

/**
 * 序列化值字符串redis存储
 * @author 朱晓峰
 */
public class AutoTypeValueSerializer implements RedisSerializer<Object> {

    private static final LazyValue<AutoTypeValueSerializer> INST = new LazyValue<>(AutoTypeValueSerializer::new);

    private JsonValueSerializer jsonValueSerializer;

    public static AutoTypeValueSerializer getInstance(){
        return INST.get();
    }

    public AutoTypeValueSerializer(){
        this.jsonValueSerializer = JsonValueSerializer.getInstance();
    }

    private static final byte BYTE = 'B';

    private static final byte STR = 'S';

    private static final byte LONG = 'L';

    private static final byte OTHER = 'O';

    @Override
    public byte[] serialize(Object o) throws SerializationException {
        if (o == null){
            return new byte[0];
        }
        byte[] bytes;
        byte type = OTHER;
        if (o instanceof Byte){
            bytes = (byte[]) o;
            type = BYTE;
        }else if (o instanceof String){
            bytes = ((String) o).getBytes(StandardCharsets.UTF_8);
            type = STR;
        }else if (o instanceof Long){
            bytes = String.valueOf(o).getBytes(StandardCharsets.UTF_8);
            type = LONG;
        }else {
            bytes = this.jsonValueSerializer.serialize(o);
            type = OTHER;
        }
        byte[] typeBytes = new byte[bytes.length + 1];
        typeBytes[0] = type;
        System.arraycopy(bytes, 0, typeBytes, 1, bytes.length);
        return typeBytes;
    }

    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        return null;
    }
}
