package com.wdcloud.ptxtgl.base.mapper;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.wdcloud.ptxtgl.base.entity.Menu;

public interface MenuMapper {
	
	
	/**
	 * 返回指定appkey下的根节点
	 * @param appkey
	 * @return
	 */
	Menu getRootMenu(String appkey);
	
	/**
	 * 获取指定父节点下的子菜单
	 * @param map
	 * @return
	 */
	List<Menu> getChildrenMenus(Map<String, String> map);	
	
	/**
	 * 获取指定用户在appkey 系统下的可以使用的菜单id列表
	 * @param userid
	 * @param appkey
	 * @return
	 */
	/*Set<String> getMenuCodesByUserIdandAppkey(Map<String,String> map);*/
	
	/**
	 * 获取此系统下关联资源的菜单 编码
	 * @author bigd
	 * @param map
	 * @return
	 */
	Set<String> getMenuCodesByReourceIdsAndAppkey(Map<String, Object> map);
}
