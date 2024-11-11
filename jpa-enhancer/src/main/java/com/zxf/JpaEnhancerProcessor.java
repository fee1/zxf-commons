package com.zxf;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedOptions;
import javax.lang.model.element.TypeElement;
import java.util.Set;

/**
 * @author zhuxiaofeng
 * @date 2024/11/4
 */
@SupportedAnnotationTypes("com.zxf.JpaEnhancer") // 配置注解
@SupportedOptions({}) // 配置编译参数
public class JpaEnhancerProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        System.out.println("JpaEnhancerProcessor start");
        for (TypeElement annotation : annotations) {

        }
        System.out.println("JpaEnhancerProcessor end");
        return true;
    }
}
