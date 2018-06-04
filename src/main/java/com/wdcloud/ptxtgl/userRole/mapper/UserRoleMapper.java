package com.wdcloud.ptxtgl.userRole.mapper;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.wdcloud.ptxtgl.role.entity.Role;
import com.wdcloud.ptxtgl.user.entity.User;
import com.wdcloud.ptxtgl.userRole.entity.UserRole;

public interface UserRoleMapper {
	
	
	/**
	 * 展示用户已分配角色-条数
	 * @param map
	 * @return
	 */
	int listUserRolesCountByUserId(Map<String,Object> map);
	/**
	 * 展示用户已分配角色-记录
	 * @param userid
	 * @return
	 */
	List<UserRole> listUserRolesByUserId(Map<String,Object> map);
	
	
	/**
	 * 展示用户未分配角色-条数
	 * @param map
	 * @return
	 */
	int listUserNotRolesCountByUserId(Map<String,Object> map);
	/**
	 * 展示用户未分配角色-记录
	 * @param userid
	 * @return
	 */
	List<UserRole> listUserNotRolesByUserId(Map<String,Object> map);
	
	/**
	 * 取消授权（删除用户角色关系中间记录）
	 * @param map
	 * @return
	 */
	int deleteUserRolesByUserId(Map<String, Object> dataMap);
	
	/**
	 * 添加授权（添加用户角色关系中间记录）
	 * @param userRoleList
	 * @return
	 */
	/*int addUserRolesByUserId(List<UserRole> list);*/	
	int addUserRolesByUserId(Map<String, Object> dataMap);
	
	/**
	 * return roleid by specified critera
	 * @param dataMap
	 * @return
	 */
	String getRoleidByConditions(Map<String, String> dataMap);
	
	/**
	 * reuturn users by roleid
	 * @param rolecode
	 * @param appkey
	 * @return
	 */
	List<User> getUsersByRole(String roleid);
	
	/**
	 * 返回用户在此系统下的角色列表
	 * @param dataMap
	 * @return
	 */
	List<Role> getRolesByUserIdandAppkey(Map<String, String> dataMap);
	
	/**
	 * 获取此appkey系统下此用户所分配角色id
	 * @param userid
	 * @param appkey
	 * @return
	 */
	Set<String>getRoleidsByUseridAndAppkey(Map<String, String> dataMap);
	
	/**
	 * 获取此appkey系统下此用户所分配角色code
	 * @param userid
	 * @param appkey
	 * @return
	 */
	Set<String> getRoleCodesByUserIdAndAppkey(Map<String, String> dataMap);
}
