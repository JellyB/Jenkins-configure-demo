/**
 * @author CHENYB
 * @(#)PermissionMapper.java 2016年1月19日
 * 
 * wdcloud 版权所有2014~2016。
 */
package com.wdcloud.ptxtgl.permission.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.wdcloud.ptxtgl.permission.entity.Permission;

/**
 * @author CHENYB
 * @since 2016年1月19日
 */
public interface PermissionMapper {

	/**
	 * 查询权限条件
	 * @author CHENYB
	 * @param params
	 * @return
	 * @since 2016年1月25日 上午11:43:07
	 */
	int selectPermissionsCount(Map<String, Object> params);

	/**
	 * 查询权限列表
	 * @author CHENYB
	 * @param params
	 * @return
	 * @since 2016年1月25日 上午11:43:13
	 */
	List<Permission> selectPermissions(Map<String, Object> params);

	/**
	 * 保存权限
	 * @author CHENYB
	 * @param permission
	 * @return
	 * @since 2016年1月19日 下午7:08:34
	 */
	int insertPermission(@Param("PERMISSION") Permission permission);

	/**
	 * 根据id查询权限
	 * @author CHENYB
	 * @param id
	 * @return
	 * @since 2016年1月19日 下午7:08:43
	 */
	Permission selectPermissionById(@Param("ID") String id);

	/**
	 * 修改权限
	 * @author CHENYB
	 * @param permission
	 * @return
	 * @since 2016年1月20日 下午4:11:23
	 */
	int updatePermission(@Param("PERMISSION") Permission permission);

	/**
	 * 通过id删除权限
	 * @author CHENYB
	 * @param params
	 * @return
	 * @since 2016年1月20日 下午4:11:23
	 */
	int deletePermissionById(Map<String, Object> params);

	/**
	 * 查询PermissionCode数量
	 * @author CHENYB
	 * @param permissioncode
	 * @param permissionid
	 * @return
	 * @since 2016年1月21日 下午3:21:14
	 */
	int getPermissionCodeCount(@Param("PERMISSIONCODE") String permissioncode, @Param("PERMISSIONID") String permissionid);

}
