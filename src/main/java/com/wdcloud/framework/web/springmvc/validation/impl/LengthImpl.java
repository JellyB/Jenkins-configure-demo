package com.wdcloud.framework.web.springmvc.validation.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.StringUtils;

import com.wdcloud.framework.core.util.PropertyXmlMgr;
import com.wdcloud.framework.web.springmvc.validation.Length;

/** 
 * Description: 字符串长度校验
 *
 * @author Administrator
 * @date 2015年9月29日
 * @version 1.0 
 */
public class LengthImpl implements ConstraintValidator<Length, String>{
	private int minlength;
	private int maxlength;
	@Override
	public void initialize(Length constraintAnnotation) {
		
		minlength = constraintAnnotation.minLength();
		maxlength = constraintAnnotation.maxLength();
		
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		
		if(value != null && !StringUtils.isEmpty(value.toString())){
			
			String regStr = PropertyXmlMgr.getString("CONF_FRAMEWORK", "reg.length");
			int strLen = value.replaceAll(regStr, "aa").length();
			if(strLen < minlength || strLen > maxlength){
				return false;
			}else{
				return true;
			}
		}else{
			return true;
		}
		
	}

}
