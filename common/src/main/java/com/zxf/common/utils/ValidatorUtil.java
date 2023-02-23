package com.zxf.common.utils;


import com.zxf.common.model.ValidateResult;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * 验证
 *
 * @author zhuxiaofeng
 * @date 2023/2/23
 */
public class ValidatorUtil {

    public static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    /**
     * 对象字段信息校验
     *
     * @param data 数据
     * @param <T>
     * @return
     */
    public static <T> List<ValidateResult> validate(T data){
        Set<ConstraintViolation<T>> validate = VALIDATOR.validate(data);
        if (validate.size() == 0){
            return Collections.EMPTY_LIST;
        }
        List<ValidateResult> failValidateMessageList = new ArrayList<>();
        for (ConstraintViolation<T> tConstraintViolation : validate) {
            ValidateResult validateResult = new ValidateResult();
            validateResult.setFieldName(tConstraintViolation.getPropertyPath().toString());
            validateResult.setMessage(tConstraintViolation.getMessage());
            failValidateMessageList.add(validateResult);
        }
        return failValidateMessageList;
    }

    /**
     * 简单校验只会返回校验失败的信息
     * @param data
     * @param <T>
     * @return
     */
    public static <T> String simpleValidate(T data){
        Set<ConstraintViolation<T>> validate = VALIDATOR.validate(data);
        StringBuilder message = new StringBuilder();

        for (ConstraintViolation<T> tConstraintViolation : validate) {
            String str =
                    String.format("%s:%s;", tConstraintViolation.getPropertyPath().toString(), tConstraintViolation.getMessage());
            message.append(message);
        }
        return message.toString();
    }

}
