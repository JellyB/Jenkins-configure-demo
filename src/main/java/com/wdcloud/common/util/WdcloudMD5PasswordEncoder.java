package com.wdcloud.common.util;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

public class WdcloudMD5PasswordEncoder extends Md5PasswordEncoder{

	private String salt;
	
	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String encodePassword(String rawPass) {
		return super.encodePassword(rawPass, salt);
	}
}
