package com.zxf.common.utils.sql.u;

import com.google.common.base.CaseFormat;
import org.apache.commons.lang3.AnnotationUtils;


/**
 * @author zhuxiaofeng
 * @date 2024/9/6
 */
public class EntityUtil {

    public static String getTableName(Class<?> entityClass){
        String tableName = null;
//        if (AnnotationUtils.isValidAnnotationMemberType(Table.class)) {
//            Table table = entityClass.getAnnotation(Table.class);
//            if (table != null && !table.name().isEmpty()) {
//                tableName = table.name();
//            }else {
//                throw new RuntimeException("实体对象的 @Table 没有正确使用");
//            }
//        }else {
            tableName = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, entityClass.getSimpleName());
//        }
        return tableName;
    }

}
