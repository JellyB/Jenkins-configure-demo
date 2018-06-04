/**
 * @author CHENYB
 * @(#)ResourceService.java 2016年1月19日
 * 
 * wdcloud 版权所有2014~2016。
 */
package com.wdcloud.ptxtgl.resource.service;

import java.util.Map;
import java.util.Set;

import com.wdcloud.ptxtgl.resource.entity.Resource;

/**
 * @author CHENYB
 * @since 2016年1月19日
 */
public interface ResourceService {

	/**
	 * 保存资源
	 * @author CHENYB
	 * @param resource
	 * @return
	 * @since 2016年1月21日 下午1:43:07
	 */
	Map<String, Object> saveResource(Resource resource);

	/**
	 * 判断resourcecode是否已存在
	 * @author CHENYB
	 * @param resourcecode
	 * @param resourceid
	 * @return 存在返回true，不存在返回false
	 * @since 2016年1月21日 下午3:33:13
	 */
	boolean resourceCodeExist(String resourcecode, String resourceid);

	/**
	 * 删除资源(批量)
	 * @author CHENYB
	 * @param resourceids resourceid,用逗号分隔
	 * @param opratercode
	 * @return
	 * @since 2016年1月21日 下午1:49:15
	 */
	Map<String, Object> deleteResources(String resourceids, String opratercode);

	/**
	 * 修改资源
	 * @author CHENYB
	 * @param resource
	 * @return
	 * @since 2016年1月21日 下午2:06:52
	 */
	Map<String, Object> updateResource(Resource resource);

	/**
	 * 加载资源列表
	 * @author CHENYB
	 * @param pageStart
	 * @param pageSize
	 * @param resource
	 * @return
	 * @since 2016年1月25日 上午11:54:16
	 */
	Map<String, Object> loadResources(int pageStart, int pageSize, Resource resource);

	/**
	 * 根据id加载资源
	 * @author CHENYB
	 * @param resourceid
	 * @return
	 * @since 2016年2月18日 下午2:09:36
	 */
	Resource getResourceById(String resourceid);
	
	
	/**
	 * 根据用户ID及Appkey获取用户可访问资源Url
	 * @author bigd
	 * @param map
	 * @return
	 */
	Set<String> getResourceUrlByUserIdandAppkey(String userIdAppkey);

}
