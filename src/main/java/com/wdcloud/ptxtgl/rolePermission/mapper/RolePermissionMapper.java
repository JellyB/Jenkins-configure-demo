package com.wdcloud.ptxtgl.rolePermission.mapper;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.wdcloud.ptxtgl.rolePermission.entity.RolePermission;

public interface RolePermissionMapper {	
	
	/**
	 * 展示角色已分配权限-条数
	 * @param map
	 * @return
	 */
	int listRolePermissionsCountByRoleId(Map<String,Object> map);
	
	/**
	 * 展示角色已分配权限-记录
	 * @param userid
	 * @return
	 */
	List<RolePermission> listRolePermissionsByRoleId(Map<String,Object> map);
	
	
	/**
	 * 展示角色未分配权限-条数
	 * @param map
	 * @return
	 */
	int listRoleNotPermissionsCountByRoleId(Map<String,Object> map);
	
	/**
	 * 展示角色未分配权限-记录
	 * @param userid
	 * @return
	 */
	List<RolePermission> listRoleNotPermissionsByRoleId(Map<String,Object> map);
	
	/**
	 * 查询角色已经分配权限条数
	 * @param roleid
	 * @return
	 */
	/*int getRolePermissionsCount(String roleid);*/
	
	/**
	 * 删除角色权限中间关系表（分配权限全删全建）
	 * @param roleid
	 * @return
	 */
	/*int deleteRoleHavenPermissions(String roleid);*/
	
	/**
	 * 取消权限（删除角色权限关系中间记录）
	 * @param map
	 * @return
	 */
	int deleteRolePermissionsByRoleId(Map<String, Object> dataMap);
	
	/**
	 * 添加权限（添加角色权限关系中间记录）
	 * @param map
	 * @return
	 */
	int addRolePermissionsByRoleId(Map<String, Object> dataMap);
	
	/**
	 * 获取此appkey系统下角色列表对应的权限id列表
	 * @param dataMap
	 * @return
	 */
	Set<String> getPermissionIdsByRoleIdsAndAppkey(Map<String, Object> dataMap);
}
