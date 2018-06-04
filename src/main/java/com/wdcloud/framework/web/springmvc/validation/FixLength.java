package com.wdcloud.framework.web.springmvc.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.wdcloud.framework.web.springmvc.validation.impl.FixLengthImpl;

@Target( {ElementType.FIELD})  
@Retention(RetentionPolicy.RUNTIME)  
@Constraint(validatedBy = FixLengthImpl.class)  
public @interface FixLength {  
    int length(); 
    int t();
    String message() default "$message.key1";  
  
    Class<?>[] groups() default {};  
  
    Class<? extends Payload>[] payload() default {};
    
    /**
     * 
     * Description:用于设置注解参数顺序及前端校验函数
     *
     * @return
     * @update [日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    String[] ParamArray() default{"length","t"};
    String Validator() default "function(value, param) {var len = $.strLen(value);return len >= 0 && len <= param[0];}";
} 
