/**
 * @author CHENYB
 * @(#)PermissionResourceServiceTest.java 2015年12月15日
 * 
 * wdcloud 版权所有2014~2015。
 */
package com.wdcloud.ptxtgl.permissionResource;

import java.io.FileNotFoundException;
import java.util.Map;

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

import com.wdcloud.ptxtgl.permissionResource.entity.PermissionResource;
import com.wdcloud.ptxtgl.permissionResource.service.PermissionResourceService;

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
public class PermissionResourceServiceTest {

	private static final Log logger = LogFactory.getLog(PermissionResourceServiceTest.class);

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
	private PermissionResourceService permissionResourceService;

	/**
	 * 测试加载权限未关联的资源(包括条数)
	 * @author CHENYB
	 * @since 2016年1月22日 下午4:00:40
	 */
	@Test
	public void testLoadRemainedResources() {
		PermissionResource permissionResource = new PermissionResource();
		permissionResource.setPermissionid("222");
		Map<String, Object> rs = this.permissionResourceService.loadRemainedResources(pageStart, pageSize, permissionResource);
		logger.info(">>>>>>>>>>>>>>>>>>>" + rs);
	}

	/**
	 * 测试加载权限已关联的资源(包括条数)
	 * @author CHENYB
	 * @since 2016年1月22日 下午3:32:11
	 */
	@Test
	public void testLoadGrantedResources() {
		PermissionResource permissionResource = new PermissionResource();
		permissionResource.setPermissionid("222");
		Map<String, Object> rs = this.permissionResourceService.loadGrantedResources(pageStart, pageSize, permissionResource);
		logger.info(">>>>>>>>>>>>>>>>>>>" + rs);
	}

	/**
	 * 测试删除某权限下的资源
	 * @author CHENYB
	 * @since 2016年1月21日 下午7:11:56
	 */
	@Test
	public void testDeleteResourcesFromPermission() {
		String permissionid = "1100000000000000008";
		String resourceids = "1100000000000000001,1100000000000000002,1100000000000000003";
		Map<String, Object> rs = this.permissionResourceService.deleteResourcesFromPermission(permissionid, resourceids);
		logger.info(">>>>>>>>>>>>>>>>>>>" + rs);
	}

	/**
	 * 测试给某权限分配资源
	 * @author CHENYB
	 * @since 2016年1月21日 下午7:11:56
	 */
	@Test
	public void testSaveResourcesForPermission() {
		String permissionid = "1100000000000000008";
		String resourceids = "1100000000000000001,1100000000000000002,1100000000000000003,1100000000000000004,1100000000000000005";
		Map<String, Object> rs = this.permissionResourceService.saveResourcesForPermission(permissionid, resourceids);
		logger.info(">>>>>>>>>>>>>>>>>>>" + rs);
	}

	public static void println(Object o) {
		System.out.println(ToStringBuilder.reflectionToString(o, ToStringStyle.SHORT_PREFIX_STYLE));
	}

}