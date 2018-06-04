package com.wdcloud.framework.web.springmvc.validation.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.StringUtils;

import com.wdcloud.framework.web.springmvc.validation.NotNull;

/** 
 * Description: 校验必填项
 *
 * @author Administrator
 * @date 2015年9月29日
 * @version 1.0 
 */
public class NotNullImpl implements ConstraintValidator<NotNull, Object>{
	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		if(value!=null){
			value = value.toString();
		}
		if(StringUtils.isEmpty(value)||null==value){
			return false;
		}else{
			return true;
		}
	}

	@Override
	public void initialize(NotNull constraintAnnotation) {
	}
}
