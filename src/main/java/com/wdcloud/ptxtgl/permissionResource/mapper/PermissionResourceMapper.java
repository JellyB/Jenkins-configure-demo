/**
 * @author CHENYB
 * @(#)PermissionResourceMapper.java 2016年1月20日
 * 
 * wdcloud 版权所有2014~2016。
 */
package com.wdcloud.ptxtgl.permissionResource.mapper;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.wdcloud.ptxtgl.permissionResource.entity.PermissionResource;

/**
 * @author CHENYB
 * @since 2016年1月20日
 */
public interface PermissionResourceMapper {

	/**
	 * 给某权限分配资源
	 * @author CHENYB
	 * @param params
	 * @since 2016年1月21日 下午7:01:58
	 */
	int insertResourcesForPermission(Map<String, Object> params);

	/**
	 * 删除某权限下的资源
	 * @author CHENYB
	 * @param permissionid
	 * @return
	 * @since 2016年1月21日 下午7:17:13
	 */
	int deleteResourcesFromPermission(Map<String, Object> params);

	/**
	 * 查询已分配资源条数
	 * @author CHENYB
	 * @param params
	 * @return
	 * @since 2016年1月22日 下午2:33:25
	 */
	int selectGrantedResourcesCount(Map<String, Object> params);

	/**
	 * 查询已分配资源列表
	 * @author CHENYB
	 * @param params
	 * @return
	 * @since 2016年1月22日 下午2:34:10
	 */
	List<PermissionResource> selectGrantedResources(Map<String, Object> params);

	/**
	 * 查询未分配资源条数
	 * @author CHENYB
	 * @param params
	 * @return
	 * @since 2016年1月22日 下午2:35:46
	 */
	int selectRemainedResourcesCount(Map<String, Object> params);

	/**
	 * 查询未分配资源列表
	 * @author CHENYB
	 * @param params
	 * @return
	 * @since 2016年1月22日 下午2:35:51
	 */
	List<PermissionResource> selectRemainedResources(Map<String, Object> params);
	
	/**
	 * 获取此系统下分配给权限的资源集合
	 * 说明： 如果map 传入resourceType 则只查询此类型的资源
	 * 菜单类型码表 ： 01 ； 按钮类型码表 02
	 * @author bigd
	 * @param map
	 * @return
	 */
	Set<String> getResourceIdsByPermissionIdsAndAppkey(Map<String, Object> map);

}
