/**
 * @author CHENYB
 * @(#)PermissionServiceImp.java 2016年1月19日
 * 
 * wdcloud 版权所有2014~2016。
 */
package com.wdcloud.ptxtgl.permission.service.impl;

import com.wdcloud.common.constant.UicConstants;
import com.wdcloud.common.exception.CommonException;
import com.wdcloud.common.service.CommonGeneratorService;
import com.wdcloud.framework.core.util.I18NMessageReader;
import com.wdcloud.ptxtgl.constant.PtxtglConstant;
import com.wdcloud.ptxtgl.permission.entity.Permission;
import com.wdcloud.ptxtgl.permission.mapper.PermissionMapper;
import com.wdcloud.ptxtgl.permission.service.PermissionService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author CHENYB
 * @since 2016年1月19日
 */
public class PermissionServiceImp implements PermissionService {

	private static final Log logger = LogFactory.getLog(PermissionServiceImp.class);

	@Autowired
	private PermissionMapper permissionMapper;
	@Autowired
	private CommonGeneratorService commonGeneratorService;

	/**
	 * @author CHENYB
	 * @see com.wdcloud.ptxtgl.permission.service.PermissionService#getPermissionById(java.lang.String)
	 * @since 2016年2月18日 下午2:06:13
	 */
	@Override
	public Permission getPermissionById(String permissionid) {
		return this.permissionMapper.selectPermissionById(permissionid);
	}

