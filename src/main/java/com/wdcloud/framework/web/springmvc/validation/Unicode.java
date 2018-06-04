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

import com.wdcloud.framework.web.springmvc.validation.impl.UnicodeImpl;

/**
 * @author CHENYB
 * @since 2016年4月16日
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UnicodeImpl.class)
public @interface Unicode {

	int minLength();

	int maxLength();

	String message() default "$validator.Unicode.message";

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

	String Validator() default "function(value,param){var len = value.trim().length; var reg = new RegExp('^(([\\\\u4e00-\\\\u9fa5]|[\\\\w\\\\s])*)?$'); return len >= param[0] && len <= param[1] && reg.test(value)}";

}
