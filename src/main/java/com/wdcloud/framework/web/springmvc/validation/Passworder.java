package com.wdcloud.framework.web.springmvc.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.wdcloud.framework.web.springmvc.validation.impl.PassworderImpl;

/**
 * 密码专用校验器
 * @author CHENYB
 * @since 2016年4月9日
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PassworderImpl.class)
public @interface Passworder {

	/**
	 * 校验提示信息
	 * @author CHENYB
	 * @return
	 * @since 2016年4月9日 下午3:36:43
	 */
	String message() default "$validator.Passworder.message";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	/**
	 * 校验器
	 * @author CHENYB
	 * @return
	 * @since 2016年4月9日 下午3:34:14
	 */
	String Validator() default "function(value){var reg = new RegExp('^\\\\w{6,20}$');return reg.test(value);}";

}
