package com.imooc.validator;

import com.imooc.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * A: 对Annotation的一个验证的实现
 * T : 这个Annotation声明的字段的类型
 * 这里不需要加 @Component,implements ConstraintValidator Spring会自动加入
 */
public class MyConstraintValidator implements ConstraintValidator<MyConstraint, Object> {

    @Autowired
    HelloService helloService;

    @Override
    public void initialize(MyConstraint constraintAnnotation) {
        //初始化验证器
        System.out.println("myConstraint validator init");
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        //验证的逻辑
        System.out.println("valid " + value + " invoke helloService.greeting "
                + helloService.greeting("leo"));
        // return true;
        return false;
    }
}
