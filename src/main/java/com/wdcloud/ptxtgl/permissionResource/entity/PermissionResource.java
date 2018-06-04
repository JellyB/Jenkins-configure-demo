/**
 * @author CHENYB
 * @(#)PermissionResource.java 2016年1月20日
 * 
 * wdcloud 版权所有2014~2016。
 */
package com.wdcloud.ptxtgl.permissionResource.entity;

import com.wdcloud.ptxtgl.base.entity.BaseEntity;

/**
 * @author CHENYB
 * @since 2016年1月20日
 */
public class PermissionResource extends BaseEntity {

	private static final long serialVersionUID = 8901023565469380402L;

	private String permissionid;
	private String id;
	private String resourcename;
	private String resourcecode;
	private String appkey;

	public String getPermissionid() {
		return permissionid;
	}

	public void setPermissionid(String permissionid) {
		this.permissionid = permissionid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getResourcename() {
		return resourcename;
	}

	public void setResourcename(String resourcename) {
		this.resourcename = resourcename;
	}

	public String getResourcecode() {
		return resourcecode;
	}

	public void setResourcecode(String resourcecode) {
		this.resourcecode = resourcecode;
	}

	public String getAppkey() {
		return appkey;
	}

	public void setAppkey(String appkey) {
		this.appkey = appkey;
	}

}
