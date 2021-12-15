package com.zxf.common.utils;

import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * 注解工具类
 *
 * @author zhuxiaofeng
 * @date 2021/12/15
 */
public class AnnotationUtil extends AnnotationConfigUtils {

    public static AnnotationAttributes attributesForAll(AnnotatedTypeMetadata metadata, Class<?> annotationClass) {
        return attributesForAll(metadata, annotationClass.getName());
    }

    static AnnotationAttributes attributesForAll(AnnotatedTypeMetadata metadata, String annotationClassName) {
        return AnnotationAttributes.fromMap(metadata.getAnnotationAttributes(annotationClassName, false));
    }
}
