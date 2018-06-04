/**
 * @author CHENYB
 * @(#)Unicode.java 2016年4月16日
 * 
 * wdcloud 版权所有2014~2016。
 */
package com.wdcloud.framework.web.springmvc.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.wdcloud.framework.web.springmvc.validation.impl.AsciiImpl;

/**
 * 指定长度的字符串，包括数字，字母，下划线，且不能以数字开头
 * @author CHENYB
 * @since 2016年4月16日
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AsciiImpl.class)
public @interface Ascii {

	int minLength();

	int maxLength();

	String message() default "$validator.Ascii.message";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	/**
	 * 
	 * Description:用于设置注解参数顺序及前端校验函数
	 *
	 * @return
	 * @update [日期YYYY-MM-DD] [更改人姓名][变更描述]
	 */
	String[] ParamArray() default { "minLength", "maxLength" };

	String Validator() default "function(value,param){var len = value.trim().length; var reg = new RegExp('^([a-zA-Z_]+\\\\w*)?$'); return len >= param[0] && len <= param[1] && reg.test(value)}";

}
