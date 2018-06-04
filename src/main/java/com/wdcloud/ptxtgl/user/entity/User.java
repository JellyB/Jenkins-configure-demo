package com.wdcloud.ptxtgl.user.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;
import com.wdcloud.common.util.Config;
import com.wdcloud.framework.web.springmvc.annotation.Lable;
import com.wdcloud.framework.web.springmvc.validation.LoginName;
import com.wdcloud.framework.web.springmvc.validation.MaxLength;
import com.wdcloud.framework.web.springmvc.validation.NotNull;
import com.wdcloud.framework.web.springmvc.validation.Passworder;
import com.wdcloud.framework.web.springmvc.validation.Unicode;
import com.wdcloud.ptxtgl.base.entity.BaseEntity;
import com.wdcloud.ptxtgl.base.inter.Save;
import com.wdcloud.ptxtgl.base.inter.Update;

public class User extends BaseEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3538903375833658100L;
	
	private String id;	
	
	private String loginid;	
	
	@NotNull(groups={Save.class,Update.class})
	@Lable("$ptxtgl.user.loginname")
	@LoginName(groups={Save.class,Update.class})
	private String loginname;
	
	@NotNull(groups={Save.class,Update.class})
	@Lable("$ptxtgl.user.nickname")
	@LoginName(groups={Save.class,Update.class})
	private String nickname;
	
	@NotNull(groups={Save.class,Update.class})
	@Lable("$ptxtgl.user.username")
	@Unicode(minLength=2,maxLength=20,groups={Save.class,Update.class})
	private String username;
	
	@Lable("$ptxtgl.user.passwd")
	@NotNull(groups={Save.class})
	@Passworder(groups={Save.class})
	private String passwd;
	
	@Lable("$ptxtgl.user.confirmpasswd")
	@NotNull(groups={Save.class})
	@Passworder(groups={Save.class})
	private String confirmpasswd;
	
	@Lable("$ptxtgl.user.descn")
	@MaxLength(Config.DESCN_MAX_LENGTH)
	private String descn;	
	
	private String sex;
	
	private String usertype;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JSONField(format="yyyy-MM-dd")
	private Date birthday;
	
	private String cellphone;
	
	private String email;
	
	private String emailstatus;
	
	private String status;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JSONField(format="yyyy-MM-dd")
	private Date registertime;
	
	private String yhtx;	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getLoginid() {
		return loginid;
	}

	public void setLoginid(String loginid) {
		this.loginid = loginid;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getUsertype() {
		return usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmailstatus() {
		return emailstatus;
	}

	public void setEmailstatus(String emailstatus) {
		this.emailstatus = emailstatus;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getRegistertime() {
		return registertime;
	}

	public void setRegistertime(Date registertime) {
		this.registertime = registertime;
	}

	public String getYhtx() {
		return yhtx;
	}

	public void setYhtx(String yhtx) {
		this.yhtx = yhtx;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getDescn() {
		return descn;
	}

	public void setDescn(String descn) {
		this.descn = descn;
	}

	public String getConfirmpasswd() {
		return confirmpasswd;
	}

	public void setConfirmpasswd(String confirmpasswd) {
		this.confirmpasswd = confirmpasswd;
	}	
	
	
}
