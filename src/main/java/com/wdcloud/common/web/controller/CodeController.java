package com.wdcloud.common.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wdcloud.framework.core.util.I18NMessageReader;
import com.wdcloud.zyzx.zyxx.integration.SysCodeIntegration;


/**
 * ClassName:CodeController <br/>
 * Date:     2015年12月8日 下午8:44:40 <br/>
 * @author   bigd
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
@Controller
@RequestMapping("/code")
public class CodeController {
	
	static Logger logger = LoggerFactory.getLogger(CodeController.class);
	
	@Autowired
	private SysCodeIntegration sysCodeIntegration;
	
	
	/**
	 * 获取码表
	 * @return
	 * @author bigd
	 */
	@RequestMapping(value="/getCodeMapByCodeClassVersion",method=RequestMethod.POST)
	@ResponseBody
	public Object getCodeMapByCodeClassVersion(String clv){
		String lang = I18NMessageReader.getLanguage();
		return this.sysCodeIntegration.getCodeMapByCodeClassVersion(clv,lang);
	}
}