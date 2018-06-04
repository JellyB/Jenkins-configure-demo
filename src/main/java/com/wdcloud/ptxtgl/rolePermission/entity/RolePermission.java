package com.wdcloud.ptxtgl.rolePermission.entity;

import java.io.Serializable;

import com.wdcloud.ptxtgl.base.entity.BaseEntity;

public class RolePermission extends BaseEntity implements Serializable{

	/**
	 * 角色权限关系表
	 */
	private static final long serialVersionUID = 5504857027395081691L;
	
	private String id;
	
	private String roleid;
	
	private String permissionid;
	
	private String permissionname;
	
	private String permissioncode;
	
	private String permissiontype;
	
	private String appkey;

	public String getRoleid() {
		return roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

	public String getPermissionid() {
		return permissionid;
	}

	public void setPermissionid(String permissionid) {
		this.permissionid = permissionid;
	}

	public String getPermissionname() {
		return permissionname;
	}

	public void setPermissionname(String permissionname) {
		this.permissionname = permissionname;
	}

	public String getPermissioncode() {
		return permissioncode;
	}

	public void setPermissioncode(String permissioncode) {
		this.permissioncode = permissioncode;
	}

	public String getPermissiontype() {
		return permissiontype;
	}

	public void setPermissiontype(String permissiontype) {
		this.permissiontype = permissiontype;
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
