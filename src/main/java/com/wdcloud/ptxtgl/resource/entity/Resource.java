/**
 * @author CHENYB
 * @(#)Resource.java 2016年1月19日
 * 
 * wdcloud 版权所有2014~2016。
 */
package com.wdcloud.ptxtgl.resource.entity;

import com.wdcloud.common.util.Config;
import com.wdcloud.framework.web.springmvc.annotation.Lable;
import com.wdcloud.framework.web.springmvc.validation.Ascii;
import com.wdcloud.framework.web.springmvc.validation.Length;
import com.wdcloud.framework.web.springmvc.validation.MaxLength;
import com.wdcloud.framework.web.springmvc.validation.NotNull;
import com.wdcloud.framework.web.springmvc.validation.Unicode;
import com.wdcloud.ptxtgl.base.entity.BaseEntity;

/**
 * @author CHENYB
 * @since 2016年1月19日
 */
public class Resource extends BaseEntity {

	private static final long serialVersionUID = -5743910640607854908L;

	private String id; // bigint(20) NOT NULL COMMENT '流水号',
	@NotNull
	@Unicode(minLength=Config.NAME_MIN_LENGTH, maxLength=Config.NAME_MAX_LENGTH)
	@Lable("$ptxtgl.resource.resourcename")
	private String resourcename; // varchar(80) NOT NULL COMMENT '资源名称',
	@NotNull
	@Ascii(minLength=Config.CODE_MIN_LENGTH, maxLength=Config.CODE_MAX_LENGTH)
	@Lable("$ptxtgl.resource.resourcecode")
	private String resourcecode; // varchar(80) NOT NULL COMMENT '资源代码：将同一个功能对应的资源归类，包括菜单、按钮、功能，这些拥有相同的资源代码。类似一个小模块的模块代码。',
	@Length(minLength=1,maxLength=2)
	@Lable("$ptxtgl.resource.resourcetype")
	private String resourcetype; // varchar(2) NOT NULL COMMENT '资源类别：1（菜单）、2（按钮）、3（标签页）、4（方法）。CODECLASS=RESOURCETYPE',
	private String classname; // varchar(80) DEFAULT NULL COMMENT '类名',
	private String methodname; // varchar(80) DEFAULT NULL COMMENT '方法名',
	private String resourceurl; // varchar(400) DEFAULT NULL COMMENT '资源URL',
	@NotNull
	@Lable("$ptxtgl.resource.appkey")
	private String appkey; // bigint(20) DEFAULT NULL COMMENT '应用KEY',

	@Lable("$ptxtgl.resource.descn")
	@MaxLength(Config.DESCN_MAX_LENGTH)
	private String descn;
	
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

	public String getResourcetype() {
		return resourcetype;
	}

	public void setResourcetype(String resourcetype) {
		this.resourcetype = resourcetype;
	}

	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	public String getMethodname() {
		return methodname;
	}

	public void setMethodname(String methodname) {
		this.methodname = methodname;
	}

	public String getResourceurl() {
		return resourceurl;
	}

	public void setResourceurl(String resourceurl) {
		this.resourceurl = resourceurl;
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
