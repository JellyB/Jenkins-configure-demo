package com.wdcloud.ptxtgl.rolePermission.service.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wdcloud.common.exception.CommonException;
import com.wdcloud.framework.core.util.I18NMessageReader;
import com.wdcloud.ptxtgl.constant.PtxtglConstant;
import com.wdcloud.ptxtgl.rolePermission.entity.RolePermission;
import com.wdcloud.ptxtgl.rolePermission.mapper.RolePermissionMapper;
import com.wdcloud.ptxtgl.rolePermission.service.RolePermissionService;

@Service
public class RolePermissionServiceImpl implements RolePermissionService{
	
	
	private static Logger logger = LoggerFactory.getLogger(RolePermissionServiceImpl.class);
	
	@Autowired
	private RolePermissionMapper rolePermissionMapper;
	
	
	/**
	 * 展示角色已分配权限
	 * @param userid
	 * @return
	 */
	@Override
	public Map<String, Object> listRolePermissionsByRoleId(int pageStart, int pageSize,RolePermission userPermission) {
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		dataMap = this.listRolePermissionsConditions(pageStart,pageSize,userPermission);
		int records = this.rolePermissionMapper.listRolePermissionsCountByRoleId(dataMap);
		List<RolePermission> rows = this.rolePermissionMapper.listRolePermissionsByRoleId(dataMap);
		map.put(PtxtglConstant.RETURN_RECORDS, records);
		map.put(PtxtglConstant.RETURN_ROWS, rows);		
		return map;
	}
	
	@Override
	public Map<String, Object> listRoleNotPermissionsByRoleId(int pageStart, int pageSize,RolePermission userPermission) {
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		dataMap = this.listRolePermissionsConditions(pageStart,pageSize,userPermission);
		int records = this.rolePermissionMapper.listRoleNotPermissionsCountByRoleId(dataMap);
		List<RolePermission> rows = this.rolePermissionMapper.listRoleNotPermissionsByRoleId(dataMap);
		map.put(PtxtglConstant.RETURN_RECORDS, records);
		map.put(PtxtglConstant.RETURN_ROWS, rows);		
		return map;
	}
	
	
	/**
	 * 获取角色分配权限查询条件
	 * @param pageSize 
	 * @param pageStart 
	 * @param userPermission
	 * @return
	 */
	private Map<String, Object> listRolePermissionsConditions(int pageStart, int pageSize, RolePermission userPermission) {
		Map<String,Object> dataMap = new HashMap<String,Object>();
		dataMap.put(PtxtglConstant.PAGE_START, pageStart);
		dataMap.put(PtxtglConstant.PAGE_SIZE,  pageSize);
		if(StringUtils.isNotEmpty(userPermission.getRoleid())){
			dataMap.put("roleid", userPermission.getRoleid());
		}
		if(StringUtils.isNotEmpty(userPermission.getPermissioncode())){
			dataMap.put("permissioncode", userPermission.getPermissioncode());
		}
		if(StringUtils.isNotEmpty(userPermission.getPermissionname())){
			dataMap.put("permissionname", userPermission.getPermissionname());
		}
		if(StringUtils.isNotEmpty(userPermission.getAppkey())){
			dataMap.put("appkey", userPermission.getAppkey());
		}		
		return dataMap;
	}
	
	/**
	 * 取消角色已授权权限
	 * @author bigd
	 * @param  userid
	 * @param  roleids
	 */
	@Override
	public Map<String, Object> deleteRolePermissionsByRoleId(String roleid, String permissionids) {
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> permissionidsList = Arrays.asList(permissionids.split(","));
		dataMap.put("roleid", roleid);
		dataMap.put("permissionids",permissionidsList);
		map.put(PtxtglConstant.RETURN_RESULT, false);
		try {
			int excuteCount = 0;
			excuteCount = this.rolePermissionMapper.deleteRolePermissionsByRoleId(dataMap);
			if (excuteCount > 0) {
				map.put(PtxtglConstant.RETURN_RESULT, true);
				map.put(PtxtglConstant.RETURN_MSG, I18NMessageReader.getMessage("rolepermission.deal.successed"));
			} else {
				map.put(PtxtglConstant.RETURN_MSG, I18NMessageReader.getMessage("rolepermission.deal.failed"));
				throw new CommonException(I18NMessageReader.getMessage("rolepermission.deal.failed"));
			} 
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new CommonException(I18NMessageReader.getMessage("rolepermission.deal.failed"));
		}
		return map;
	}
	
	/**
	 * 取消角色已授权权限
	 * @author bigd
	 * @param  userid
	 * @param  roleids
	 */
	@Override
	public Map<String, Object> addRolePermissionsByRoleId(String roleid, String permissionids) {
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> permissionidsList = Arrays.asList(permissionids.split(","));
		dataMap.put("roleid", roleid);
		dataMap.put("permissionids",permissionidsList);
		map.put(PtxtglConstant.RETURN_RESULT, false);
		try {
			int excuteCount = 0;
			excuteCount = this.rolePermissionMapper.addRolePermissionsByRoleId(dataMap);
			if (excuteCount > 0) {
				map.put(PtxtglConstant.RETURN_RESULT, true);
				map.put(PtxtglConstant.RETURN_MSG, I18NMessageReader.getMessage("rolepermission.deal.successed"));
			} else {
				map.put(PtxtglConstant.RETURN_MSG, I18NMessageReader.getMessage("rolepermission.deal.failed"));
				throw new CommonException(I18NMessageReader.getMessage("rolepermission.deal.failed"));
			} 
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new CommonException(I18NMessageReader.getMessage("rolepermission.deal.failed"));
		}
		return map;
	}
	
	/**
	 * 获取角色分配权限ids
	 * @author bigd
	 * @param roleIdList
	 * @param appkey
	 */
	@Override
	public Set<String> getPermissionIdsByRoleIdsAndAppkey(Set<String> roleSet, String appkey) {
		Set<String> permisionIdList = null;
		try {
			Map<String, Object> dataMap = new HashMap<String, Object>();
			permisionIdList = null;
			dataMap.put("roleSet", roleSet);
			dataMap.put("appkey", appkey);
			permisionIdList = this.rolePermissionMapper.getPermissionIdsByRoleIdsAndAppkey(dataMap);
		} catch (Exception e) {
			permisionIdList = new HashSet<String>();
			logger.error(I18NMessageReader.getMessage("ptxtgl.role.getpermissionid.error"), e);
		}
		return permisionIdList;
	}
}
