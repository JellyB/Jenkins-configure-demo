/**
 * @author CHENYB
 * @(#)PermissionResourceServiceImp.java 2016年1月20日
 * 
 * wdcloud 版权所有2014~2016。
 */
package com.wdcloud.ptxtgl.permissionResource.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wdcloud.common.exception.CommonException;
import com.wdcloud.framework.core.util.I18NMessageReader;
import com.wdcloud.ptxtgl.constant.PtxtglConstant;
import com.wdcloud.ptxtgl.permissionResource.entity.PermissionResource;
import com.wdcloud.ptxtgl.permissionResource.mapper.PermissionResourceMapper;
import com.wdcloud.ptxtgl.permissionResource.service.PermissionResourceService;

/**
 * @author CHENYB
 * @since 2016年1月20日
 */
@Service
public class PermissionResourceServiceImp implements PermissionResourceService {

	private static final Log logger = LogFactory.getLog(PermissionResourceServiceImp.class);

	@Autowired
	private PermissionResourceMapper permissionResourceMapper;

	/**
	 * @author CHENYB
	 * @see com.wdcloud.ptxtgl.permissionResource.service.PermissionResourceService#saveResourcesForPermission(java.lang.String, java.lang.String)
	 * @since 2016年1月21日 下午6:57:18
	 */
	@Override
	public Map<String, Object> saveResourcesForPermission(String permissionid, String resourceids) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(PtxtglConstant.RETURN_RESULT, false);
		if (StringUtils.isBlank(permissionid)) {
			result.put(PtxtglConstant.RETURN_MSG, "参数权限ID为空");
			return result;
		}
		if (StringUtils.isBlank(resourceids)) {
			result.put(PtxtglConstant.RETURN_MSG, "参数资源ID为空");
			return result;
		}
		String[] resids = resourceids.split(",");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("permissionid", permissionid);
		params.put("resids", resids);
		//先删除，再添加
		this.permissionResourceMapper.deleteResourcesFromPermission(params);
		int rs = 0;
		try {
			rs = this.permissionResourceMapper.insertResourcesForPermission(params);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			if (rs == 0) {
				result.put(PtxtglConstant.RETURN_MSG, "给权限分配资源失败，请稍后再试");
				return result;
			}
			throw new CommonException(I18NMessageReader.getMessage("operate.error"));
		}
		result.put(PtxtglConstant.RETURN_RESULT, true);
		return result;
	}

	/**
	 * @author CHENYB
	 * @see com.wdcloud.ptxtgl.permissionResource.service.PermissionResourceService#deleteResourcesFromPermission(java.lang.String, java.lang.String)
	 * @since 2016年1月22日 上午9:55:26
	 */
	@Override
	public Map<String, Object> deleteResourcesFromPermission(String permissionid, String resourceids) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(PtxtglConstant.RETURN_RESULT, false);
		if (StringUtils.isBlank(permissionid)) {
			result.put(PtxtglConstant.RETURN_MSG, "参数权限ID为空");
			return result;
		}
		if (StringUtils.isBlank(resourceids)) {
			result.put(PtxtglConstant.RETURN_MSG, "参数资源ID为空");
			return result;
		}
		String[] resids = resourceids.split(",");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("permissionid", permissionid);
		params.put("resids", resids);
		int rs = 0;
		try {
			rs = this.permissionResourceMapper.deleteResourcesFromPermission(params);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			if (rs == 0) {
				result.put(PtxtglConstant.RETURN_MSG, "解除资源失败，请稍后再试");
				return result;
			}
			throw new CommonException(I18NMessageReader.getMessage("operate.error"));
		}
		result.put(PtxtglConstant.RETURN_RESULT, true);
		return result;
	}

	/**
	 * @author CHENYB
	 * @see com.wdcloud.ptxtgl.permissionResource.service.PermissionResourceService#loadGrantedResources(int, int, com.wdcloud.ptxtgl.permissionResource.entity.PermissionResource)
	 * @since 2016年1月22日 下午2:21:26
	 */
	@Override
	public Map<String, Object> loadGrantedResources(int pageStart, int pageSize, PermissionResource permissionResource) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> params = this.assemblePermissionResourceConditions(pageStart, pageSize, permissionResource);
		int records = this.permissionResourceMapper.selectGrantedResourcesCount(params);
		List<PermissionResource> rows = this.permissionResourceMapper.selectGrantedResources(params);
		result.put(PtxtglConstant.RETURN_RECORDS, records);
		result.put(PtxtglConstant.RETURN_ROWS, rows);
		return result;
	}

	/**
	 * 组装权限分配资源条件
	 * @author CHENYB
	 * @param pageStart
	 * @param pageSize
	 * @param permissionResource
	 * @return
	 * @since 2016年1月22日 下午2:29:36
	 */
	private Map<String, Object> assemblePermissionResourceConditions(int pageStart, int pageSize,
			PermissionResource permissionResource) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(PtxtglConstant.PAGE_START, pageStart);
		params.put(PtxtglConstant.PAGE_SIZE, pageSize);
		if (StringUtils.isNotEmpty(permissionResource.getPermissionid())) {
			params.put("permissionid", permissionResource.getPermissionid());
		}
		if (StringUtils.isNotEmpty(permissionResource.getResourcename())) {
			params.put("resourcename", permissionResource.getResourcename());
		}
		if (StringUtils.isNotEmpty(permissionResource.getResourcecode())) {
			params.put("resourcecode", permissionResource.getResourcecode());
		}
		if (StringUtils.isNotEmpty(permissionResource.getAppkey())) {
			params.put("appkey", permissionResource.getAppkey());
		}
		return params;
	}

	/**
	 * @author CHENYB
	 * @see com.wdcloud.ptxtgl.permissionResource.service.PermissionResourceService#loadRemainedResources(int, int, com.wdcloud.ptxtgl.permissionResource.entity.PermissionResource)
	 * @since 2016年1月22日 下午2:21:26
	 */
	@Override
	public Map<String, Object> loadRemainedResources(int pageStart, int pageSize, PermissionResource permissionResource) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> params = this.assemblePermissionResourceConditions(pageStart, pageSize, permissionResource);
		int records = this.permissionResourceMapper.selectRemainedResourcesCount(params);
		List<PermissionResource> rows = this.permissionResourceMapper.selectRemainedResources(params);
		result.put(PtxtglConstant.RETURN_RECORDS, records);
		result.put(PtxtglConstant.RETURN_ROWS, rows);
		return result;
	}
	
	/**
	 * 
	 * 获取此归属系统下权限分配资源集合
	 * @author bigd
	 * @param  permissionSet
	 * @param  appkey
	 * 
	 */
	@Override
	public Set<String> getResourceIdsByPermissionIdsAndAppkey(Set<String> permissionSet, String appkey,  String resourcetype) {
		Set<String> ResourceIdSet = null;
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("permissionSet", permissionSet);
			map.put("appkey", appkey);
			map.put("resourcetype", resourcetype);
			ResourceIdSet = this.permissionResourceMapper.getResourceIdsByPermissionIdsAndAppkey(map);
		} catch (Exception e) {
			ResourceIdSet = new HashSet<String>();
			logger.error(I18NMessageReader.getMessage("ptxtgl.permission.getresourceid.error"), e);
		}
		return ResourceIdSet;
	}

}
