package com.zxf.trace.util;


import com.zxf.trace.util.id.Snowflake;

/**
 * @author zhuxiaofeng
 * @date 2022/10/24
 */
public class IdGenerate {

    private static final LazyValue<Snowflake> INST_SNOWFLAKE = new LazyValue<>(Snowflake::new);

    public static String getSnowflakeId(){
        return String.valueOf(INST_SNOWFLAKE.get().nextId());
    }

}
