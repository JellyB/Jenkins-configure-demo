/**
 * @author CHENYB
 * @(#)PermissionController.java 2016年1月19日
 * 
 * wdcloud 版权所有2014~2016。
 */
package com.wdcloud.ptxtgl.permission.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wdcloud.common.util.PageUtil;
import com.wdcloud.framework.core.query.Page;
import com.wdcloud.framework.web.exception.ValidateException;
import com.wdcloud.framework.web.model.ValidatorModel;
import com.wdcloud.framework.web.session.SessionInfoHolder;
import com.wdcloud.framework.web.springmvc.validation.MvcValidateUtil;
import com.wdcloud.framework.web.util.WebUtil;
import com.wdcloud.framework.web.validation.entity.ValidateResult;
import com.wdcloud.ptxtgl.constant.PtxtglConstant;
import com.wdcloud.ptxtgl.permission.entity.Permission;
import com.wdcloud.ptxtgl.permission.service.PermissionService;

/**
 * @author CHENYB
 * @since 2016年1月19日
 */
@Controller
@RequestMapping("/permission")
public class PermissionController {

	private static final Log logger = LogFactory.getLog(PermissionController.class);

	@Autowired
	private PermissionService permissionService;
	@Autowired
	private SessionInfoHolder sessionInfoHolder;

	/**
	 * 权限校验规则
	 * @author CHENYB
	 * @param response
	 * @since 2016年2月17日 下午4:33:08
	 */
	@ResponseBody
	@RequestMapping("/validateRule")
	public void validateRule(HttpServletResponse response) {
		ValidatorModel vm = MvcValidateUtil.getValidatorModel(Permission.class);
		WebUtil.renderHtml(response, vm.getRules());
	}

	/**
	 * 根据id加载权限
	 * @author CHENYB
	 * @param id
	 * @return
	 * @since 2016年2月18日 下午2:06:40
	 */
	@ResponseBody
	@RequestMapping(value = "/load", method = RequestMethod.POST)
	public Permission loadPermission(@RequestParam String id) {
		return this.permissionService.getPermissionById(id);
	}

	/**
	 * 查询权限列表
	 * @author CHENYB
	 * @param permission
	 * @param request
	 * @return
	 * @since 2016年1月20日 下午5:37:48
	 */
	@ResponseBody
	@RequestMapping(value = "/list")
	public Map<String, Object> listPermissions(Permission permission, HttpServletRequest request) {
		@SuppressWarnings("unchecked")
		Page<Permission> page = PageUtil.getPageForJqGrid(request);
		return this.permissionService.loadPermissions(page.getStart(), page.getPageSize(), permission);
	}

	/**
	 * 创建权限
	 * @author CHENYB
	 * @param permission
	 * @param bindingResult
	 * @param request
	 * @return
	 * @since 2016年1月20日 下午3:08:35
	 */
	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public Map<String, Object> createPermission(@Validated Permission permission, BindingResult bindingResult,
			HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(PtxtglConstant.RETURN_RESULT, false);
		String operaterCode = this.sessionInfoHolder.getLoginId(request);
		permission.setOperatercode(operaterCode);
		permission.setCreatercode(operaterCode);
		try {
			MvcValidateUtil.validate(permission, bindingResult);
			result = this.permissionService.savePermission(permission);
		} catch (ValidateException ve) {
			ValidateResult validateResult = ve.getValidateResult();
			validateResult.join("<br>");
			result.put(PtxtglConstant.RETURN_MSG, validateResult.getValidateMessage());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return result;
	}

	/**
	 * 删除权限(批量)
	 * @author CHENYB
	 * @param ids permissionid,用逗号分隔
	 * @param request
	 * @return
	 * @since 2016年1月20日 下午3:57:10
	 */
	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public Map<String, Object> removePermissions(@RequestParam String ids, HttpServletRequest request) {
		String opratercode = this.sessionInfoHolder.getLoginId(request);
		return this.permissionService.deletePermissions(ids, opratercode);
	}

	/**
	 * 修改权限
	 * @author CHENYB
	 * @param permission
	 * @param bindingResult
	 * @param request
	 * @return
	 * @since 2016年1月20日 下午5:20:09
	 */
	@ResponseBody
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public Map<String, Object> updatePermission(@Validated Permission permission, BindingResult bindingResult,
			HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(PtxtglConstant.RETURN_RESULT, false);
		String operaterCode = this.sessionInfoHolder.getLoginId(request);
		permission.setOperatercode(operaterCode);
		try {
			MvcValidateUtil.validate(permission, bindingResult);
			result = this.permissionService.updatePermission(permission);
		} catch (ValidateException ve) {
			ValidateResult validateResult = ve.getValidateResult();
			validateResult.join("<br>");
			result.put(PtxtglConstant.RETURN_MSG, validateResult.getValidateMessage());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return result;
	}

}
