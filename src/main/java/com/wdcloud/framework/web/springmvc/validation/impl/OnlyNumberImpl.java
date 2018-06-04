package com.wdcloud.framework.web.springmvc.validation.impl;

import com.wdcloud.framework.core.util.PropertyXmlMgr;
import com.wdcloud.framework.web.springmvc.validation.OnlyNumber;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/** 
 * Description: n位正整数校验
 *
 * @author Administrator
 * @date 2015年9月29日
 * @version 1.0 
 */
public class OnlyNumberImpl implements ConstraintValidator<OnlyNumber, Object> {
	private int length;

	@Override
	public void initialize(OnlyNumber constraintAnnotation) {
		length = constraintAnnotation.length();
	}

	@Override
	public boolean isValid(Object val, ConstraintValidatorContext context) {
		String value ="";
		if(val instanceof String || val instanceof  Integer || val instanceof  Long)
			value = String.valueOf(val);
		boolean flag = false;
		String regStr = PropertyXmlMgr.getString("CONF_FRAMEWORK", "reg.onlyNumber");
		if(!StringUtils.isEmpty(value) && "null" != value){
			if(value.matches(regStr) && value.length() <= length){
				flag = true;
			}
		}else{
			flag = true;
		}
		return flag;
	}

}
