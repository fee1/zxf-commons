package com.zxf.common.utils;

import com.sun.istack.internal.Nullable;
import com.zxf.common.exception.SerializationException;

/**
 * 序列化
 * @author 朱晓峰
 */
public interface ByteSerializer<T>  {

    /**
     *
     * @param t 对象
     * @return byte[]
     * @throws SerializationException 序列化异常
     */
    @Nullable
    byte[] serialize(@Nullable T t) throws SerializationException;

    /**
     *
     * @param bytes 字节数组
     * @return T 对象
     * @throws SerializationException 序列化异常
     */
    @Nullable
    T deserialize(@Nullable byte[] bytes) throws SerializationException;

}
