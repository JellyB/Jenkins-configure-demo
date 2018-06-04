package com.wdcloud.common.util;

import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class AdminLoginUtils {
	
	/**
	 * 
	 * admin-login判断loginid工具类
	 * @author bigd
	 * validate loginname is loginId
	 * @param loginName
	 */
	public static boolean isLoginId(String loginName) {
		if (StringUtils.isEmpty(loginName)) {
			return false;
		}
		String id = "^9\\d{5}$";
		return Pattern.matches(id, loginName);
	}	
}
