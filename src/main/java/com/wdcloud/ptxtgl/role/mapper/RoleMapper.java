package com.wdcloud.ptxtgl.role.mapper;

import java.util.List;
import java.util.Map;

import com.wdcloud.ptxtgl.role.entity.Role;

public interface RoleMapper {
	
	/**
	 * 检查角色是否存在
	 * @param map
	 * @return
	 */
	int checkRoleExist(Map<String, String> map);
	
	/**
	 * 更新角色信息检查角色代码是否被占用
	 * @param dataMap
	 * @return
	 */
	int checkRoleExistForUpdate(Map<String, String> dataMap);
	
	/**
	 * 创建角色
	 * @param Role
	 * @return
	 */
	int createRole(Role Role);
	
	/**
	 * 根据ID加载角色信息
	 * @param id
	 * @return
	 */
	Role loadRoleMesg(String id);
	
	/**
	 * 删除角色
	 * @param id
	 * @return
	 */
	int deleteRoleByLogic(Map<String,Object> map);
	
	/**
	 * 更新角色
	 * @param Role
	 * @return
	 */
	int updateRole(Role Role);
	
	/**
	 * 查询角色-条数
	 * @param dataMap
	 * @return
	 */
	int listRoleCountByConditions(Map<String, Object> dataMap);
	
	/**
	 * 查询角色-记录
	 * @param dataMap
	 * @return
	 */
	List<Role> listRoleByConditions(Map<String, Object> dataMap);

	

	

	
}
