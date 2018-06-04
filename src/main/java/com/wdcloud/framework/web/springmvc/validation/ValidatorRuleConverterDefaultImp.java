package com.wdcloud.framework.web.springmvc.validation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.wdcloud.framework.core.util.I18NMessageReader;
import com.wdcloud.framework.web.model.ValidatorModel;
import com.wdcloud.framework.web.model.ValidatorModelItem;
import com.wdcloud.framework.web.model.ValidatorModelItemRule;
import com.wdcloud.framework.web.validation.service.ValidatorRuleConverter;

/** 
 * Description: 校验转换规则默认实现
 *
 * @date 2015年5月14日
 * @version 1.0 
 */
public class ValidatorRuleConverterDefaultImp implements ValidatorRuleConverter {
	@Override
	public String converte(ValidatorModel validatorModel) {
		//前端校验需要格式字符串
		String validateStr = "var checkDetailArray = ${checkDetailArray};var wdvalidateBoxrules = {required: false,validType: null,missingMessage: '"+I18NMessageReader.getMessage("validator.NotNull.message")+"',invalidMessage: null,rules : ${rules}};";
		List<String> checkDetailArray = new ArrayList<String>();
		Map<String,Map<String,String>> ruleMap = new HashMap<String,Map<String,String>>();
		
		for(ValidatorModelItem validatorModelItem:validatorModel.getItemList()){
			List<ValidatorModelItemRule> rules = validatorModelItem.getRules();
			if(rules.size()>0){
				StringBuilder tempBuilder = new StringBuilder(validatorModelItem.getPropertyName());
				tempBuilder.append(":");
				int i=0;
				for(ValidatorModelItemRule validatorModelItemRule:rules){
					String ruleName = validatorModelItemRule.getRuleName();
					Map<String, Object> paramMap = validatorModelItemRule.getParamMap();
					
					if(!ruleMap.containsKey(ruleName) && !"NotNull".equals(ruleName)){
						Map<String,String> ruleUnit = new HashMap<String,String>();
						ruleUnit.put("message", validatorModelItemRule.getArrayMessage());
						if(paramMap!=null&&paramMap.containsKey("Validator")){
							ruleUnit.put("validator", (String)paramMap.get("Validator"));
						}
//						if("NotNull".equals(ruleName)||"notNull".equals(ruleName)){
//							ruleName = "required";
//						}
						ruleMap.put(ruleName, ruleUnit);
					}
					
					if(i++!=0){
						tempBuilder.append(";");
					}
					
					tempBuilder.append("validType=");
					tempBuilder.append(ruleName);
					
					if(paramMap!=null&&paramMap.containsKey("ParamArray")){
						List<Object> paramList = new ArrayList<Object>();
						String[] paramArray = (String[])paramMap.get("ParamArray");
						for(String paramName:paramArray){
							paramList.add(paramMap.get(paramName));
						}
						tempBuilder.append(JSONArray.fromObject(paramList).toString());
					}
				}
				
				String tempStr = tempBuilder.toString();
				tempStr = tempStr.replaceAll("validType=notNull", "required");
				tempStr = tempStr.replaceAll("validType=NotNull", "required");
				checkDetailArray.add(tempStr);
			}
		}
		String str = JSONObject.fromObject(ruleMap).toString();
		validateStr = validateStr.replace("${checkDetailArray}", JSONArray.fromObject(checkDetailArray).toString());
		validateStr = validateStr.replace("${rules}", str);
		return validateStr;
	}
}