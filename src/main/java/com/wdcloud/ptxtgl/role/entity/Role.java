
package com.wdcloud.ptxtgl.role.entity;

import java.io.Serializable;

import com.wdcloud.common.util.Config;
import com.wdcloud.framework.web.springmvc.annotation.Lable;
import com.wdcloud.framework.web.springmvc.validation.Ascii;
import com.wdcloud.framework.web.springmvc.validation.MaxLength;
import com.wdcloud.framework.web.springmvc.validation.NotNull;
import com.wdcloud.framework.web.springmvc.validation.Unicode;
import com.wdcloud.ptxtgl.base.entity.BaseEntity;

/**

 * date: 2016年1月19日 *
 * @author bigd
 * @version 
 */
public class Role extends BaseEntity implements Serializable{

	/**
	 * 角色
	 * @author bigd
	 */
	private static final long serialVersionUID = 687499785515629572L;
	
	private String id;
	
	@Lable("$ptxtgl.role.rolecode")
	@NotNull
	@Ascii(minLength=Config.CODE_MIN_LENGTH,maxLength=Config.CODE_MAX_LENGTH)
	private String rolecode;
	
	@Lable("$ptxtgl.role.rolename")
	@NotNull
	@Unicode(minLength=Config.NAME_MIN_LENGTH,maxLength=Config.NAME_MAX_LENGTH)
	private String rolename;
	
	@Lable("$ptxtgl.role.appkey")
	@NotNull
	private String appkey;
	
	@Lable("$ptxtgl.role.descn")
	@MaxLength(Config.DESCN_MAX_LENGTH)
	private String descn;
	
	private String roletype;	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public String getRolecode() {
		return rolecode;
	}

	public void setRolecode(String rolecode) {
		this.rolecode = rolecode;
	}

	public String getRoletype() {
		return roletype;
	}

	public void setRoletype(String roletype) {
		this.roletype = roletype;
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

