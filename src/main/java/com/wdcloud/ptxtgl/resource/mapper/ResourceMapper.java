/**
 * @author CHENYB
 * @(#)ResourceMapper.java 2016年1月19日
 * 
 * wdcloud 版权所有2014~2016。
 */
package com.wdcloud.ptxtgl.resource.mapper;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.wdcloud.ptxtgl.resource.entity.Resource;

/**
 * @author CHENYB
 * @since 2016年1月19日
 */
public interface ResourceMapper {

	/**
	 * 查询资源条数
	 * @author CHENYB
	 * @param params
	 * @return
	 * @since 2016年1月25日 下午12:47:38
	 */
	int selectResourcesCount(Map<String, Object> params);

	/**
	 * 查询资源列表
	 * @author CHENYB
	 * @param params
	 * @return
	 * @since 2016年1月25日 下午12:47:45
	 */
	List<Resource> selectResources(Map<String, Object> params);

	/**
	 * 保存资源
	 * @author CHENYB
	 * @param resource
	 * @since 2016年1月19日 下午7:01:44
	 */
	int insertResource(@Param("RESOURCE") Resource resource);

	/**
	 * 根据id查询资源
	 * @author CHENYB
	 * @param id
	 * @return
	 * @since 2016年1月19日 下午7:04:30
	 */
	Resource selectResourceById(@Param("ID") String id);

	/**
	 * 通过id删除资源
	 * @author CHENYB
	 * @param params
	 * @return
	 * @since 2016年1月21日 下午1:51:33
	 */
	int deleteResourceById(Map<String, Object> params);

	/**
	 * 修改资源
	 * @author CHENYB
	 * @param resource
	 * @return
	 * @since 2016年1月21日 下午2:09:29
	 */
	int updateResource(@Param("RESOURCE") Resource resource);

	/**
	 * 查询ResourceCode数量
	 * @author CHENYB
	 * @param resourcecode
	 * @param resourceid
	 * @return
	 * @since 2016年1月21日 下午3:34:05
	 */
	int getPermissionCodeCount(@Param("RESOURCECODE") String resourcecode, @Param("RESOURCEID") String resourceid);
	
	/**
	 * 根据用户Id及Appkey获取用户可访问资源Url
	 * @author bigd
	 * @param map
	 * @return
	 */
	Set<String> getResourceUrlByUserIdAndAppkey(Map<String,String> map);

}
