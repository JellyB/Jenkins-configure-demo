/**
 * @author CHENYB
 * @(#)AsciiImpl.java 2016年4月16日
 * 
 * wdcloud 版权所有2014~2016。
 */
package com.wdcloud.framework.web.springmvc.validation.impl;

import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.StringUtils;

import com.wdcloud.framework.web.springmvc.validation.Ascii;

/**
 * @author CHENYB
 * @since 2016年4月16日
 */
public class AsciiImpl implements ConstraintValidator<Ascii, String> {

	private int minlength;
	private int maxlength;

	/**
	 * @author CHENYB
	 * @see javax.validation.ConstraintValidator#initialize(java.lang.annotation.Annotation)
	 * @since 2016年4月16日 下午4:51:48
	 */
	@Override
	public void initialize(Ascii constraint) {
		this.minlength = constraint.minLength();
		this.maxlength = constraint.maxLength();
	}

	/**
	 * @author CHENYB
	 * @see javax.validation.ConstraintValidator#isValid(java.lang.Object, javax.validation.ConstraintValidatorContext)
	 * @since 2016年4月16日 下午4:51:48
	 */
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (StringUtils.isEmpty(value)) {
			return true;
		}
		if (value.length() < minlength || value.length() > maxlength) {
			return false;
		}
		return Pattern.matches("^([a-zA-Z_]+\\w*)?$", value);
	}

}
