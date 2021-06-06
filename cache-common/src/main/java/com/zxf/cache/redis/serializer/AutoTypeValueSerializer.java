package com.zxf.cache.redis.serializer;

import com.zxf.common.LazyValue;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * 序列化值字符串redis存储
 *
 * @author 朱晓峰
 */
public class AutoTypeValueSerializer implements RedisSerializer<Object> {

    private static final LazyValue<AutoTypeValueSerializer> INST = new LazyValue<>(AutoTypeValueSerializer::new);

    private JsonValueSerializer jsonValueSerializer;

    /**
     * 获取实例
     *
     * @return AutoTypeValueSerializer AutoTypeValueSerializer
     */
    public static AutoTypeValueSerializer getInstance() {
        return INST.get();
    }

    public AutoTypeValueSerializer() {
        this.jsonValueSerializer = JsonValueSerializer.getInstance();
    }

    private static final byte BYTE = 'B';

    private static final byte STR = 'S';

    private static final byte LONG = 'L';

    private static final byte OTHER = 'O';

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
            return new byte[0];
        }
        byte[] bytes;
        byte type = OTHER;
        if (o instanceof byte[]) {
            bytes = (byte[]) o;
            type = BYTE;
        } else if (o instanceof String) {
            bytes = ((String) o).getBytes(StandardCharsets.UTF_8);
            type = STR;
        } else if (o instanceof Long) {
            bytes = String.valueOf(o).getBytes(StandardCharsets.UTF_8);
            type = LONG;
        } else {
            bytes = this.jsonValueSerializer.serialize(o);
        }
        byte[] typeBytes = new byte[bytes.length + 1];
        typeBytes[0] = type;
        System.arraycopy(bytes, 0, typeBytes, 1, bytes.length);
        return typeBytes;
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
        byte flag = bytes[0];
        if (flag == BYTE) {
            return Arrays.copyOfRange(bytes, 1, bytes.length);
        } else if (flag == STR) {
            return new String(bytes, 1, bytes.length - 1, StandardCharsets.UTF_8);
        } else if (flag == LONG) {
            return Long.parseLong(new String(bytes, 1, bytes.length - 1, StandardCharsets.UTF_8));
        } else {
            byte[] jsonByte = new byte[bytes.length - 1];
            System.arraycopy(bytes, 1, jsonByte, 0, bytes.length - 1);
            return this.jsonValueSerializer.deserialize(jsonByte);
        }
    }
}
