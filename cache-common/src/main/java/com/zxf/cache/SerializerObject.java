package com.zxf.cache;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Proto 序列化对象
 * @author 朱晓峰
 */
@Data
@NoArgsConstructor
public class SerializerObject {

    private Object object;

    public SerializerObject(Object o){
        this.object = o;
    }

}
