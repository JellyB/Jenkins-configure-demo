package com.wdcloud.ptxtgl.userRole.web.controller;

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
import com.wdcloud.ptxtgl.userRole.entity.UserRole;
import com.wdcloud.ptxtgl.userRole.service.UserRoleService;

@Controller
@RequestMapping("/userRole")
public class UserRoleController {
	
	private static Logger logger = LoggerFactory.getLogger(UserRoleController.class);
	
	@Autowired
	private UserRoleService userRoleService;
	
	
	/**
	 * 展示用户已分配权限
	 * @param userid
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/relatedList")
	@ResponseBody
	public Map<String,Object> listUserRoles(UserRole userRole,HttpServletRequest request){
		@SuppressWarnings("unchecked")
		Page<UserRole> page = PageUtil.getPageForJqGrid(request);
		return this.userRoleService.listUserRolesByUserId(page.getStart(),page.getPageSize(),userRole);
	}
	
	/**
	 * 展示用户未分配权限列表
	 * @param userRole
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/unRelatedList")
	@ResponseBody
	public Map<String,Object> listUserNotRoles(UserRole userRole,HttpServletRequest request){
		@SuppressWarnings("unchecked")
		Page<UserRole> page = PageUtil.getPageForJqGrid(request);
		return this.userRoleService.listUserNotRolesByUserId(page.getStart(),page.getPageSize(),userRole);
	}
	
	/**
	 * 取消用户授权
	 * @param userid
	 * @param roleids
	 * @return
	 */
	@RequestMapping(value="/unRelated" ,method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> deleteUserRoles(String userid,String roleids){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put(PtxtglConstant.RETURN_RESULT, false);
		String roleIds[] = roleids.split(",");
		if(roleIds.length > 0){
			try {
				map = this.userRoleService.deleteUserRolesByUserId(userid, roleids);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}else{
			map.put(PtxtglConstant.RETURN_MSG, I18NMessageReader.getMessage("userrole.data.empty"));
			return map;
		}		
		return map;
	}
	
	
	/**
	 * 添加用户授权
	 * @param userid
	 * @param roleids
	 * @return
	 */
	@RequestMapping(value="/related" ,method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> addUserRoles(String userid,String roleids){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put(PtxtglConstant.RETURN_RESULT, false);
		String roleIds[] = roleids.split(",");
		if(roleIds.length > 0){
			try {
				map = this.userRoleService.addUserRolesByUserId(userid, roleids);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}else{
			map.put(PtxtglConstant.RETURN_MSG, I18NMessageReader.getMessage("userrole.data.empty"));
			return map;
		}		
		return map;
	}
}
