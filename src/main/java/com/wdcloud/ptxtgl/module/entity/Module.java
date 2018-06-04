/**
 * @author CHENYB
 * @(#)Module.java 2016年1月19日
 * 
 * wdcloud 版权所有2014~2016。
 */
package com.wdcloud.ptxtgl.module.entity;

import com.wdcloud.common.util.Config;
import com.wdcloud.framework.web.springmvc.annotation.Lable;
import com.wdcloud.framework.web.springmvc.validation.*;
import com.wdcloud.ptxtgl.base.entity.BaseEntity;

/**
 * @author CHENYB
 * @since 2016年1月19日
 */
public class Module extends BaseEntity {

	private static final long serialVersionUID = -5545516884003661764L;

	private String id; // bigint(20) NOT NULL COMMENT '流水号',
	private String parentid; // bigint(20) DEFAULT NULL COMMENT '父流水号：PARENTID为空、ID与PARENTID相等这两种情况为根。',
	@NotNull
	@Unicode(minLength=Config.NAME_MIN_LENGTH, maxLength=Config.NAME_MAX_LENGTH)
	@Lable("$ptxtgl.module.modulename")
	private String modulename; // varchar(80) DEFAULT NULL COMMENT '模块名称',
	@NotNull
	@Ascii(minLength=Config.CODE_MIN_LENGTH, maxLength=Config.CODE_MAX_LENGTH)
	@Lable("$ptxtgl.module.modulecode")
	private String modulecode; // varchar(80) DEFAULT NULL COMMENT '模块代码',
	private Integer modulelevel; // int(11) DEFAULT NULL COMMENT '模块级别',
	private String menuid; // varchar(32) DEFAULT NULL COMMENT '资源流水号',
	
	private String resourcename; // 附加列
	private String resourcecode; // 附加列

	@NotNull
	@Lable("$ptxtgl.module.appkey")
	private String appkey; // bigint(20) DEFAULT NULL COMMENT '应用KEY',

	@Lable("$ptxtgl.module.displayorder")
	@OnlyNumber(length = 3)
	private String displayorder; // int(11) DEFAULT NULL COMMENT '显示顺序',

	@Lable("$ptxtgl.module.descn")
	@MaxLength(Config.DESCN_MAX_LENGTH)
	private String descn;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	public String getModulename() {
		return modulename;
	}

	public void setModulename(String modulename) {
		this.modulename = modulename;
	}

	public String getModulecode() {
		return modulecode;
	}

	public void setModulecode(String modulecode) {
		this.modulecode = modulecode;
	}

	public Integer getModulelevel() {
		return modulelevel;
	}

	public void setModulelevel(Integer modulelevel) {
		this.modulelevel = modulelevel;
	}

	public String getMenuid() {
		return menuid;
	}

	public void setMenuid(String menuid) {
		this.menuid = menuid;
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

	public String getDisplayorder() {
		return displayorder;
	}

	public void setDisplayorder(String displayorder) {
		this.displayorder = displayorder;
	}

	public String getDescn() {
		return descn;
	}

	public void setDescn(String descn) {
		this.descn = descn;
	}

}
