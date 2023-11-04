package com.zxf.trace.util;

import com.zxf.trace.constants.Constants;
import org.slf4j.MDC;

/**
 * @author zhuxiaofeng
 * @date 2022/10/24
 */
public class TraceFatch {

    public static String getTraceId(){
        String trackId = MDC.get(Constants.TRACE_ID);
        if (trackId == null || trackId.length() == 0){
            trackId = IdGenerate.getSnowflakeId();
        }
        return trackId;
    }

    public static boolean isExistTraceId(){
        String trackId = MDC.get(Constants.TRACE_ID);
        return !(trackId == null || trackId.length() == 0);
    }

}
