/**
 * @author CHENYB
 * @(#)PermissionService.java 2016年1月19日
 * 
 * wdcloud 版权所有2014~2016。
 */
package com.wdcloud.ptxtgl.permission.service;

import java.util.Map;

import com.wdcloud.ptxtgl.permission.entity.Permission;

/**
 * @author CHENYB
 * @since 2016年1月19日
 */
public interface PermissionService {

	/**
	 * 加载权限列表
	 * @author CHENYB
	 * @param pageStart
	 * @param pageSize
	 * @param permission
	 * @return
	 * @since 2016年1月25日 上午11:38:08
	 */
	Map<String, Object> loadPermissions(int pageStart, int pageSize, Permission permission);

	/**
	 * 保存权限
	 * @author CHENYB
	 * @param permission
	 * @return
	 * @since 2016年1月20日 下午3:05:54
	 */
	Map<String, Object> savePermission(Permission permission);

	/**
	 * 判断permissioncode是否已存在
	 * @author CHENYB
	 * @param permissioncode
	 * @param permissionid
	 * @return 存在返回true，不存在返回false
	 * @since 2016年1月21日 下午3:17:41
	 */
	boolean permissionCodeExist(String permissioncode, String permissionid);

	/**
	 * 删除权限(批量)
	 * @author CHENYB
	 * @param permissionids permissionid,用逗号分隔
	 * @param opratercode
	 * @return
	 * @since 2016年1月20日 下午4:16:51
	 */
	Map<String, Object> deletePermissions(String permissionids, String opratercode);

	/**
	 * 修改权限
	 * @author CHENYB
	 * @param permission
	 * @return
	 * @since 2016年1月20日 下午5:20:55
	 */
	Map<String, Object> updatePermission(Permission permission);

	/**
	 * 根据id加载权限
	 * @author CHENYB
	 * @param permissionid
	 * @return
	 * @since 2016年2月18日 下午2:05:06
	 */
	Permission getPermissionById(String permissionid);

}
