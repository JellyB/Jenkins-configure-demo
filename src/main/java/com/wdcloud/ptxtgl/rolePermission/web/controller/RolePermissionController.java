package com.wdcloud.ptxtgl.rolePermission.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wdcloud.common.util.PageUtil;
import com.wdcloud.framework.core.query.Page;
import com.wdcloud.framework.core.util.I18NMessageReader;
import com.wdcloud.ptxtgl.constant.PtxtglConstant;
import com.wdcloud.ptxtgl.rolePermission.entity.RolePermission;
import com.wdcloud.ptxtgl.rolePermission.service.RolePermissionService;

@Controller
@RequestMapping("/rolePermission")
public class RolePermissionController {
	
	private static Logger logger = LoggerFactory.getLogger(RolePermissionController.class);
	
	@Autowired
	private RolePermissionService rolePermissionService;
	
	
	/**
	 * 展示角色已分配权限
	 * @param roleid
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/relatedList")
	@ResponseBody
	public Map<String,Object> listRolePermissions(RolePermission rolePermission,HttpServletRequest request){
		@SuppressWarnings("unchecked")
		Page<RolePermission> page = PageUtil.getPageForJqGrid(request);
		return this.rolePermissionService.listRolePermissionsByRoleId(page.getStart(),page.getPageSize(),rolePermission);
	}
	
	/**
	 * 展示角色未分配权限列表
	 * @param rolePermission
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/unRelatedList")
	@ResponseBody
	public Map<String,Object> listRoleNotPermissions(RolePermission rolePermission,HttpServletRequest request){
		@SuppressWarnings("unchecked")
		Page<RolePermission> page = PageUtil.getPageForJqGrid(request);
		return this.rolePermissionService.listRoleNotPermissionsByRoleId(page.getStart(),page.getPageSize(),rolePermission);
	}
	
	/**
	 * 添加角色权限
	 * @param roleid
	 * @param permissionids
	 * @return
	 */
	@RequestMapping(value="/related" ,method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> addRolePermissions(String roleid,String permissionids){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put(PtxtglConstant.RETURN_RESULT, false);
		String permissionIds[] = permissionids.split(",");
		if(permissionIds.length > 0){
			try {
				map = this.rolePermissionService.addRolePermissionsByRoleId(roleid, permissionids);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}else{
			map.put(PtxtglConstant.RETURN_MSG, I18NMessageReader.getMessage("rolepermission.data.empty"));
			return map;
		}		
		return map;
	}
	
	
	/**
	 * 取消角色授权
	 * @param roleid
	 * @param permissionids
	 * @return
	 */
	@RequestMapping(value="/unRelated" ,method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> deleteRolePermissions(String roleid,String permissionids){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put(PtxtglConstant.RETURN_RESULT, false);
		String permissionIds[] = permissionids.split(",");
		if(permissionIds.length > 0){
			try {
				map = this.rolePermissionService.deleteRolePermissionsByRoleId(roleid, permissionids);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}else{
			map.put(PtxtglConstant.RETURN_MSG, I18NMessageReader.getMessage("rolepermission.data.empty"));
			return map;
		}		
		return map;
	}
	
	
	/**
	 * 添加角色权限
	 * @param roleid
	 * @param permissionids
	 * @return
	 */
	/*@RequestMapping("/dealRolePermissions")
	@ResponseBody
	public Map<String,Object> dealRolePermissionsDatas(String roleid,String permissionids){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put(PtxtglConstant.RETURN_RESULT, false);
		String permissionIds[] = permissionids.split(",");
		if(permissionIds.length > 0){
			try {
				map = this.rolePermissionService.dealRolePermissionDatas(roleid, permissionids);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}else{
			map.put(PtxtglConstant.RETURN_MSG, I18NMessageReader.getMessage("rolepermission.data.empty"));
			return map;
		}		
		return map;
	}*/	
}
