package com.wdcloud.framework.web.springmvc.validation;

import com.wdcloud.framework.web.springmvc.validation.impl.OnlyNumberImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** 
 * Description:  n位正整数校验
 *
 * 
 * @date 2015年9月29日
 * @version 1.0 
 */
@Target( {ElementType.FIELD})  
@Retention(RetentionPolicy.RUNTIME)  
@Constraint(validatedBy = OnlyNumberImpl.class) 
public @interface OnlyNumber {
	int length();
	String message() default "$validator.OnlyNum.message";  
	  
    Class<?>[] groups() default {};  
  
    Class<? extends Payload>[] payload() default {};
    
    /**
     * 
     * Description:用于设置注解参数顺序及前端校验函数
     *
     * @return
     * @update [日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    String[] ParamArray() default {"length"};
    String Validator() default "function(value,param){var reg = new RegExp('^(^[0-9]*[1-9][0-9]*$)*$');var len = value.length;if(reg.test(value) && len <= param[0]){return true;}else{return false;}}";
}
