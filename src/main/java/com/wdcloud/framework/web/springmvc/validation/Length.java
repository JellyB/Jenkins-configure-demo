package com.wdcloud.framework.web.springmvc.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.wdcloud.framework.web.springmvc.validation.impl.LengthImpl;

/** 
 * Description: 字符串长度校验
 *
 * @author Administrator
 * @date 2015年9月29日
 * @version 1.0 
 */
@Target( {ElementType.FIELD})  
@Retention(RetentionPolicy.RUNTIME)  
@Constraint(validatedBy = LengthImpl.class)
public @interface Length {
	int minLength();
	int maxLength();
	//framework.web.springmvc.validation.TestValidation.message
    String message() default "$validator.Length.message";  
  
    Class<?>[] groups() default {};  
  
    Class<? extends Payload>[] payload() default {};
    /**
     * 
     * Description:用于设置注解参数顺序及前端校验函数
     *
     * @return
     * @update [日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    String[] ParamArray() default {"minLength","maxLength"};
    String Validator() default "function(value,param){var len = value.trim().length;param[2] = (param[1] / 2).toString().split('.')[0];return len >= param[0] && len <= param[1]}";
}
