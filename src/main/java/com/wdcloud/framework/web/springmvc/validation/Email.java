package com.wdcloud.framework.web.springmvc.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.wdcloud.framework.web.springmvc.validation.impl.EmailImpl;

/** 
 * Description: 被注释的元素必须是电子邮箱地址
 *
 * @author Administrator
 * @date 2015年9月30日
 * @version 1.0 
 */
@Target( {ElementType.FIELD})  
@Retention(RetentionPolicy.RUNTIME)  
@Constraint(validatedBy = EmailImpl.class)
public @interface Email {
	String message() default "$validator.Email.message";  
	  
    Class<?>[] groups() default {};  
  
    Class<? extends Payload>[] payload() default {};
}
