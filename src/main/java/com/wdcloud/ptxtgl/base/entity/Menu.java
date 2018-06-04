package com.wdcloud.ptxtgl.base.entity;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonBackReference;

public class Menu implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6094905944864822082L;
	
	public static final boolean SUCCESS = true;
	
	public	static final boolean FAILURE = false;
	
	/**菜单返回结果**/
	private boolean result;
	
	/**菜单返回信息**/
	private String msg;
	
	/**是否根节点**/
	private boolean isRoot;
	
	/**是否拥有权限；true 是；false:否**/
	private boolean isAuth;
	
	/**此菜单是否显示,根菜单不予展示**/
	private boolean display;
	
	private String id;
	
	private String parentId;
	
	/**菜单编码**/
	private String modulecode;
	
	/**菜单名称**/
	private String modulename;
	
	private String menuid;
	
	private String resourceurl;
	
	/**父菜单**/
	@JsonBackReference 
	private Menu parent;
	
	/**子菜单们**/
	private List<Menu> child;
	
	

	public boolean isRoot() {
		return isRoot;
	}

	public void setRoot(boolean isRoot) {
		this.isRoot = isRoot;
	}

	public boolean isAuth() {
		return isAuth;
	}

	public void setAuth(boolean isAuth) {
		this.isAuth = isAuth;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getModulecode() {
		return modulecode;
	}

	public void setModulecode(String modulecode) {
		this.modulecode = modulecode;
	}

	public String getModulename() {
		return modulename;
	}

	public void setModulename(String modulename) {
		this.modulename = modulename;
	}

	public String getMenuid() {
		return menuid;
	}

	public void setMenuid(String menuid) {
		this.menuid = menuid;
	}

	public String getResourceurl() {
		return resourceurl;
	}

	public void setResourceurl(String resourceurl) {
		this.resourceurl = resourceurl;
	}

	public Menu getParent() {
		return parent;
	}

	public void setParent(Menu parent) {
		this.parent = parent;
	}

	public List<Menu> getChild() {
		return child;
	}

	public void setChild(List<Menu> child) {
		this.child = child;
	}

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public boolean isDisplay() {
		return display;
	}

	public void setDisplay(boolean display) {
		this.display = display;
	}
	
	
}
