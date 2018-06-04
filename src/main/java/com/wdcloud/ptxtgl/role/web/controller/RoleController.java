package com.wdcloud.ptxtgl.role.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wdcloud.common.util.PageUtil;
import com.wdcloud.framework.core.query.Page;
import com.wdcloud.framework.core.util.I18NMessageReader;
import com.wdcloud.framework.web.exception.ValidateException;
import com.wdcloud.framework.web.model.ValidatorModel;
import com.wdcloud.framework.web.session.SessionInfoHolder;
import com.wdcloud.framework.web.springmvc.validation.MvcValidateUtil;
import com.wdcloud.framework.web.util.WebUtil;
import com.wdcloud.framework.web.validation.entity.ValidateResult;
import com.wdcloud.ptxtgl.constant.PtxtglConstant;
import com.wdcloud.ptxtgl.role.service.RoleService;
import com.wdcloud.ptxtgl.role.entity.Role;

@Controller
@RequestMapping("/role")
public class RoleController {
	
	private static Logger logger = LoggerFactory.getLogger(RoleController.class);
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private SessionInfoHolder sessionInfoHolder;
	
	
	@RequestMapping(value="/validateRule")
	@ResponseBody
	public void getValidateUserRule(HttpServletResponse response){
		ValidatorModel vm = MvcValidateUtil.getValidatorModel(Role.class);
		WebUtil.renderHtml(response, vm.getRules());
	}
	
	/***
	 * 检查角色代码是否被占用
	 * @param roleCode
	 * @return
	 */
	@RequestMapping(value="/checkRoleExist" ,method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> checkRoleExist(String roleCode, String id){
		return this.roleService.checkRoleExist(roleCode,id);
	}
	
	/**
	 * 创建角色
	 * @param role
	 * @param bindingResult
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/save" ,method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> createrole(@Validated Role role,BindingResult bindingResult,HttpServletRequest request){
		Map<String,Object> map = new HashMap<String,Object>();
		String createrCode = this.sessionInfoHolder.getLoginId(request);
		role.setCreatercode(createrCode);
		map.put(PtxtglConstant.RETURN_RESULT, false);
		try {
			MvcValidateUtil.validate(role, bindingResult);
			map = this.roleService.createrole(role);
		} catch (ValidateException ve) {
			ValidateResult validateResult = ve.getValidateResult();
			validateResult.join("<br>");
			map.put(PtxtglConstant.RETURN_MSG, validateResult.getValidateMessage());
		} catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		return map;
	}
	
	@RequestMapping(value="load" ,method=RequestMethod.POST)
	@ResponseBody
	public Role loadRoleMesg(String id){
		return this.roleService.loadRoleMesg(id);		
	}
	
	/**
	 * 修改角色信息
	 * @param role
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/update" ,method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> updaterole(@Validated Role role,BindingResult bindingResult,HttpServletRequest request){
		Map<String,Object> map = new HashMap<String,Object>();
		String operaterCode = this.sessionInfoHolder.getLoginId(request);
		role.setOperatercode(operaterCode);
		map.put(PtxtglConstant.RETURN_RESULT, false);
		try {
			MvcValidateUtil.validate(role, bindingResult);
			map = this.roleService.updaterole(role);
		} catch (ValidateException ve) {
			ValidateResult validateResult = ve.getValidateResult();
			validateResult.join("<br>");
			map.put(PtxtglConstant.RETURN_MSG, validateResult.getValidateMessage());
		} catch (Exception e) {
			map.put(PtxtglConstant.RETURN_MSG,I18NMessageReader.getMessage("role.update.failed"));
			logger.error(e.getMessage(), e);
		}
		return map;
	}
	
	/**
	 * 删除角色（逻辑）
	 * @param id
	 * @param requeset
	 * @return
	 */
	@RequestMapping(value="/delete" ,method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> deleteRole(String ids,HttpServletRequest requeset){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put(PtxtglConstant.RETURN_RESULT, false);
		String operateCode = this.sessionInfoHolder.getLoginId(requeset);
		try {
			map = this.roleService.deleteroleByLogic(ids, operateCode);
		} catch (Exception e) {
			map.put(PtxtglConstant.RETURN_MSG,I18NMessageReader.getMessage("role.delete.failed"));
			logger.error(e.getMessage(), e);
		}
		return map;		
	}
	
	
	/**
	 * 返回符合查询条件的角色列表
	 * @param role
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/list")
	@ResponseBody
	public Map<String,Object> listRoleByConditions(Role role,HttpServletRequest request){
		@SuppressWarnings("unchecked")
		Page<Role> page = PageUtil.getPageForJqGrid(request);
		return this.roleService.listroleByConditions(page.getStart(),page.getPageSize(),role);
}
}
