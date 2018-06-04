package com.wdcloud.ptxtgl.base.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wdcloud.framework.core.util.I18NMessageReader;
import com.wdcloud.framework.core.util.PropertyXmlMgr;
import com.wdcloud.framework.web.session.SessionInfoHolder;
import com.wdcloud.ptxtgl.base.entity.Menu;
import com.wdcloud.ptxtgl.base.service.MenuService;

@Controller
@RequestMapping("/menu")
public class MenuController {
	
	private static Logger logger = LoggerFactory.getLogger(MenuController.class);
	
	@Autowired
	private MenuService menuService;
	
	@Autowired
	private SessionInfoHolder sessionInfoHolder;
	
	
	@RequestMapping("/getUserMenus")
	@ResponseBody
	public  Menu getSystemMenus(HttpServletRequest request){
		String appkey = PropertyXmlMgr.getString("CONF_COMMON", "PTXTGL_APPKEY");
		String userid = sessionInfoHolder.getUserId(request);
		Menu menu = null;
		try {
			menu = this.menuService.getMenusByUser(userid, appkey);
		} catch (Exception e) {
			menu = new Menu();
			menu.setResult(Menu.FAILURE);
			String errMsg = I18NMessageReader.getMessage("ptxtgl.getusermenus.error");
			menu.setMsg(errMsg);
			logger.error(errMsg, e);
		}
		return menu;
	}
}
