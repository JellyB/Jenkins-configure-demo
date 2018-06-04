package com.wdcloud.framework.web.springmvc.validation.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.wdcloud.common.util.CheckUtils;
import com.wdcloud.framework.web.springmvc.validation.LoginName;

/** 
 * 用户登录名校验实现类
 * @author bigd
 * @date 2016.04.15
 * @version 1.0 
 */
public class LoginNameImpl implements ConstraintValidator<LoginName, String> {
	@Override
	public void initialize(LoginName constraintAnnotation) {
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return CheckUtils.isNickName(value);
	}
}
