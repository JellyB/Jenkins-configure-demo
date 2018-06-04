/**
 * @author CHENYB
 * @(#)PermissionResourceController.java 2016年1月20日
 * 
 * wdcloud 版权所有2014~2016。
 */
package com.wdcloud.ptxtgl.permissionResource.web.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wdcloud.common.util.PageUtil;
import com.wdcloud.framework.core.query.Page;
import com.wdcloud.ptxtgl.permissionResource.entity.PermissionResource;
import com.wdcloud.ptxtgl.permissionResource.service.PermissionResourceService;

/**
 * @author CHENYB
 * @since 2016年1月20日
 */
@Controller
@RequestMapping("/permissionResource")
public class PermissionResourceController {

	@Autowired
	private PermissionResourceService permissionResourceService;

	/**
	 * 给某权限分配资源
	 * @author CHENYB
	 * @param permissionid
	 * @param ids resourceid,用逗号分隔
	 * @return
	 * @since 2016年1月21日 下午2:59:14
	 */
	@ResponseBody
	@RequestMapping(value = "/related", method = RequestMethod.POST)
	public Map<String, Object> grantResources(@RequestParam String permissionid, @RequestParam String ids) {
		return this.permissionResourceService.saveResourcesForPermission(permissionid, ids);
	}

	/**
	 * 解除某权限下的资源
	 * @author CHENYB
	 * @param permissionid
	 * @param ids resourceid,用逗号分隔
	 * @return
	 * @since 2016年1月22日 上午11:20:32
	 */
	@ResponseBody
	@RequestMapping(value = "/unRelated", method = RequestMethod.POST)
	public Map<String, Object> revokeResources(@RequestParam String permissionid, @RequestParam String ids) {
		return this.permissionResourceService.deleteResourcesFromPermission(permissionid, ids);
	}

	/**
	 * 获取某权限下已分配的资源(包括条数)[Granted]
	 * @author CHENYB
	 * @param permissionResource
	 * @param request
	 * @return
	 * @since 2016年1月22日 上午11:52:49
	 */
	@ResponseBody
	@RequestMapping(value = "/relatedList")
	public Map<String, Object> listGrantedResources(PermissionResource permissionResource, HttpServletRequest request) {
		@SuppressWarnings("unchecked")
		Page<PermissionResource> page = PageUtil.getPageForJqGrid(request);
		return this.permissionResourceService.loadGrantedResources(page.getStart(), page.getPageSize(), permissionResource);
	}

	/**
	 * 获取某权限下未分配的资源(包括条数)[Remained]
	 * @author CHENYB
	 * @param permissionResource
	 * @param request
	 * @return
	 * @since 2016年1月22日 上午11:53:02
	 */
	@ResponseBody
	@RequestMapping(value = "/unRelatedList")
	public Map<String, Object> listRemainedResources(PermissionResource permissionResource, HttpServletRequest request) {
		@SuppressWarnings("unchecked")
		Page<PermissionResource> page = PageUtil.getPageForJqGrid(request);
		return this.permissionResourceService.loadRemainedResources(page.getStart(), page.getPageSize(), permissionResource);
	}

}
