/**
 * @author CHENYB
 * @(#)ModuleController.java 2016年1月19日
 * 
 * wdcloud 版权所有2014~2016。
 */
package com.wdcloud.ptxtgl.module.web.controller;

import com.wdcloud.common.constant.UicConstants;
import com.wdcloud.common.exception.CommonException;
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
import com.wdcloud.ptxtgl.module.entity.Module;
import com.wdcloud.ptxtgl.module.service.ModuleService;
import com.wdcloud.ptxtgl.resource.entity.Resource;
import org.apache.commons.lang.StringUtils;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author CHENYB
 * @since 2016年1月19日
 */
@Controller
@RequestMapping("/module")
public class ModuleController {

	private static final Log logger = LogFactory.getLog(ModuleController.class);
	@Autowired
	private ModuleService moduleService;
	@Autowired
	private SessionInfoHolder sessionInfoHolder;

	/**
	 * 菜单核验规则
	 * @param response 响应对象
	 * @since 2016年2月17日 下午4:31:57
	 */
	@ResponseBody
	@RequestMapping("/validateRule")
	public void validateRule(HttpServletResponse response) {
		ValidatorModel vm = MvcValidateUtil.getValidatorModel(Module.class);
		WebUtil.renderHtml(response, vm.getRules());
	}

	/**
	 * 根据id加载菜单
	 * @param id 菜单id
	 * @return	菜单对象
	 * @since 2016年2月18日 下午2:14:34
	 */
	@ResponseBody
	@RequestMapping(value = "/load", method = RequestMethod.POST)
	public Module loadModule(@RequestParam String id) {
		return this.moduleService.getModuleById(id);
	}

	/**
	 * 查询菜单列表
	 * @param module 菜单对象
	 * @param request request 对象
	 * @return	菜单Page
	 * @since 2016年1月20日 下午5:50:15
	 */
	@ResponseBody
	@RequestMapping(value = "/list")
	public Map<String, Object> listModules(Module module, HttpServletRequest request) {
		@SuppressWarnings("unchecked")
		Page<Module> page = PageUtil.getPageForJqGrid(request);
		return this.moduleService.loadModules(page.getStart(), page.getPageSize(), module);
	}

	/**
	 * 创建菜单
	 * @param module	菜单对象
	 * @param bindingResult		绑定校验结果
	 * @param request	request 对象
	 * @return	map 返回信息
	 * @since 2016年1月20日 下午5:51:17
	 */
	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public Map<String, Object> createModule(@Validated Module module, BindingResult bindingResult, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(PtxtglConstant.RETURN_RESULT, false);
		if (StringUtils.isEmpty(module.getParentid())) {//是根菜单，每个系统只能有一个根菜单
			if (StringUtils.isNotEmpty(module.getAppkey())) {
				boolean hasRoot = this.moduleService.hasRootModule(module.getAppkey());
				if (hasRoot) {
					result.put(PtxtglConstant.RETURN_MSG, I18NMessageReader.getMessage("ptxtgl.action.module.root.exist"));
					return result;
				}
			} else {
				result.put(PtxtglConstant.RETURN_MSG, I18NMessageReader.getMessage("ptxtgl.action.module.app.unselect"));
				return result;
			}
		}
		String operaterCode = this.sessionInfoHolder.getLoginId(request);
		module.setOperatercode(operaterCode);
		module.setCreatercode(operaterCode);
		try {
			MvcValidateUtil.validate(module, bindingResult);
			result = this.moduleService.saveModule(module);
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
	 * 删除Module(批量)
	 * @param ids ,用逗号分隔
	 * @param request	请求对象
	 * @return	返回信息
	 * @since 2016年1月20日 下午6:15:43
	 */
	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public Map<String, Object> removeModules(@RequestParam String ids, HttpServletRequest request) {
		String code = this.sessionInfoHolder.getLoginId(request);
		return this.moduleService.deleteModules(ids, code);
	}

	/**
	 * 修改Module
	 * @param module 菜单对象
	 * @param bindingResult	绑定校验结果
	 * @param request	请求对象
	 * @return	返回信息
	 * @since 2016年1月20日 下午6:40:18
	 */
	@ResponseBody
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public Map<String, Object> updateModule(@Validated Module module, BindingResult bindingResult, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(PtxtglConstant.RETURN_RESULT, false);
		String code = this.sessionInfoHolder.getLoginId(request);
		module.setOperatercode(code);
		try {
			MvcValidateUtil.validate(module, bindingResult);
			result = this.moduleService.updateModule(module);
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
	 * 查询可关联的菜单资源
	 * @param resource 	资源对象
	 * @param request	请求对象
	 * @return	返回信息
	 * @since 2016年1月22日 下午5:16:47
	 */
	@ResponseBody
	@RequestMapping(value = "/listResource")
	public Map<String, Object> listAllowedResources(Resource resource, HttpServletRequest request) {
		@SuppressWarnings("unchecked")
		Page<Resource> page = PageUtil.getPageForJqGrid(request);
		return this.moduleService.loadAllowedResourcesForMenu(page.getStart(), page.getPageSize(), resource);
	}

	/**
	 * 菜单关联资源
	 * @param moduleid 菜单标识
	 * @param resourceid	资源标识
	 * @return	返回信息
	 * @since 2016年1月22日 下午6:35:55
	 */
	@ResponseBody
	@RequestMapping(value = "/related", method = RequestMethod.POST)
	public Map<String, Object> grantResource(@RequestParam String moduleid, @RequestParam String resourceid,
			HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(PtxtglConstant.RETURN_RESULT, false);
		try {
			String operaterCode = this.sessionInfoHolder.getLoginId(request);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("moduleid", moduleid);
			params.put("resourceid", resourceid);
			params.put(UicConstants.FIELD_OPERATERCODE, operaterCode);
			this.moduleService.saveResourceForMenu(params);
			result.put(PtxtglConstant.RETURN_RESULT, true);
		} catch (CommonException e) {
			result.put(PtxtglConstant.RETURN_MSG, e.getMessage());
		} catch (Exception e) {
			logger.error(I18NMessageReader.getMessage("operate.error"), e);
			result.put(PtxtglConstant.RETURN_MSG, I18NMessageReader.getMessage("ptxtgl.action.module.related.resource.error"));
		}
		return result;
	}

	/**
	 * 根据归属系统查询菜单列表
	 * @param appkey 归属系统
	 * @return	返回信息
	 * @since 2016年3月3日 下午2:27:45
	 */
	@ResponseBody
	@RequestMapping(value = "/moduleList", method = RequestMethod.POST)
	public Map<String, Object> listModulesByAppkey(@RequestParam String appkey) {
		return this.moduleService.loadModulesByAppkey(appkey);
	}

}
