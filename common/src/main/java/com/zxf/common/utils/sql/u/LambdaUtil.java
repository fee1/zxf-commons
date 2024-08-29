package com.zxf.common.utils.sql.u;

import com.google.common.base.CaseFormat;
import com.zxf.common.utils.sql.m.ColumnCache;
import com.zxf.common.utils.sql.SFunction;

import java.lang.invoke.SerializedLambda;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * TODO 缓存处理
 *
 * @author zhuxiaofeng
 * @date 2024/8/29
 */
public class LambdaUtil {

    /**
     * 字段映射
     */
    private static final Map<String, Map<String, ColumnCache>> LAMBDA_MAP = new ConcurrentHashMap<>();

    /**
     * SerializedLambda 反序列化缓存
     */
    private static final Map<Class<?>, WeakReference<SerializedLambda>> FUNC_CACHE = new ConcurrentHashMap<>();

    public static <T> String columnToString(SFunction<T, ?> column) {
        try {
            Method writeReplace = column.getClass().getDeclaredMethod("writeReplace");
            writeReplace.setAccessible(true);
            SerializedLambda serializedLambda = (SerializedLambda) writeReplace.invoke(column);
            return methodNameToColumnName(serializedLambda.getImplMethodName());
        }catch (Exception e){
            throw new RuntimeException(String.format("无法解析 %s 的字段", column));
        }
    }

    public static String methodNameToColumnName(String methodName){
        if (methodName.startsWith("get") || methodName.startsWith("set")){
            methodName = methodName.substring(3);
        }else if (methodName.startsWith("is")){
            methodName = methodName.substring(2);
        }
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, methodName);
    }

}
