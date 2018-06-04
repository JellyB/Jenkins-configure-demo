/**
 * @author CHENYB
 * @(#)PermissionResourceService.java 2016年1月20日
 * 
 * wdcloud 版权所有2014~2016。
 */
package com.wdcloud.ptxtgl.permissionResource.service;

import java.util.Map;
import java.util.Set;

import com.wdcloud.ptxtgl.permissionResource.entity.PermissionResource;

/**
 * @author CHENYB
 * @since 2016年1月20日
 */
public interface PermissionResourceService {

	/**
	 * 给某权限分配资源
	 * @author CHENYB
	 * @param permissionid
	 * @param resourceids resourceid,用逗号分隔
	 * @return
	 * @since 2016年1月21日 下午6:56:22
	 */
	Map<String, Object> saveResourcesForPermission(String permissionid, String resourceids);

	/**
	 * 删除某权限下的资源
	 * @author CHENYB
	 * @param permissionid
	 * @param resourceids resourceid,用逗号分隔
	 * @return
	 * @since 2016年1月21日 下午7:15:15
	 */
	Map<String, Object> deleteResourcesFromPermission(String permissionid, String resourceids);

	/**
	 * 加载某权限下已分配的资源
	 * @author CHENYB
	 * @param pageStart
	 * @param pageSize
	 * @param permissionResource
	 * @return
	 * @since 2016年1月22日 下午2:16:36
	 */
	Map<String, Object> loadGrantedResources(int pageStart, int pageSize, PermissionResource permissionResource);

	/**
	 * 加载某权限下未分配的资源
	 * @author CHENYB
	 * @param pageStart
	 * @param pageSize
	 * @param permissionResource
	 * @return
	 * @since 2016年1月22日 下午2:17:03
	 */
	Map<String, Object> loadRemainedResources(int pageStart, int pageSize, PermissionResource permissionResource);
	
	/**
	 * 获取此归属系统下分配给权限的资源
	 * @param roleSet
	 * @param appkey
	 * @return
	 */
	Set<String> getResourceIdsByPermissionIdsAndAppkey(Set<String> permissionSet, String appkey, String resourcetype);
	
	

}
