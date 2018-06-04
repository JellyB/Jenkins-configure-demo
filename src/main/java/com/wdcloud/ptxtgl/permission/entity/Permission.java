/**
 * @author CHENYB
 * @(#)Permission.java 2016年1月19日
 * 
 * wdcloud 版权所有2014~2016。
 */
package com.wdcloud.ptxtgl.permission.entity;

import com.wdcloud.common.util.Config;
import com.wdcloud.framework.web.springmvc.annotation.Lable;
import com.wdcloud.framework.web.springmvc.validation.Ascii;
import com.wdcloud.framework.web.springmvc.validation.MaxLength;
import com.wdcloud.framework.web.springmvc.validation.NotNull;
import com.wdcloud.framework.web.springmvc.validation.Unicode;
import com.wdcloud.ptxtgl.base.entity.BaseEntity;

/**
 * @author CHENYB
 * @since 2016年1月19日
 */
public class Permission extends BaseEntity {

	private static final long serialVersionUID = -3556614585207217681L;

	private String id; // bigint(20) NOT NULL COMMENT '流水号',
	@NotNull
	@Unicode(minLength=Config.NAME_MIN_LENGTH, maxLength=Config.NAME_MAX_LENGTH)
	@Lable("$ptxtgl.permission.permissionname")
	private String permissionname; // varchar(80) NOT NULL COMMENT '权限名称',
	@NotNull
	@Ascii(minLength=Config.CODE_MIN_LENGTH, maxLength=Config.CODE_MAX_LENGTH)
	@Lable("$ptxtgl.permission.permissioncode")
	private String permissioncode; // varchar(80) NOT NULL COMMENT '权限代码',
	private String permissiontype; // varchar(2) DEFAULT NULL COMMENT '权限类型（0：系统权限、1：应用权限、2：普通权限）。CODECLASS=PERMISSIONTYPE',
	@NotNull
	@Lable("$ptxtgl.permission.appkey")
	private String appkey; // bigint(20) DEFAULT NULL COMMENT '应用KEY',

	@Lable("$ptxtgl.permission.descn")
	@MaxLength(Config.DESCN_MAX_LENGTH)
	private String descn;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getDescn() {
		return descn;
	}

	public void setDescn(String descn) {
		this.descn = descn;
	}

}
