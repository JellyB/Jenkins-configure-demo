package com.wdcloud.ptxtgl.base.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;

public class BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3901511791492989281L;

	private String descn;

	private String delflag;

	private String operatercode;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JSONField(format = "yyyy-MM-dd")
	private Date operatetime;

	private String creatercode;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JSONField(format = "yyyy-MM-dd")
	private Date creatertime;

	private String versions;

	public String getDescn() {
		return descn;
	}

	public void setDescn(String descn) {
		this.descn = descn;
	}

	public String getDelflag() {
		return delflag;
	}

	public void setDelflag(String delflag) {
		this.delflag = delflag;
	}

	public String getOperatercode() {
		return operatercode;
	}

	public void setOperatercode(String operatercode) {
		this.operatercode = operatercode;
	}

	public String getCreatercode() {
		return creatercode;
	}

	public void setCreatercode(String creatercode) {
		this.creatercode = creatercode;
	}

	public Date getCreatertime() {
		return creatertime;
	}

	public void setCreatertime(Date creatertime) {
		this.creatertime = creatertime;
	}

	public String getVersions() {
		return versions;
	}

	public void setVersions(String versions) {
		this.versions = versions;
	}

	public Date getOperatetime() {
		return operatetime;
	}

	public void setOperatetime(Date operatetime) {
		this.operatetime = operatetime;
	}

}
