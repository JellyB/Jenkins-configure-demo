package com.wdcloud.ptxtgl.rolePermission.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.wdcloud.ptxtgl.rolePermission.entity.RolePermission;

public interface RolePermissionService {

	
	/**
	 * 展示角色已分配权限
	 * @param pageSize 
	 * @param pageStart 
	 * @param rolePermission
	 * @return
	 */
	Map<String, Object> listRolePermissionsByRoleId(int pageStart, int pageSize, RolePermission rolePermission);
	
	/**
	 * 展示角色未分配权限
	 * @param pageSize 
	 * @param pageStart 
	 * @param rolePermission
	 * @return
	 */
	Map<String, Object> listRoleNotPermissionsByRoleId(int pageStart, int pageSize, RolePermission rolePermission);
	
	
	
	/**
	 * 取消角色授权
	 * @param roleid
	 * @param roleids
	 * @return
	 */
	Map<String, Object> deleteRolePermissionsByRoleId(String roleid, String permissionids);
	
	/**
	 * 添加角色权限
	 * @param roleid
	 * @param permissionids
	 * @return
	 */
	Map<String, Object> addRolePermissionsByRoleId(String roleid, String permissionids);
	
	/**
	 * 分配权限管理一次性增删（全删全建）
	 * @param roleid
	 * @return
	 */
	/*Map<String, Object> dealRolePermissionDatas(String roleid, String permissionids);*/
	
	/**
	 * 获取此appkey系统下此角色ID分配的权限列表ids
	 * @param roleId
	 * @param appkey
	 * @return
	 */
	Set<String> getPermissionIdsByRoleIdsAndAppkey(Set<String> roleSet, String appkey);
}
