package com.wdcloud.common.util;

import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class CheckUtils {

	/**
	 * validate loginname is email
	 * @param loginName
	 */
	public static boolean isEmail(String loginName) {
		if (StringUtils.isEmpty(loginName)) {
			return false;
		}
		if(loginName.length() > 50 || loginName.length() < 5) {
			return false;
		}
		String email = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
		return loginName.matches(email);
	}

	/**
	 * validate loginname is cellphone
	 * @param loginName
	 */
	public static boolean isCellphone(String loginName) {
		if (StringUtils.isEmpty(loginName)) {
			return false;
		}
		String phone = "^1\\d{10}$";
		return Pattern.matches(phone, loginName);
	}

	/**
	 * validate loginname is loginId
	 * @param loginName
	 */
	public static boolean isLoginId(String loginName) {
		if (StringUtils.isEmpty(loginName)) {
			return false;
		}
		String id = "^[1-9]\\d{9}$";
		return Pattern.matches(id, loginName);
	}

	/**
	 * validate loginname is nickname
	 * @param loginName
	 */
	public static boolean isNickName(String loginName) {
		if (StringUtils.isEmpty(loginName)) {
			return false;
		}
		//昵称只能输入2-20个以字母或汉字开头，可带数字的字符串
		String nickname = "^([a-zA-Z]|[\u4e00-\u9fa5]){1}([a-zA-Z0-9]|[\u4e00-\u9fa5]){1,19}$";
		return Pattern.matches(nickname, loginName);
	}

	public static boolean isCheckedPassword(String password) {
		if (StringUtils.isEmpty(password)) {
			return false;
		}
		// 密码应该由6-20位的字母、数字、下划线组成
		String patten = "^\\w{6,20}$";
		return Pattern.matches(patten, password);
	}
	

}
