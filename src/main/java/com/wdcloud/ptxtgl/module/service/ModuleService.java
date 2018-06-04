/**
 * @author CHENYB
 * @(#)ModuleService.java 2016年1月19日
 * 
 * wdcloud 版权所有2014~2016。
 */
package com.wdcloud.ptxtgl.module.service;

import java.util.Map;

import com.wdcloud.ptxtgl.module.entity.Module;
import com.wdcloud.ptxtgl.resource.entity.Resource;

/**
 * @author CHENYB
 * @since 2016年1月19日
 */
public interface ModuleService {
	/**
	 * 加载菜单列表
	 * @author CHENYB
	 * @param pageStart
	 * @param pageSize
	 * @param module
	 * @return
	 * @since 2016年1月25日 上午11:03:24
	 */
	Map<String, Object> loadModules(int pageStart, int pageSize, Module module);

	/**
	 * 创建菜单
	 * @author CHENYB
	 * @param module
	 * @return
	 * @since 2016年1月20日 下午5:53:32
	 */
	Map<String, Object> saveModule(Module module);

	/**
	 * 判断modulecode是否已存在
	 * @author CHENYB
	 * @param modulecode
	 * @param moduleid
	 * @return 存在返回true，不存在返回false
	 * @since 2016年1月21日 下午3:42:11
	 */
	boolean moduleCodeExist(String modulecode, String moduleid);

	/**
	 * 删除Module(批量)
	 * @author CHENYB
	 * @param moduleids moduleid,用逗号分隔
	 * @param opratercode
	 * @return
	 * @since 2016年1月20日 下午6:16:10
	 */
	Map<String, Object> deleteModules(String moduleids, String opratercode);

	/**
	 * 修改Module
	 * @author CHENYB
	 * @param module
	 * @return
	 * @since 2016年1月20日 下午6:40:56
	 */
	Map<String, Object> updateModule(Module module);

	/**
	 * 加载可关联的菜单资源
	 * @author CHENYB
	 * @param start
	 * @param pageSize
	 * @param resource
	 * @return
	 * @since 2016年1月22日 下午5:20:55
	 */
	Map<String, Object> loadAllowedResourcesForMenu(int pageStart, int pageSize, Resource resource);

	/**
	 * 菜单关联资源
	 * @author CHENYB
	 * @param params
	 * @return
	 * @since 2016年1月22日 下午7:01:46
	 */
	void saveResourceForMenu(Map<String, Object> params);

	/**
	 * 根据id加载菜单
	 * @author CHENYB
	 * @param moduleid
	 * @return
	 * @since 2016年2月18日 下午2:13:15
	 */
	Module getModuleById(String moduleid);

	/**
	 * 根据归属系统查询菜单列表
	 * @author CHENYB
	 * @param appkey
	 * @return
	 * @since 2016年3月3日 下午2:28:34
	 */
	Map<String, Object> loadModulesByAppkey(String appkey);

	/**
	 * 查询某系统是否有根菜单
	 * @author CHENYB
	 * @param appkey
	 * @return
	 * @since 2016年4月27日 下午9:14:58
	 */
	boolean hasRootModule(String appkey);

}
