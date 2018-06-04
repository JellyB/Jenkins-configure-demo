/**
 * @author CHENYB
 * @(#)MaxLengthImpl.java 2016年4月18日
 * 
 * wdcloud 版权所有2014~2016。
 */
package com.wdcloud.framework.web.springmvc.validation.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.StringUtils;

import com.wdcloud.framework.web.springmvc.validation.MaxLength;

/**
 * @author CHENYB
 * @since 2016年4月18日
 */
public class MaxLengthImpl implements ConstraintValidator<MaxLength, String> {

	int value;

	/**
	 * @author CHENYB
	 * @see javax.validation.ConstraintValidator#initialize(java.lang.annotation.Annotation)
	 * @since 2016年4月18日 下午3:04:21
	 */
	@Override
	public void initialize(MaxLength constraint) {
		this.value = constraint.value();
	}

	/**
	 * @author CHENYB
	 * @see javax.validation.ConstraintValidator#isValid(java.lang.Object, javax.validation.ConstraintValidatorContext)
	 * @since 2016年4月18日 下午3:04:26
	 */
	@Override
	public boolean isValid(String obj, ConstraintValidatorContext context) {
		if (StringUtils.isEmpty(obj)) {
			return true;
		}
		return obj.length() <= value;
	}

}
