package com.wdcloud.ptxtgl.base.service;

import java.util.Set;

import com.wdcloud.ptxtgl.base.entity.Menu;

public interface MenuService {
	
	
	/**
	 * 获取系统的所有菜单列表
	 * @param appkey
	 * @return
	 */
	Menu getSystemMenus(String appkey);
	
	/**
	 * 获取指定用户的菜单
	 * @param userid
	 * @return
	 */	
	Menu getMenusByUser(String userid,String appkey);
	
	
	/**
	 * 根据resourceIds获取此系统下的菜单编码
	 * @param map
	 * @return
	 */
	Set<String> getMenuCodesByReourceIdsAndAppkey(Set<String> resourceSet,String appkey);
	
}
