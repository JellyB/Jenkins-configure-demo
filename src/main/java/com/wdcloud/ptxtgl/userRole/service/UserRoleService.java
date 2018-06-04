package com.wdcloud.ptxtgl.userRole.service;

import java.util.Map;
import java.util.Set;

import com.wdcloud.ptxtgl.base.entity.ListResult;
import com.wdcloud.ptxtgl.userRole.entity.UserRole;

public interface UserRoleService {
	
	/**
	 * 展示用户已分配角色
	 * @param pageSize 
	 * @param pageStart 
	 * @param userRole
	 * @return
	 */
	Map<String, Object> listUserRolesByUserId(int pageStart, int pageSize, UserRole userRole);
	
	/**
	 * 展示用户未分配角色
	 * @param pageSize 
	 * @param pageStart 
	 * @param userRole
	 * @return
	 */
	Map<String, Object> listUserNotRolesByUserId(int pageStart, int pageSize, UserRole userRole);

	
	/**
	 * 取消用户授权
	 * @param userid
	 * @param roleids
	 * @return
	 */
	Map<String, Object> deleteUserRolesByUserId(String userid, String roleids);
	
	/**
	 * add roles to the user;
	 * @param userid
	 * @param roleids
	 * @return
	 */
	/*Map<String, Object> addUserRolesByUserId(String userid, String roleids);*/
	Map<String, Object> addUserRolesByUserId(String userid, String roleids);	
	
	/**
	 * 返回指定角色的人员信息
	 * @param roldcode
	 * @param appkey
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	ListResult getUsersByRole(String roldcode,String appkey);
	
	/**
	 * 对外提供接口
	 * 根据当前用户id返回指定系统下的角色列表
	 * @param userid
	 * @param appkey
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	ListResult getRoleByUserIdAndAappkey (String userid, String appkey);
	
	
	/**
	 * 获取用户对应appkey下分配的角色IDs
	 * @param userid
	 * @param appkey
	 * @return
	 */
	Set<String> getRoleidsByUseridAndAppkey(String userid, String appkey);
	
	/**
	 * 获取此归属系统下用户分配的角色代码
	 * @param userid
	 * @param appkey
	 * @return
	 */
	Set<String> getRoleCodesByUserIdAndAppkey(String userid, String appkey);
}	
