package com.wdcloud.ptxtgl.role.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wdcloud.common.exception.CommonException;
import com.wdcloud.common.service.CommonGeneratorService;
import com.wdcloud.framework.core.util.I18NMessageReader;
import com.wdcloud.ptxtgl.constant.PtxtglConstant;
import com.wdcloud.ptxtgl.role.entity.Role;
import com.wdcloud.ptxtgl.role.mapper.RoleMapper;
import com.wdcloud.ptxtgl.role.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService{
	
	private static Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);
	
	@Autowired
	private RoleMapper roleMapper;
	
	@Autowired
	private CommonGeneratorService commonGeneratorService;

	/**
	 * 检查角色是否存在
	 * @param loginid
	 * @return
	 */
	@Override
	public Map<String,Object> checkRoleExist(String roleCode,String id) {
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,String> dataMap = new HashMap<String,String>();
		dataMap.put("rolecode", roleCode);
		if(StringUtils.isNotEmpty(id)){
			dataMap.put("id", id);
		}
		int count = this.roleMapper.checkRoleExist(dataMap);
		if(count >0 ){
			map.put(PtxtglConstant.RETURN_RESULT, false);
			map.put(PtxtglConstant.RETURN_MSG,I18NMessageReader.getMessage("user.rolecode.occupied"));
		}else{
			map.put(PtxtglConstant.RETURN_RESULT, true);
			map.put(PtxtglConstant.RETURN_MSG, I18NMessageReader.getMessage("user.rolecode.valid"));
		}
		return map;
	}
	
	/**
	 * 展示角色信息
	 * 
	 */
	@Override
	public Role loadRoleMesg(String id) {
		return this.roleMapper.loadRoleMesg(id);
	}
	
	/**
	 * 创建角色
	 * @param role
	 * @return
	 */
	@Override
	public Map<String,Object> createrole(Role role) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put(PtxtglConstant.RETURN_RESULT, false);
		Map<String, Object> checkMap = this.checkRoleExist(role.getRolecode(),"");
		if((Boolean) checkMap.get(PtxtglConstant.RETURN_RESULT)){
			try {
				int excuteCount = 0;
				Long versions = this.commonGeneratorService.nextVersion("t_sysmgr_role");
				String id = this.commonGeneratorService.nextPrimaryId("t_sysmgr_role");
				role.setId(id);
				role.setVersions(String.valueOf(versions));
				role.setDelflag("A");
				role.setCreatertime(new Date());			
				excuteCount = this.roleMapper.createRole(role);
				if (excuteCount > 0) {
					map.put(PtxtglConstant.RETURN_RESULT, true);
					map.put(PtxtglConstant.RETURN_MSG, I18NMessageReader.getMessage("role.add.successed"));
				} else {
					map.put(PtxtglConstant.RETURN_MSG, I18NMessageReader.getMessage("role.add.failed"));
					throw new CommonException(I18NMessageReader.getMessage("role.add.failed"));
				} 
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				throw new CommonException(e.getMessage(), e);
			}
		}else{
			return checkMap;
		}
		return map;
	}
	
	/**
	 * 删除角色
	 * @param id
	 * @return
	 */
	@Override
	public Map<String, Object> deleteroleByLogic(String ids,String operateCode) {
		List<String> list =  Arrays.asList(ids.split(","));
		Map<String,Object> map = new HashMap<String,Object>();
		int excuteNum = 0;
		try {			
			for(String roleid : list){
				Map<String,Object> dataMap = new HashMap<String,Object>();				
				Long versions = this.commonGeneratorService.nextVersion("t_sysmgr_role");
				dataMap.put("id", roleid);
				dataMap.put("delflag", "D");
				dataMap.put("operatercode", operateCode);
				dataMap.put("operatetime", new Date());
				dataMap.put("versions", versions);
				int excuteCount = this.roleMapper.deleteRoleByLogic(dataMap);
				if( excuteCount == 1){
					excuteNum ++;
				}else{
					map.put(PtxtglConstant.RETURN_MSG, I18NMessageReader.getMessage("role.delete.failed"));
					throw new CommonException(I18NMessageReader.getMessage("role.delete.failed"));
				}				
			}
			if(excuteNum == list.size()){
				map.put(PtxtglConstant.RETURN_RESULT, true);
				map.put(PtxtglConstant.RETURN_MSG, I18NMessageReader.getMessage("role.delete.successed"));
			}else{
				map.put(PtxtglConstant.RETURN_MSG, I18NMessageReader.getMessage("role.delete.failed"));
				throw new CommonException(I18NMessageReader.getMessage("role.delete.failed"));
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new CommonException(e.getMessage(), e);
		}
		return map;
	}
	
	/**
	 * 更新角色
	 * @param role
	 * @return
	 */
	@Override
	public Map<String, Object> updaterole(Role role) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put(PtxtglConstant.RETURN_RESULT,false);
		Map<String, Object> checkMap = this.checkRoleExist(role.getRolecode(),role.getId());
		if((Boolean) checkMap.get(PtxtglConstant.RETURN_RESULT)){
			int excuteCount = 0;
			try {
				long versions = this.commonGeneratorService.nextVersion("t_sysmgr_role");
				role.setDelflag("U");
				role.setOperatetime(new Date());
				role.setVersions(String.valueOf(versions));
				excuteCount = this.roleMapper.updateRole(role);
				if (excuteCount > 0) {
					map.put(PtxtglConstant.RETURN_RESULT, true);
					map.put(PtxtglConstant.RETURN_MSG, I18NMessageReader.getMessage("role.update.successed"));
				} else {
					map.put(PtxtglConstant.RETURN_MSG, I18NMessageReader.getMessage("role.update.failed"));
					throw new CommonException(I18NMessageReader.getMessage("role.update.failed"));
				} 
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				throw new CommonException(I18NMessageReader.getMessage("role.update.failed"));
			}
		}else{
			return checkMap;
		}		
		return map;
	}
	
	/**
	 * 查询角色
	 * @param role
	 * @return
	 */
	@Override
	public Map<String, Object> listroleByConditions(int pageStart, int pageSize, Role role) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put(PtxtglConstant.RETURN_RESULT,false);
		Map<String,Object> dataMap = this.listRoleConditions(pageStart,pageSize,role);
		try {
			int records = this.roleMapper.listRoleCountByConditions(dataMap);
			List<Role> rows = this.roleMapper.listRoleByConditions(dataMap);
			map.put(PtxtglConstant.RETURN_RESULT,true);
			map.put(PtxtglConstant.RETURN_RECORDS, records);
			map.put(PtxtglConstant.RETURN_ROWS, rows);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return map;
	}
	
	
	/**
	 * 返回条件查询条件map
	 * @param pageStart
	 * @param pageSize
	 * @param role
	 * @return
	 */
	private Map<String, Object> listRoleConditions(int pageStart, int pageSize, Role role) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put(PtxtglConstant.PAGE_START, pageStart);
		map.put(PtxtglConstant.PAGE_SIZE, pageSize);
		if(StringUtils.isNotEmpty(role.getRolename())){
			map.put("rolename", role.getRolename());
		}
		if(StringUtils.isNotEmpty(role.getRolecode())){
			map.put("rolecode", role.getRolecode());
		}
		if(StringUtils.isNotEmpty(role.getAppkey())){
			map.put("appkey", role.getAppkey());
		}	
		return map;
	}
}
