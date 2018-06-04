package com.wdcloud.ptxtgl.userRole.entity;

import java.io.Serializable;

import com.wdcloud.ptxtgl.base.entity.BaseEntity;

public class UserRole extends BaseEntity implements Serializable{

	/**
	 * 用户-角色关系表
	 * @author bigd
	 */
	private static final long serialVersionUID = 9149233188326802861L;
	
	
	private String id;
	
	private String userid;
	
	private String roleid;
	
	private String rolecode;
	
	private String rolename;
	
	private String appkey;

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getRoleid() {
		return roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

	public String getRolecode() {
		return rolecode;
	}

	public void setRolecode(String rolecode) {
		this.rolecode = rolecode;
	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public String getAppkey() {
		return appkey;
	}

	public void setAppkey(String appkey) {
		this.appkey = appkey;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}	
}
