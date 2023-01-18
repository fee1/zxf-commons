package com.zxf.method.trace.util;


import com.zxf.common.id.Snowflake;
import com.zxf.common.id.UUID;
import com.zxf.common.utils.LazyValue;

/**
 * id生成工具获取取
 *
 * @author zhuxiaofeng
 * @date 2022/10/24
 */
public class IdGenerate {

    private static final LazyValue<Snowflake> INST_SNOWFLAKE = new LazyValue<>(Snowflake::new);
    private static final LazyValue<UUID> INST_UUID = new LazyValue<>(UUID::new);

    public static String getSnowflakeId(){
        return String.valueOf(INST_SNOWFLAKE.get().nextId());
    }

    public static String getUUIDId(){
        return INST_UUID.get().builder();
    }

}
