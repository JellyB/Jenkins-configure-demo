/**
 * @author CHENYB
 * @(#)UnicodeImpl.java 2016年4月16日
 * 
 * wdcloud 版权所有2014~2016。
 */
package com.wdcloud.framework.web.springmvc.validation.impl;

import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.StringUtils;

import com.wdcloud.framework.web.springmvc.validation.Unicode;

/**
 * @author CHENYB
 * @since 2016年4月16日
 */
public class UnicodeImpl implements ConstraintValidator<Unicode, String> {

	private int minlength;
	private int maxlength;

	/**
	 * @author CHENYB
	 * @see javax.validation.ConstraintValidator#initialize(java.lang.annotation.Annotation)
	 * @since 2016年4月16日 下午5:27:25
	 */
	@Override
	public void initialize(Unicode constraint) {
		this.minlength = constraint.minLength();
		this.maxlength = constraint.maxLength();
	}

	/**
	 * @author CHENYB
	 * @see javax.validation.ConstraintValidator#isValid(java.lang.Object, javax.validation.ConstraintValidatorContext)
	 * @since 2016年4月16日 下午5:27:25
	 */
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (StringUtils.isEmpty(value)) {
			return true;
		}
		if (value.length() < minlength || value.length() > maxlength) {
			return false;
		}
		return Pattern.matches("^(([\\u4e00-\\u9fa5]|[\\w\\s])*)?$", value);
	}

}
