package com.wdcloud.framework.web.springmvc.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.wdcloud.framework.web.springmvc.validation.impl.PatternImpl;

/** 
 * Description: 被注释的元素必须符合指定的正则表达式
 *
 * @author Administrator
 * @date 2015年10月8日
 * @version 1.0 
 */
@Target({ ElementType.METHOD,ElementType.FIELD,ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PatternImpl.class)
public @interface Pattern {
	String value();
	
	String message()default"$validator.Pattern.message";
	
	Class<?>[] groups() default {};  
	  
    Class<? extends Payload>[] payload() default {};
    
  /**
   * 
   * Description:用于设置注解参数顺序及前端校验函数
   *
   * @return
   * @update [日期YYYY-MM-DD] [更改人姓名][变更描述]
   */
  String[] ParamArray() default {"value"};
  String Validator() default "function(value,param){if(value.test(param[0])){return true;}else{return false;}}";

}
