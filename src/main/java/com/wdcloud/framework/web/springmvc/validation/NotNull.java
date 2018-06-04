package com.wdcloud.framework.web.springmvc.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.wdcloud.framework.web.springmvc.validation.impl.NotNullImpl;

/** 
 * Description: 必填项校验
 *
 * 
 * @date 2015年9月29日
 * @version 1.0 
 */
@Target({ ElementType.METHOD,ElementType.FIELD,ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotNullImpl.class)
public @interface NotNull {
	
	String message()default"$validator.NotNull.message";
	
	Class<?>[] groups() default {};  
	  
    Class<? extends Payload>[] payload() default {};
    
    String Validator() default "function(value){return value!=''&&value!=null}";
}
