package com.wdcloud.ptxtgl.base.entity;

import java.io.Serializable;

public class AuthReuslt implements Serializable{

	/**
	 * @author bigd
	 * 用户鉴权返回信息
	 */
	private static final long serialVersionUID = 7071088659054394903L;
	
	public static int CODE_YES = 1;
	
	public static int CODE_NO = 0;
	
	private int code;
	
	private String message;
	
	private Object data;
	

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
