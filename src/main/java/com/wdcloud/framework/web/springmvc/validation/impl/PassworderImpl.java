/**
 * @author CHENYB
 * @(#)PasswordImpl.java 2016年4月9日
 * 
 * wdcloud 版权所有2014~2016。
 */
package com.wdcloud.framework.web.springmvc.validation.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.wdcloud.common.util.CheckUtils;
import com.wdcloud.framework.web.springmvc.validation.Passworder;

/**
 * @author CHENYB
 * @since 2016年4月9日
 */
public class PassworderImpl implements ConstraintValidator<Passworder, String> {

	/**
	 * @author CHENYB
	 * @see javax.validation.ConstraintValidator#initialize(java.lang.annotation.Annotation)
	 * @since 2016年4月9日 下午2:27:16
	 */
	@Override
	public void initialize(Passworder constraintAnnotation) {
	}

	/**
	 * @author CHENYB
	 * @see javax.validation.ConstraintValidator#isValid(java.lang.Object, javax.validation.ConstraintValidatorContext)
	 * @since 2016年4月9日 下午2:27:16
	 */
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return CheckUtils.isCheckedPassword(value);
	}

}
