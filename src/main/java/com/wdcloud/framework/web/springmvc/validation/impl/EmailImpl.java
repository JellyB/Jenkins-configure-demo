package com.wdcloud.framework.web.springmvc.validation.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.StringUtils;

import com.wdcloud.framework.core.util.PropertyXmlMgr;
import com.wdcloud.framework.web.springmvc.validation.Email;
import com.wdcloud.framework.web.springmvc.validation.ValidatorInterface;

/** 
 * Description: TODO[描述该类概要功能介绍]
 *
 * @author Administrator
 * @date 2015年9月30日
 * @version 1.0 
 */
public class EmailImpl implements ConstraintValidator<Email, String>,ValidatorInterface {

	@Override
	public void initialize(Email constraintAnnotation) {
		
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		boolean flag = false;
		if(!StringUtils.isEmpty(value) && null != value){
			String reg = PropertyXmlMgr.getString("CONF_FRAMEWORK", "reg.email");
			if(value.matches(reg)){
				flag = true;
			}
		}
		return flag;
	}

	@Override
	public String[] getParamArray() {
		
		// TODO Auto-generated method stub
		return null;
		
	}

	@Override
	public String getValidator() {
		String reg = PropertyXmlMgr.getString("CONF_FRAMEWORK", "reg.email");
		return "function(value){return value.test('"+reg+"')}";
		
	}

}
