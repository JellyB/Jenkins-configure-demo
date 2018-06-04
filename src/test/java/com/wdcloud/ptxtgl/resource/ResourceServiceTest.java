/**
 * @author CHENYB
 * @(#)ResourceServiceTest.java 2015年12月15日
 * 
 * wdcloud 版权所有2014~2015。
 */
package com.wdcloud.ptxtgl.resource;

import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Random;

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

import com.wdcloud.ptxtgl.resource.entity.Resource;
import com.wdcloud.ptxtgl.resource.service.ResourceService;

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
public class ResourceServiceTest {

	private static final Log logger = LogFactory.getLog(ResourceServiceTest.class);

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
	private ResourceService resourceService;

	/**
	 * 测试通过id加载资源
	 * @author CHENYB
	 * @since 2016年2月18日 下午2:29:09
	 */
	@Test
	public void testGetResourceById() {
		String resourceid = "12";
		Resource rs = this.resourceService.getResourceById(resourceid);
		logger.info(">>>>>>>>>>>>>>>>>>>" + rs);
		println(rs);
	}

	/**
	 * 测试加载资源(包括条数)
	 * @author CHENYB
	 * @since 2016年1月25日 下午12:54:33
	 */
	@Test
	public void testLoadResources() {
		Resource resource = new Resource();
		resource.setAppkey("12");
		Map<String, Object> rs = this.resourceService.loadResources(pageStart, pageSize, resource);
		logger.info(">>>>>>>>>>>>>>>>>>>" + rs);
	}

	/**
	 * 测试删除资源(批量)
	 * @author CHENYB
	 * @since 2016年1月21日 下午2:24:44
	 */
	@Test
	public void testDeleteResources() {
		String resourceids = "12,14";
		String operatercode = "junit" + getRandomNo();
		Map<String, Object> rs = this.resourceService.deleteResources(resourceids, operatercode);
		logger.info(">>>>>>>>>>>>>>>>>>>" + rs);
	}

	/**
	 * 测试修改资源
	 * @author CHENYB
	 * @since 2016年1月21日 下午2:20:16
	 */
	@Test
	public void testUpdateResource() {
		Resource resource = new Resource();
		resource.setId("1100000000000000001");
		resource.setResourcename("resourcename" + getRandomNo());
		resource.setResourcecode("resourcecode" + getRandomNo());
		resource.setResourcetype("x");
		Map<String, Object> rs = this.resourceService.updateResource(resource);
		logger.info(">>>>>>>>>>>>>>>>>>>" + rs);
	}

	/**
	 * 测试创建资源
	 * @author CHENYB
	 * @since 2016年1月21日 下午2:15:45
	 */
	@Test
	public void testSaveResource() {
		Resource resource = new Resource();
		resource.setResourcename("resourcename" + getRandomNo());
		resource.setResourcecode("resourcecode" + getRandomNo());
		resource.setResourcetype("x");
		Map<String, Object> rs = this.resourceService.saveResource(resource);
		logger.info(">>>>>>>>>>>>>>>>>>>" + rs);
	}

	public static void println(Object o) {
		System.out.println(ToStringBuilder.reflectionToString(o, ToStringStyle.SHORT_PREFIX_STYLE));
	}

	private int getRandomNo() {
		return new Random().nextInt(1000);
	}

}