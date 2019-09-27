package com.imooc.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// Target表示此注解可用在哪些元素上
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MyConstraintValidator.class)//这里引用验证逻辑类
public @interface MyConstraint {
    // 下面这3个属性必须有
    String message() default "1111";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
