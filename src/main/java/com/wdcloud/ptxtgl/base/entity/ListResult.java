package com.wdcloud.ptxtgl.base.entity;

import java.io.Serializable;
import java.util.List;

public class ListResult<T> implements Serializable{	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1323254042337819001L;
	/**成功**/
	private static final boolean SUCCESS = true;
	
	/**失败**/
	private static final boolean FAILURE = false;

	private boolean result;
	
	private String msg;
	
	private List<T> data;
	

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}
}
