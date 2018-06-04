/**
 * @author CHENYB
 * @(#)ModuleMapper.java 2016年1月19日
 * 
 * wdcloud 版权所有2014~2016。
 */
package com.wdcloud.ptxtgl.module.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.wdcloud.ptxtgl.module.entity.Module;
import com.wdcloud.ptxtgl.resource.entity.Resource;

/**
 * @author CHENYB
 * @since 2016年1月19日
 */
public interface ModuleMapper {

	/**
	 * 查询菜单条数
	 * @author CHENYB
	 * @param params
	 * @return
	 * @since 2016年1月25日 上午11:11:22
	 */
	int selectModulesCount(Map<String, Object> params);

	/**
	 * 查询菜单列表
	 * @author CHENYB
	 * @param params
	 * @return
	 * @since 2016年1月25日 上午11:11:31
	 */
	List<Module> selectModules(Map<String, Object> params);

	/**
	 * 保存菜单
	 * @author CHENYB
	 * @param permission
	 * @since 2016年1月19日 下午7:10:23
	 */
	int insertModule(@Param("MODULE") Module module);

	/**
	 * 根据id查询菜单
	 * @author CHENYB
	 * @param id
	 * @return
	 * @since 2016年1月19日 下午7:10:41
	 */
	Module selectModuleById(@Param("ID") String id);

	/**
	 * 通过id删除Module
	 * @author CHENYB
	 * @param params
	 * @return
	 * @since 2016年1月20日 下午6:21:47
	 */
	int deleteModuleById(Map<String, Object> params);

	/**
	 * 修改Module
	 * @author CHENYB
	 * @param module
	 * @return
	 * @since 2016年1月20日 下午6:43:48
	 */
	int updateModule(@Param("MODULE") Module module);

	/**
	 * 查询ModuleCode数量
	 * @author CHENYB
	 * @param modulecode
	 * @param moduleid
	 * @return
	 * @since 2016年1月21日 下午3:46:03
	 */
	int getModuleCodeCount(@Param("MODULECODE") String modulecode, @Param("MODULEID") String moduleid);

	/**
	 * 查询可关联资源条数
	 * @author CHENYB
	 * @param params
	 * @return
	 * @since 2016年1月22日 下午5:45:11
	 */
	int selectAllowedResourcesCountForMenu(Map<String, Object> params);

	/**
	 * 查询可关联资源列表
	 * @author CHENYB
	 * @param params
	 * @return
	 * @since 2016年1月22日 下午5:45:25
	 */
	List<Resource> selectAllowedResourcesForMenu(Map<String, Object> params);

	/**
	 * @author CHENYB
	 * @param moduleid
	 * @param resourceid
	 * @param appkey
	 * @since 2016年1月25日 上午10:20:36
	 */
	int saveResourceForMenu(Map<String, Object> params);

	/**
	 * 根据归属系统查询菜单列表
	 * @author CHENYB
	 * @param appkey
	 * @return
	 * @since 2016年3月3日 下午2:59:03
	 */
	List<Map<String, String>> selectModulesByAppkey(@Param("APPKEY")String appkey);

	/**
	 * 查询某系统是否有根菜单
	 * @author CHENYB
	 * @param appkey
	 * @return
	 * @since 2016年4月27日 下午9:18:32
	 */
	int selectRootModuleCount(String appkey);

	/**
	 * 获取当前父菜单下displayOrder 最大值
	 * @param parentId
	 * @return
     */
	int getMaxDisplayOrderByParentId(String parentId);

}
