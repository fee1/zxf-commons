package com.zxf.common.annotation;

import com.zxf.common.utils.AnnotationUtil;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.GenericTypeResolver;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.lang.annotation.Annotation;

/**
 * 无用，暂时保留
 *
 * 获取注解中的  type 属性中需要导入的类型
 *
 * @author zhuxiaofeng
 * @date 2021/12/15
 */
public abstract class TypeImportSelector<A extends Annotation> implements ImportSelector {

    /**
     * The default type attribute name.
     */
    public static final String DEFAULT_TYPE_ATTRIBUTE_NAME = "type";

    protected String getDefaultExpressionAttributeName(){
        return DEFAULT_TYPE_ATTRIBUTE_NAME;
    }

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        Class<?> annType = GenericTypeResolver.resolveTypeArgument(getClass(), TypeImportSelector.class);
        Assert.state(annType != null, "Unresolvable type argument for ExpressionImportSelector");

        AnnotationAttributes attributes = AnnotationUtil.attributesForAll(importingClassMetadata, annType);
        if (attributes == null) {
            throw new IllegalArgumentException(String.format(
                    "@%s is not present on importing class '%s' as expected",
                    annType.getSimpleName(), importingClassMetadata.getClassName()));
        }

        String typeStr = attributes.getString(DEFAULT_TYPE_ATTRIBUTE_NAME);
        String[] imports = selectImports(typeStr);
        if (imports == null) {
            throw new IllegalArgumentException("Unknown typeStr: " + typeStr);
        }
        return imports;
    }

    @Nullable
    protected abstract String[] selectImports(String expressionStr);

}
