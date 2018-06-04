package com.wdcloud.framework.web.springmvc.validation.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.StringUtils;

import com.wdcloud.framework.web.springmvc.validation.FixLength;

public class FixLengthImpl implements ConstraintValidator<FixLength, String> {  
    private int length;  
    @Override  
    public boolean isValid(String validStr,  
            ConstraintValidatorContext constraintContext) {  
        if (!StringUtils.isEmpty(validStr)&&validStr.length() != length) {  
            return false;  
        } else {  
            return true;  
        }  
    }  
  
    @Override  
    public void initialize(FixLength fixLen) {  
        this.length = fixLen.length();  
    }  
}  