package com.wdcloud.framework.web.springmvc.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.wdcloud.framework.web.springmvc.validation.impl.LoginNameImpl;

/**
 * 密码专用校验器
 * @author CHENYB
 * @since 2016年4月9日
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = LoginNameImpl.class)
public @interface LoginName {

	/**
	 * 校验提示信息
	 * @author bigd
	 * @return
	 * @since 2016年4月9日 下午3:36:43
	 */
	String message() default "$validator.LoginName.message";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	/**
	 * 用户登录名校验Js
	 * @author bigd
	 * @since 2016年4月15日 下午3:34:14
	 */
	String Validator() default "function(value){var reg = new RegExp('^([a-zA-Z]|[\u4e00-\u9fa5]){1}([a-zA-Z0-9]|[\u4e00-\u9fa5]){1,19}$');return reg.test(value);}";

}
