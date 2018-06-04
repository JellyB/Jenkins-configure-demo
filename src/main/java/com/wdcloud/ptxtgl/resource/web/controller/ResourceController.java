/**
 * @author CHENYB
 * @(#)ResourceController.java 2016年1月19日
 * 
 * wdcloud 版权所有2014~2016。
 */
package com.wdcloud.ptxtgl.resource.web.controller;

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
import com.wdcloud.ptxtgl.resource.entity.Resource;
import com.wdcloud.ptxtgl.resource.service.ResourceService;

/**
 * @author CHENYB
 * @since 2016年1月19日
 */
@Controller
@RequestMapping("/resource")
public class ResourceController {

	private static final Log logger = LogFactory.getLog(ResourceController.class);

	@Autowired
	private ResourceService resourceService;
	@Autowired
	private SessionInfoHolder sessionInfoHolder;

	/**
	 * 资源校验规则
	 * @author CHENYB
	 * @param response
	 * @since 2016年2月17日 下午4:33:45
	 */
	@ResponseBody
	@RequestMapping("/validateRule")
	public void validateRule(HttpServletResponse response) {
		ValidatorModel vm = MvcValidateUtil.getValidatorModel(Resource.class);
		WebUtil.renderHtml(response, vm.getRules());
	}

	/**
	 * 根据id加载资源
	 * @author CHENYB
	 * @param id
	 * @return
	 * @since 2016年2月18日 下午2:10:36
	 */
	@ResponseBody
	@RequestMapping(value = "/load", method = RequestMethod.POST)
	public Resource loadResource(@RequestParam String id) {
		return this.resourceService.getResourceById(id);
	}

	/**
	 * 查询资源列表
	 * @author CHENYB
	 * @return
	 * @since 2016年1月20日 下午5:50:15
	 */
	@ResponseBody
	@RequestMapping(value = "/list")
	public Map<String, Object> listResources(Resource resource, HttpServletRequest request) {
		@SuppressWarnings("unchecked")
		Page<Resource> page = PageUtil.getPageForJqGrid(request);
		return this.resourceService.loadResources(page.getStart(), page.getPageSize(), resource);
	}

	/**
	 * 创建资源
	 * @author CHENYB
	 * @param resource
	 * @param bindingResult
	 * @param request
	 * @return
	 * @since 2016年1月21日 下午1:41:50
	 */
	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public Map<String, Object> createResource(@Validated Resource resource, BindingResult bindingResult,
			HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(PtxtglConstant.RETURN_RESULT, false);
		String operaterCode = this.sessionInfoHolder.getLoginId(request);
		resource.setOperatercode(operaterCode);
		resource.setCreatercode(operaterCode);
		try {
			MvcValidateUtil.validate(resource, bindingResult);
			result = this.resourceService.saveResource(resource);
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
	 * 删除资源(批量)
	 * @author CHENYB
	 * @param ids resourceid,用逗号分隔
	 * @param request
	 * @return
	 * @since 2016年1月21日 下午1:47:45
	 */
	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public Map<String, Object> removeResources(@RequestParam String ids, HttpServletRequest request) {
		String opratercode = this.sessionInfoHolder.getLoginId(request);
		return this.resourceService.deleteResources(ids, opratercode);
	}

	/**
	 * 修改资源
	 * @author CHENYB
	 * @param resource
	 * @param bindingResult
	 * @param request
	 * @return
	 * @since 2016年1月21日 下午2:04:15
	 */
	@ResponseBody
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public Map<String, Object> updateResource(@Validated Resource resource, BindingResult bindingResult,
			HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(PtxtglConstant.RETURN_RESULT, false);
		String operaterCode = this.sessionInfoHolder.getLoginId(request);
		resource.setOperatercode(operaterCode);
		try {
			MvcValidateUtil.validate(resource, bindingResult);
			result = this.resourceService.updateResource(resource);
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
