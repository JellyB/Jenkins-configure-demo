package com.wdcloud.ptxtgl.userRole.service.impl;

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
import com.wdcloud.ptxtgl.base.entity.ListResult;
import com.wdcloud.ptxtgl.constant.PtxtglConstant;
import com.wdcloud.ptxtgl.role.entity.Role;
import com.wdcloud.ptxtgl.user.entity.User;
import com.wdcloud.ptxtgl.userRole.entity.UserRole;
import com.wdcloud.ptxtgl.userRole.mapper.UserRoleMapper;
import com.wdcloud.ptxtgl.userRole.service.UserRoleService;

@Service
public class UserRoleServiceImpl<T> implements UserRoleService{
	
	
	private static Logger logger = LoggerFactory.getLogger(UserRoleServiceImpl.class);
	
	@Autowired
	private UserRoleMapper userRoleMapper;
	
	
	/**
	 * 展示用户已分配角色
	 * @param userid
	 * @return
	 */
	@Override
	public Map<String, Object> listUserRolesByUserId(int pageStart, int pageSize,UserRole userRole) {
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		dataMap = this.listUserRolesConditions(pageStart,pageSize,userRole);
		int records = this.userRoleMapper.listUserRolesCountByUserId(dataMap);
		List<UserRole> rows = this.userRoleMapper.listUserRolesByUserId(dataMap);
		map.put(PtxtglConstant.RETURN_RECORDS, records);
		map.put(PtxtglConstant.RETURN_ROWS, rows);		
		return map;
	}
	
	@Override
	public Map<String, Object> listUserNotRolesByUserId(int pageStart, int pageSize,UserRole userRole) {
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		dataMap = this.listUserRolesConditions(pageStart,pageSize,userRole);
		int records = this.userRoleMapper.listUserNotRolesCountByUserId(dataMap);
		List<UserRole> rows = this.userRoleMapper.listUserNotRolesByUserId(dataMap);
		map.put(PtxtglConstant.RETURN_RECORDS, records);
		map.put(PtxtglConstant.RETURN_ROWS, rows);		
		return map;
	}
	
	
	/**
	 * 获取用户分配角色查询条件
	 * @param pageSize 
	 * @param pageStart 
	 * @param userRole
	 * @return
	 */
	private Map<String, Object> listUserRolesConditions(int pageStart, int pageSize, UserRole userRole) {
		Map<String,Object> dataMap = new HashMap<String,Object>();
		dataMap.put(PtxtglConstant.PAGE_START, pageStart);
		dataMap.put(PtxtglConstant.PAGE_SIZE,  pageSize);
		if(StringUtils.isNotEmpty(userRole.getUserid())){
			dataMap.put("userid", userRole.getUserid());
		}
		if(StringUtils.isNotEmpty(userRole.getRolecode())){
			dataMap.put("rolecode", userRole.getRolecode());
		}
		if(StringUtils.isNotEmpty(userRole.getRolename())){
			dataMap.put("rolename", userRole.getRolename());
		}
		if(StringUtils.isNotEmpty(userRole.getAppkey())){
			dataMap.put("appkey", userRole.getAppkey());
		}		
		return dataMap;
	}
	
	/**
	 * 取消用户已授权角色
	 * @author bigd
	 * @param  userid
	 * @param  roleids
	 */
	@Override
	public Map<String, Object> deleteUserRolesByUserId(String userid, String roleids) {
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> list = Arrays.asList(roleids.split(","));
		dataMap.put("userid", userid);
		dataMap.put("roleids", list);
		map.put(PtxtglConstant.RETURN_RESULT, false);
		try {
			int excuteCount = 0;
			excuteCount = this.userRoleMapper.deleteUserRolesByUserId(dataMap);
			if (excuteCount > 0) {
				map.put(PtxtglConstant.RETURN_RESULT, true);
				map.put(PtxtglConstant.RETURN_MSG, I18NMessageReader.getMessage("userrole.delete.successed"));
			} else {
				map.put(PtxtglConstant.RETURN_MSG, I18NMessageReader.getMessage("userrole.delete.failed"));
				throw new CommonException(I18NMessageReader.getMessage("userrole.delete.failed"));
			} 
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new CommonException(I18NMessageReader.getMessage("userrole.delete.failed"));
		}
		return map;
	}
	
