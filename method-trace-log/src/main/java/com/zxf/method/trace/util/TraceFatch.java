package com.zxf.method.trace.util;

import com.zxf.method.trace.constants.Constants;
import org.slf4j.MDC;
import org.springframework.util.StringUtils;

/**
 * traceId 获取类
 *
 * @author zhuxiaofeng
 * @date 2022/10/24
 */
public class TraceFatch {

    /**
     * 获取当前线程的traceId，获取不到直接重新生成
     *
     * @return string
     */
    public static String getTraceId(){
        String trackId = MDC.get(Constants.TRACE_ID);
        if (StringUtils.isEmpty(trackId)){
            trackId = IdGenerate.getSnowflakeId();
        }
        return trackId;
    }

    /**
     * 判断当前线程是否存在traceId
     *
     * @return bool
     */
    public static boolean isExistTraceId(){
        String trackId = MDC.get(Constants.TRACE_ID);
        return !StringUtils.isEmpty(trackId);
    }

}
