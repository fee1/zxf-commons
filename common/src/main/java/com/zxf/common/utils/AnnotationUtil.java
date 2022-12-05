package com.zxf.common.utils;

import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.lang.annotation.Annotation;

/**
 * 注解工具类
 *
 * @author zhuxiaofeng
 * @date 2021/12/15
 */
public class AnnotationUtil extends AnnotatedElementUtils {

    public static AnnotationAttributes attributesForAll(AnnotatedTypeMetadata metadata, Class<?> annotationClass) {
        return attributesForAll(metadata, annotationClass.getName());
    }

    static AnnotationAttributes attributesForAll(AnnotatedTypeMetadata metadata, String annotationClassName) {
        return AnnotationAttributes.fromMap(metadata.getAnnotationAttributes(annotationClassName, false));
    }

    public static Boolean hasAnnotation(Class<?> clazz, Class<? extends Annotation> annotationType){
        return AnnotatedElementUtils.hasAnnotation(clazz, annotationType);
    }
}
