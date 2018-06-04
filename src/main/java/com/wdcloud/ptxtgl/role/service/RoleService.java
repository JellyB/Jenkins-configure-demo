package com.wdcloud.ptxtgl.role.service;

import java.util.Map;

import com.wdcloud.ptxtgl.role.entity.Role;

public interface RoleService {
	
	/**
	 * 查看角色代码是否被占用
	 * @param roleCode
	 * @param id
	 * @return
	 */
		
	Map<String, Object> checkRoleExist(String roleCode, String id);
	
	/**
	 * 根据ID加载角色信息
	 * @param id
	 * @return
	 */
	Role loadRoleMesg(String id);
	
	/**
	 * 角色新增
	 * @param role
	 * @return
	 */
	Map<String, Object> createrole(Role role);
	
	/**
	 * 删除角色（逻辑）
	 * @param id
	 * @return
	 */
	Map<String, Object> deleteroleByLogic(String id, String operateCode);
	
	/**
	 * 角色更新
	 * @param role
	 * @return
	 */
	Map<String, Object> updaterole(Role role);
	
	/**
	 * 加载角色列表
	 * @param role
	 * @return
	 */
	Map<String, Object> listroleByConditions(int start, int pageSize, Role role);
	
}
