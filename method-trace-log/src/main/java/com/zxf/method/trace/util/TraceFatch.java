package com.zxf.method.trace.util;

import com.zxf.method.trace.constants.Constants;
import org.slf4j.MDC;
import org.springframework.util.StringUtils;

/**
 * @author zhuxiaofeng
 * @date 2022/10/24
 */
public class TraceFatch {

    public static String getTraceId(){
        String trackId = MDC.get(Constants.TRACE_ID);
        if (StringUtils.isEmpty(trackId)){
            trackId = IdGenerate.getSnowflakeId();
        }
        return trackId;
    }

    public static boolean isExistTraceId(){
        String trackId = MDC.get(Constants.TRACE_ID);
        return StringUtils.isEmpty(trackId);
    }

}
