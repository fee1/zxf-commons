package com.zxf.cache.utils;

import com.zxf.common.exception.SerializationException;
import com.zxf.common.utils.ByteSerializer;
import com.zxf.common.utils.ProtostuffUtils;

/**
 * @author 朱晓峰
 */
public class ProtoSerializer<T> implements ByteSerializer<T> {

    /**
     * 类型
     */
    private final Class<T> c;

    public ProtoSerializer(Class<T> c) {
        this.c = c;
    }

    /**
     * 序列化
     * @param t 对象
     * @return 字节数组
     * @throws SerializationException
     */
    @Override
    public byte[] serialize(T t) throws SerializationException {
        if (t == null) {
            return new byte[0];
        }
        return ProtostuffUtils.serialize(t);
    }

    /**
     * 反序列化
     * @param bytes 字节数组
     * @return 对象
     * @throws SerializationException
     */
    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        return ProtostuffUtils.deserialize(bytes, c);
    }

}
