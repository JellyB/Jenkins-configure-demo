package com.wdcloud.framework.web.springmvc.validation.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.StringUtils;

import com.wdcloud.framework.web.springmvc.validation.Pattern;

/** 
 * Description: TODO[描述该类概要功能介绍]
 *
 * @author Administrator
 * @date 2015年10月8日
 * @version 1.0 
 */
public class PatternImpl implements ConstraintValidator<Pattern, String> {
	private String val;
	@Override
	public void initialize(Pattern constraintAnnotation) {
		
		val = constraintAnnotation.value();
		
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		boolean flag = false;
		if(!StringUtils.isEmpty(value) && null != value){
			if(value.matches(val)){
				flag = true;
			}
		}
		
		return flag;
		
	}

}