	/**
	 * 添加用户角色
	 * @author bigd
	 * @param  userid
	 * @param  roleids
	 */	
	@Override
	public Map<String, Object> addUserRolesByUserId(String userid, String roleids) {
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		map.put(PtxtglConstant.RETURN_RESULT, false);
		List<String> list = Arrays.asList(roleids.split(","));
		dataMap.put("userid", userid);
		dataMap.put("roleids", list);		
		try {
			int excuteCount = 0;
			excuteCount = this.userRoleMapper.addUserRolesByUserId(dataMap);
			if (excuteCount > 0) {
				map.put(PtxtglConstant.RETURN_RESULT, true);
				map.put(PtxtglConstant.RETURN_MSG, I18NMessageReader.getMessage("userrole.add.successed"));
			} else {
				map.put(PtxtglConstant.RETURN_MSG, I18NMessageReader.getMessage("userrole.add.failed"));
				throw new CommonException(I18NMessageReader.getMessage("userrole.add.failed"));
			} 
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new CommonException(I18NMessageReader.getMessage("userrole.add.failed"));
		}
		return map;
	}
	
	/**
	 * 根据角色代码及应用标识返回此角色下的人员信息列表
	 * @author bigd
	 * @param rolecode
	 * @param appkey
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ListResult getUsersByRole(String rolecode, String appkey) {
		Map<String,String> dataMap = new HashMap<String,String>();
		ListResult listResult = new ListResult<T>();
		listResult.setMsg(I18NMessageReader.getMessage("ptxtgl.select.error"));
		listResult.setResult(false);
		if(StringUtils.isEmpty(rolecode) || StringUtils.isEmpty(appkey)){
			listResult.setMsg(I18NMessageReader.getMessage("userrole.conditions.error"));
			return listResult;
		}else{
			dataMap.put("rolecode", rolecode);
			dataMap.put("appkey", appkey);
			String roleid = this.userRoleMapper.getRoleidByConditions(dataMap);
			if(StringUtils.isEmpty(roleid)){
				listResult.setMsg(I18NMessageReader.getMessage("userrole.role.unexist"));
				return listResult;
			}else{
				List<User> list = this.userRoleMapper.getUsersByRole(roleid);
				if(list.size() == 0){
					listResult.setMsg(I18NMessageReader.getMessage("userrole.role.nousers"));
					return listResult;
				}else{
					listResult.setMsg(I18NMessageReader.getMessage("ptxtgl.select.success"));
					listResult.setResult(true);
					listResult.setData(list);
				}
			}			
		}
		return listResult;
	}
	
	/**
	 * 根据用户id及appkey返回指定系统下的角色列表
	 * @author bigd
	 * @param <T>
	 * @param userid
	 * @param appkey
	 * 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ListResult getRoleByUserIdAndAappkey(String userid, String appkey) {
		Map<String,String> dataMap = new HashMap<String,String>();
		ListResult listResult = new ListResult<T>();
		listResult.setMsg(I18NMessageReader.getMessage("ptxtgl.select.error"));
		listResult.setResult(false);
		if(StringUtils.isEmpty(appkey) || StringUtils.isEmpty(userid)){
			listResult.setMsg(I18NMessageReader.getMessage("userrole.conditions.error"));
			return listResult;
		}else{
			dataMap.put("userid", userid);
			dataMap.put("appkey", appkey);
			try {
				List<Role> list = this.userRoleMapper.getRolesByUserIdandAppkey(dataMap);
				if (list.size() == 0) {
					listResult.setMsg(I18NMessageReader.getMessage("ptxtgl.select.success"));
					listResult.setMsg(I18NMessageReader.getMessage("userrole.user.noroles"));
					return listResult;
				} else {
					listResult.setMsg(I18NMessageReader.getMessage("ptxtgl.select.success"));
					listResult.setResult(true);
					listResult.setData(list);
				} 
			} catch (Exception e) {
				String errMesg = I18NMessageReader.getMessage("ptxtgl.select.error");
				listResult.setMsg(errMesg);
				logger.error(errMesg, e);
			}
		}			
		return listResult;
	}

	@Override
	public Set<String> getRoleidsByUseridAndAppkey(String userid, String appkey) {
		Set<String> roleIdList = null;
		try {
			Map<String, String> dataMap = new HashMap<String, String>();
			dataMap.put("userid", userid);
			dataMap.put("appkey", appkey);
			roleIdList = this.userRoleMapper.getRoleidsByUseridAndAppkey(dataMap);
		} catch (Exception e) {
			roleIdList = new HashSet<String>();
			logger.error(I18NMessageReader.getMessage("ptxtgl.user.getroleids.error"), e);
		}
		return roleIdList;
	}
	
	
	/**
	 * 获取用户在此归属系统下分配的角色代码
	 * @author bigd
	 * @param userid
	 * @param appkey
	 * @return
	 */
	@Override
	public Set<String> getRoleCodesByUserIdAndAppkey(String userid, String appkey) {
		Set<String> roleSet = null;
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("userid", userid);
			map.put("appkey", appkey);
			roleSet = this.userRoleMapper.getRoleCodesByUserIdAndAppkey(map);
		} catch (Exception e) {
			logger.error(I18NMessageReader.getMessage("ptxtgl.user.getrolecodes.error"), e);
		}
		return roleSet;
	}
}
