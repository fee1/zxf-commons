package com.zxf.cache;

import lombok.Data;

/**
 * @author 朱晓峰
 */
@Data
public class CacheObject {

    private Class<?> c;

    private byte[] b;

    public CacheObject(Class<?> c, byte[] b) {
        this.c = c;
        this.b = b;
    }
}