	/**
	 * @author CHENYB
	 * @see com.wdcloud.ptxtgl.permission.service.PermissionService#loadPermissions(int, int, com.wdcloud.ptxtgl.permission.entity.Permission)
	 * @since 2016年1月25日 上午11:39:10
	 */
	@Override
	public Map<String, Object> loadPermissions(int pageStart, int pageSize, Permission permission) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> params = this.assemblePermissionConditions(pageStart, pageSize, permission);
		int records = this.permissionMapper.selectPermissionsCount(params);
		List<Permission> rows = this.permissionMapper.selectPermissions(params);
		result.put(PtxtglConstant.RETURN_RECORDS, records);
		result.put(PtxtglConstant.RETURN_ROWS, rows);
		return result;
	}

	/**
	 * 组装权限查询条件
	 * @author CHENYB
	 * @param pageStart
	 * @param pageSize
	 * @param permission
	 * @return
	 * @since 2016年1月25日 上午11:40:10
	 */
	private Map<String, Object> assemblePermissionConditions(int pageStart, int pageSize, Permission permission) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(PtxtglConstant.PAGE_START, pageStart);
		params.put(PtxtglConstant.PAGE_SIZE, pageSize);
		if (StringUtils.isNotEmpty(permission.getPermissionname())) {
			params.put("permissionname", permission.getPermissionname());
		}
		if (StringUtils.isNotEmpty(permission.getPermissioncode())) {
			params.put("permissioncode", permission.getPermissioncode());
		}
		if (StringUtils.isNotEmpty(permission.getPermissiontype())) {
			params.put("permissiontype", permission.getPermissiontype());
		}
		if (StringUtils.isNotEmpty(permission.getAppkey())) {
			params.put("appkey", permission.getAppkey());
		}
		return params;
	}

	/**
	 * @author CHENYB
	 * @see com.wdcloud.ptxtgl.permission.service.PermissionService#savePermission(com.wdcloud.ptxtgl.permission.entity.Permission)
	 * @since 2016年1月20日 下午3:06:23
	 */
	@Override
	public Map<String, Object> savePermission(Permission permission) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(PtxtglConstant.RETURN_RESULT, false);
		if (this.permissionCodeExist(permission.getPermissioncode(), permission.getId())) {
			result.put(PtxtglConstant.RETURN_MSG, I18NMessageReader.getMessage("ptxtgl.action.permission.permissioncode.exist", permission.getPermissioncode()));
			return result;
		}
		Date date = new Date();
		int rs = 0;
		try {
			permission.setDelflag("A");
			permission.setCreatertime(date);
			permission.setOperatetime(date);
			Long version = this.commonGeneratorService.nextVersion("t_sysmgr_permission");
			String id = this.commonGeneratorService.nextPrimaryId("t_sysmgr_permission");
			permission.setId(id);
			permission.setVersions(UicConstants.getStringFromObject(version));
			rs = this.permissionMapper.insertPermission(permission);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			if (rs == 0) {
				result.put(PtxtglConstant.RETURN_MSG, I18NMessageReader.getMessage("ptxtgl.action.permission.create.error"));
				return result;
			}
			throw new CommonException(I18NMessageReader.getMessage("operate.error"));
		}
		result.put(PtxtglConstant.RETURN_RESULT, true);
		return result;
	}

	/**
	 * @author CHENYB
	 * @see com.wdcloud.ptxtgl.permission.service.PermissionService#permissionCodeExist(java.lang.String, java.lang.String)
	 * @since 2016年1月22日 上午9:53:30
	 */
	@Override
	public boolean permissionCodeExist(String permissioncode, String permissionid) {
		int count = this.permissionMapper.getPermissionCodeCount(permissioncode, permissionid);
		return count > 0;
	}

	/**
	 * @author CHENYB
	 * @see com.wdcloud.ptxtgl.permission.service.PermissionService#deletePermissions(java.lang.String, java.lang.String)
	 * @since 2016年1月20日 下午4:17:26
	 */
	@Override
	public Map<String, Object> deletePermissions(String permissionids, String opratercode) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(PtxtglConstant.RETURN_RESULT, false);
		if (StringUtils.isBlank(permissionids)) {
			result.put(PtxtglConstant.RETURN_MSG, I18NMessageReader.getMessage("ptxtgl.action.delete.unselect"));
			return result;
		}
		String[] ids = permissionids.split(",");
		try {
			for (int i = 0; i < ids.length; i++) {
				this.deletePermissionById(ids[i], opratercode);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CommonException(I18NMessageReader.getMessage("operate.error"));
		}
		result.put(PtxtglConstant.RETURN_RESULT, true);
		return result;
	}

	/**
	 * 根据id删除权限
	 * @author CHENYB
	 * @param permissionid
	 * @param opratercode
	 * @return
	 * @since 2016年1月25日 下午2:51:34
	 */
	private void deletePermissionById(String permissionid, String opratercode) throws Exception {
		try {
			Permission permission = this.permissionMapper.selectPermissionById(permissionid);
			if (permission == null) {
				return;
			}
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("permissionid", permissionid);
			params.put(UicConstants.FIELD_DELFLAG, "D");
			params.put(UicConstants.FIELD_OPERATERCODE, opratercode);
			params.put(UicConstants.FIELD_OPERATETIME, new Date());
			params.put(UicConstants.FIELD_VERSIONS, this.commonGeneratorService.nextVersion("t_sysmgr_permission"));
			this.permissionMapper.deletePermissionById(params);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new CommonException(I18NMessageReader.getMessage("operate.error"));
		}
	}

	/**
	 * @author CHENYB
	 * @see com.wdcloud.ptxtgl.permission.service.PermissionService#updatePermission(com.wdcloud.ptxtgl.permission.entity.Permission)
	 * @since 2016年1月20日 下午5:21:17
	 */
	@Override
	public Map<String, Object> updatePermission(Permission permission) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(PtxtglConstant.RETURN_RESULT, false);
		int rs = 0;
		try {
			Permission perm = this.permissionMapper.selectPermissionById(permission.getId());
			if (perm == null) {
				result.put(PtxtglConstant.RETURN_MSG, I18NMessageReader.getMessage("ptxtgl.action.permission.notexist"));
				return result;
			}
			if (this.permissionCodeExist(permission.getPermissioncode(), permission.getId())) {
				result.put(PtxtglConstant.RETURN_MSG, I18NMessageReader.getMessage("ptxtgl.action.permission.permissioncode.duplicate", permission.getPermissioncode()));
				return result;
			}
			permission.setDelflag("U");
			permission.setOperatetime(new Date());
			Long version = this.commonGeneratorService.nextVersion("t_sysmgr_permission");
			permission.setVersions(UicConstants.getStringFromObject(version));
			rs = this.permissionMapper.updatePermission(permission);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			if (rs == 0) {
				result.put(PtxtglConstant.RETURN_MSG, I18NMessageReader.getMessage("ptxtgl.action.permission.update.error"));
				return result;
			}
			throw new CommonException(I18NMessageReader.getMessage("operate.error"));
		}
		result.put(PtxtglConstant.RETURN_RESULT, true);
		return result;
	}

}
