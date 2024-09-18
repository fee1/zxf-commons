package com.zxf.common.utils.sql.u;

import com.google.common.base.CaseFormat;
import com.zxf.common.utils.sql.SFunction;

import java.lang.invoke.SerializedLambda;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 *
 * @author zhuxiaofeng
 * @date 2024/8/29
 */
public class LambdaUtil {

    /**
     * SerializedLambda 反序列化缓存
     */
    private static final Map<Class<?>, WeakReference<SerializedLambda>> FUNC_CACHE = new ConcurrentHashMap<>();

    /**
     * SerializedLambda
     */
    private static final Map<Class<?>, WeakReference<Class<?>>> FUNC_CLASS_CACHE = new ConcurrentHashMap<>();

    public static <T> String columnToString(SFunction<T, ?> column) {
        Class<?> clazz = column.getClass();
        SerializedLambda serializedLambda = Optional.ofNullable(FUNC_CACHE.get(clazz)).map(WeakReference::get).orElseGet(() -> {
            Method writeReplace = null;
            try {
                writeReplace = column.getClass().getDeclaredMethod("writeReplace");
                writeReplace.setAccessible(true);
                SerializedLambda lambda = (SerializedLambda) writeReplace.invoke(column);
                FUNC_CACHE.put(clazz, new WeakReference<>(lambda));
                return lambda;
            } catch (Exception e) {
                throw new RuntimeException(String.format("无法解析 %s 的字段", column));
            }
        });
        return methodNameToColumnName(serializedLambda.getImplMethodName());
    }

    private static final Pattern INSTANTIATED_METHOD_TYPE = Pattern.compile("\\(L(?<instantiatedMethodType>[\\S&&[^;)]]+);\\)L[\\S]+;");
    public static <T> String columnToString(boolean tablePrefix, SFunction<T, ?> column) {
        String columnToString = columnToString(column);
        if (!tablePrefix){
            return columnToString;
        }
        Class<?> columnClass = getColumnTableClass(column);
        String tableName = EntityUtil.getTableName(columnClass);
        return  tableName+ "." + columnToString;
    }

    private static String normalName(String name) {
        return name.replace('/', '.');
    }

    public static String methodNameToColumnName(String methodName){
        if (methodName.startsWith("get") || methodName.startsWith("set")){
            methodName = methodName.substring(3);
        }else if (methodName.startsWith("is")){
            methodName = methodName.substring(2);
        }
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, methodName);
    }

    public static Class<?> getColumnTableClass(SFunction<?, ?> column) {
        if (column == null) {
            return null;
        }
        Class<?> clazz = column.getClass();
        WeakReference<Class<?>> classWeakReference = FUNC_CLASS_CACHE.get(clazz);
        if (classWeakReference != null){
            return classWeakReference.get();
        }

        SerializedLambda serializedLambda = Optional.ofNullable(FUNC_CACHE.get(clazz)).map(WeakReference::get).orElseGet(() -> {
            Method writeReplace = null;
            try {
                writeReplace = column.getClass().getDeclaredMethod("writeReplace");
                writeReplace.setAccessible(true);
                SerializedLambda lambda = (SerializedLambda) writeReplace.invoke(column);
                FUNC_CACHE.put(clazz, new WeakReference<>(lambda));
                return lambda;
            } catch (Exception e) {
                throw new RuntimeException(String.format("无法解析 %s 的字段", column));
            }
        });
        Matcher matcher = INSTANTIATED_METHOD_TYPE.matcher(serializedLambda.getInstantiatedMethodType());
        if (!matcher.find()){
            throw new RuntimeException("无法解析 lambda 表达式，该 lambda 表达式不符合格式，该 lambda 表达式需要是实体类属性的 getter 方法。");
        }

        Class<?> columnTableClass = ClassUtil.toClassConfident(normalName(matcher.group("instantiatedMethodType")));
        FUNC_CLASS_CACHE.put(clazz, new WeakReference<>(columnTableClass));
        return columnTableClass;
    }

}
