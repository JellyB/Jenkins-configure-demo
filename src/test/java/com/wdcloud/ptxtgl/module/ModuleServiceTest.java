/**
 * @author CHENYB
 * @(#)ModuleServiceTest.java 2015年12月15日
 *
 * wdcloud 版权所有2014~2015。
 */
package com.wdcloud.ptxtgl.module;

import com.wdcloud.ptxtgl.module.entity.Module;
import com.wdcloud.ptxtgl.module.service.ModuleService;
import com.wdcloud.ptxtgl.resource.entity.Resource;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.Log4jConfigurer;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author CHENYB
 * @since 2015年12月15日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = { "classpath:spring/applicationContext-cache.xml",
		"classpath:spring/applicationContext-common.xml", "classpath:spring/applicationContext-dubbo.xml",
		"classpath:spring/applicationContext-security.xml", "classpath:spring/applicationContext-session.xml",
		"classpath:spring/applicationContext-springmvc.xml", "classpath:spring/applicationContext.xml",
		"classpath:applicationContext-hbase.xml" })
public class ModuleServiceTest {

	private static final Log logger = LogFactory.getLog(ModuleServiceTest.class);

	@Before
	public void initLog() {
		try {
			Log4jConfigurer.initLogging("src/main/resources/conf/log4j.properties");
		} catch (FileNotFoundException e) {
			logger.error("initLog error!", e);
		}
	}

	int pageStart = 0;
	int pageSize = 10;

	@Autowired
	private ModuleService moduleService;

	/**
	 * 测试某系统是否有根菜单
	 * @author CHENYB
	 * @since 2016年4月27日 下午9:28:22
	 */
	@Test
	public void testHasRootModule() {
		String appkey = "05";
		boolean rs = this.moduleService.hasRootModule(appkey);
		logger.info(">>>>>>>>>>>>>>>>>>>" + rs);
		println(rs);
	}

	/**
	 * 测试根据归属系统查询菜单列表
	 * @author CHENYB
	 * @since 2016年3月3日 下午3:01:38
	 */
	@Test
	public void testLoadModulesByAppkey() {
		String appkey = "01";
		Map<String,Object> rs = this.moduleService.loadModulesByAppkey(appkey);
		logger.info(">>>>>>>>>>>>>>>>>>>" + rs);
		println(rs);
	}

	/**
	 * 测试通过id加载菜单
	 * @author CHENYB
	 * @since 2016年2月18日 下午2:31:47
	 */
	@Test
	public void testGetModuleById() {
		String moduleid = "1";
		Module rs = this.moduleService.getModuleById(moduleid);
		logger.info(">>>>>>>>>>>>>>>>>>>" + rs);
		println(rs);
	}

	/**
	 * 测试加载菜单(包括条数)
	 * @author CHENYB
	 * @since 2016年1月25日 上午11:20:13
	 */
	@Test
	public void testLoadModules() {
		Module module = new Module();
		module.setModulename("管理");
		Map<String, Object> rs = this.moduleService.loadModules(pageStart, pageSize, module);
		logger.info(">>>>>>>>>>>>>>>>>>>" + rs);
	}

	/**
	 * 测试菜单关联资源
	 * @author CHENYB
	 * @since 2016年1月25日 上午10:52:33
	 */
	@Test
	public void testSaveResourceForMenu() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("moduleid", "6");
		params.put("resourceid", "13132111111111111,13333333333332");
		this.moduleService.saveResourceForMenu(params);
		logger.info(">>>>>>>>>>>>>>>>>>>");
	}

	/**
	 * 测试加载菜单可关联的资源(包括条数)
	 * @author CHENYB
	 * @since 2016年1月25日 上午10:47:12
	 */
	@Test
	public void testLoadAllowedResourcesForMenu() {
		Resource resource = new Resource();
		resource.setAppkey("12");
		Map<String, Object> rs = this.moduleService.loadAllowedResourcesForMenu(pageStart, pageSize, resource);
		logger.info(">>>>>>>>>>>>>>>>>>>" + rs);
	}

	/**
	 * 测试删除Module(批量)
	 * @author CHENYB
	 * @since 2016年1月20日 下午6:56:13
	 */
	@Test
	public void testDeleteModules() {
		String moduleids = "16,17";
		String operatercode = "junit" + getRandomNo();
		Map<String, Object> rs = this.moduleService.deleteModules(moduleids, operatercode);
		logger.info(">>>>>>>>>>>>>>>>>>>" + rs);
	}

	/**
	 * 测试修改Module
	 * @author CHENYB
	 * @since 2016年1月20日 下午6:50:23
	 */
	@Test
	public void testUpdateModule() {
		Module module = new Module();
		module.setId("1100000000000000001");
		module.setModulename("modulename" + getRandomNo());
		module.setModulecode("modulecode" + getRandomNo());
		Map<String, Object> rs = this.moduleService.updateModule(module);
		logger.info(">>>>>>>>>>>>>>>>>>>" + rs);
	}

	/**
	 * 测试创建Module
	 * @author CHENYB
	 * @since 2016年1月20日 下午6:47:07
	 */
	@Test
	public void testSaveModule() {
		Module module = new Module();
		module.setModulename("modulename" + getRandomNo());
		module.setModulecode("modulecode" + getRandomNo());
		Map<String, Object> rs = this.moduleService.saveModule(module);
		logger.info(">>>>>>>>>>>>>>>>>>>" + rs);
	}

	public static void println(Object o) {
		System.out.println(ToStringBuilder.reflectionToString(o, ToStringStyle.SHORT_PREFIX_STYLE));
	}

	private int getRandomNo() {
		return new Random().nextInt(1000);
	}

}