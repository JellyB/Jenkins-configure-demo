package com.wdcloud.framework.web.springmvc.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.wdcloud.framework.web.springmvc.validation.impl.MaxLengthImpl;

/**
 * 最大长度校验器
 * @author CHENYB
 * @since 2016年4月18日
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MaxLengthImpl.class)
public @interface MaxLength {

	int value();
	
	String message() default "$validator.MaxLength.message";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	/**
	 * 校验器
	 * @author CHENYB
	 * @return
	 * @since 2016年4月18日 下午3:07:29
	 */
    String[] ParamArray() default {"value"};
	String Validator() default "function(value,param){var len = value.trim().length; return len <= param[0];}";

}
