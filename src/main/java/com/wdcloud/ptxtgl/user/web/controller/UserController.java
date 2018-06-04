package com.wdcloud.ptxtgl.user.web.controller;

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
import com.wdcloud.ptxtgl.base.inter.Save;
import com.wdcloud.ptxtgl.base.inter.Update;
import com.wdcloud.ptxtgl.constant.PtxtglConstant;
import com.wdcloud.ptxtgl.user.entity.User;
import com.wdcloud.ptxtgl.user.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	private static Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SessionInfoHolder sessionInfoHolder;

	/**
	 * 获取管理员校验规则
	 * @param response
	 */
	@RequestMapping(value="/validateRule")
	@ResponseBody
	public void getValidateUserRule(HttpServletResponse response){
		ValidatorModel vm = MvcValidateUtil.getValidatorModel(User.class);
		WebUtil.renderHtml(response, vm.getRules());
	}
	
	/***
	 * 检查用户登录名时候被占用
	 * @param loginid
	 * @return
	 */
	@RequestMapping(value="/checkUserExist" ,method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> checkUserExist(String loginname ,String id){
		return this.userService.checkUserExist(loginname, id);
	}
	
	/**
	 * 创建用户
	 * @param user
	 * @param bindingResult
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/save" ,method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> createUser(@Validated({Save.class}) User user,BindingResult bindingResult,HttpServletRequest request){
		Map<String,Object> map = new HashMap<String,Object>();
		String createrCode = this.sessionInfoHolder.getLoginId(request);
		user.setCreatercode(createrCode);
		map.put(PtxtglConstant.RETURN_RESULT, false);
		try {
			MvcValidateUtil.validate(user, bindingResult);
			map = this.userService.createUser(user);
		} catch (ValidateException ve) {
			ValidateResult validateResult = ve.getValidateResult();
			validateResult.join("<br>");
			map.put(PtxtglConstant.RETURN_MSG, validateResult.getValidateMessage());
		} catch(Exception e){
			map.put(PtxtglConstant.RETURN_MSG, I18NMessageReader.getMessage("user.add.failed"));
			logger.error(e.getMessage(), e);
		}
		return map;
	}
	
	
	/**
	 * 根据ID返回管理源信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value="load" ,method=RequestMethod.POST)
	@ResponseBody
	public User loadUserMesg(String id){
		return this.userService.loadUserMesg(id);
	}
	
	
	/**
	 * 修改管理员信息
	 * @param user
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/update" ,method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> updateUser(@Validated(Update.class)User user, BindingResult bindingResult, HttpServletRequest request){
		Map<String,Object> map = new HashMap<String,Object>();
		String operaterCode = this.sessionInfoHolder.getLoginId(request);
		user.setOperatercode(operaterCode);
		map.put(PtxtglConstant.RETURN_RESULT, false);
		try {
			MvcValidateUtil.validate(user, bindingResult);
			map = this.userService.updateUser(user);
		} catch (ValidateException ve) {
			ValidateResult validateResult = ve.getValidateResult();
			validateResult.join("<br>");
			map.put(PtxtglConstant.RETURN_MSG, validateResult.getValidateMessage());
		} catch (Exception e) {
			map.put(PtxtglConstant.RETURN_MSG,I18NMessageReader.getMessage("user.update.failed"));
			logger.error(e.getMessage(), e);
		}
		return map;
	}
	
	/**
	 * 删除管理员（逻辑）
	 * @param id
	 * @param requeset
	 * @return
	 */
	@RequestMapping(value="/delete" ,method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> deleteUser(String ids,HttpServletRequest requeset){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put(PtxtglConstant.RETURN_RESULT, false);
		String operateCode = this.sessionInfoHolder.getLoginId(requeset);
		try {
			map = this.userService.deleteUserByLogic(ids, operateCode);
		} catch (Exception e) {
			map.put(PtxtglConstant.RETURN_MSG,I18NMessageReader.getMessage("user.delete.failed"));
			logger.error(e.getMessage(), e);
		}
		return map;
	}
	
	
	/**
	 * 返回符合查询条件的管理员列表
	 * @param user
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/list")
	@ResponseBody
	public Map<String,Object> listUserByConditions(User user,HttpServletRequest request){
		@SuppressWarnings("unchecked")
		Page<User> page = PageUtil.getPageForJqGrid(request);
		return this.userService.listUserByConditions(page.getStart(),page.getPageSize(),user);
	}
	
	/**
	 * 重置用户密码
	 * @param ids
	 * @param passwd
	 * @param confirmPasswd
	 * @return
	 */
	@RequestMapping(value="resetPasswd",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> resetPasswd(String ids, String passwd, String confirmPasswd,HttpServletRequest requeset){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put(PtxtglConstant.RETURN_RESULT, false);
		try {
			String operaterCode = this.sessionInfoHolder.getLoginId(requeset);
			map = this.userService.updatePassword(ids, passwd, confirmPasswd, operaterCode);
		} catch (Exception e) {
			map.put(PtxtglConstant.RETURN_MSG, I18NMessageReader.getMessage("user.passwd.reset.failed"));
			logger.error(e.getMessage(), e);
		}
		return map;
	}
}
