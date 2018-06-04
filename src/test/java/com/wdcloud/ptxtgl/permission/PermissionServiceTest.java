/**
 * @author CHENYB
 * @(#)PermissionServiceTest.java 2015年12月15日
 * 
 * wdcloud 版权所有2014~2015。
 */
package com.wdcloud.ptxtgl.permission;

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

import com.wdcloud.ptxtgl.permission.entity.Permission;
import com.wdcloud.ptxtgl.permission.service.PermissionService;

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
public class PermissionServiceTest {

	private static final Log logger = LogFactory.getLog(PermissionServiceTest.class);

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
	private PermissionService permissionService;

	/**
	 * 测试通过id加载权限
	 * @author CHENYB
	 * @since 2016年2月18日 下午2:20:46
	 */
	@Test
	public void testGetPermissionById() {
		String permissionid = "1100000000000000006";
		Permission rs = this.permissionService.getPermissionById(permissionid);
		logger.info(">>>>>>>>>>>>>>>>>>>" + rs);
		println(rs);
	}

	/**
	 * 测试加载权限(包括条数)
	 * @author CHENYB
	 * @since 2016年1月25日 上午11:50:38
	 */
	@Test
	public void testLoadPermissions() {
		Permission permission = new Permission();
		permission.setPermissionname("permissionname");
		Map<String, Object> rs = this.permissionService.loadPermissions(pageStart, pageSize, permission);
		logger.info(">>>>>>>>>>>>>>>>>>>" + rs);
	}

	/**
	 * 测试修改权限
	 * @author CHENYB
	 * @since 2016年1月20日 下午5:46:15
	 */
	@Test
	public void testUpdatePermission() {
		Permission permission = new Permission();
		permission.setId("1100000000000000008");
		permission.setPermissionname("permissionname" + getRandomNo());
		permission.setPermissioncode("permissioncode" + getRandomNo());
		Map<String, Object> rs = this.permissionService.updatePermission(permission);
		logger.info(">>>>>>>>>>>>>>>>>>>" + rs);
	}

	/**
	 * 测试删除权限(批量)
	 * @author CHENYB
	 * @since 2016年1月20日 下午5:42:29
	 */
	@Test
	public void testDeletePermissions() {
		String permissionids = "1100000000000000006,1100000000000000011";
		String operatercode = "junit" + getRandomNo();
		Map<String, Object> rs = this.permissionService.deletePermissions(permissionids, operatercode);
		logger.info(">>>>>>>>>>>>>>>>>>>" + rs);
	}

	/**
	 * 测试添加权限
	 * @author CHENYB
	 * @since 2016年1月20日 下午3:36:24
	 */
	@Test
	public void testSavePermission() {
		Permission permission = new Permission();
		permission.setPermissionname("permissionname" + getRandomNo());
		permission.setPermissioncode("permissioncode" + getRandomNo());
		Map<String, Object> rs = this.permissionService.savePermission(permission);
		logger.info(">>>>>>>>>>>>>>>>>>>" + rs);
	}

	public static void println(Object o) {
		System.out.println(ToStringBuilder.reflectionToString(o, ToStringStyle.SHORT_PREFIX_STYLE));
	}

	private int getRandomNo() {
		return new Random().nextInt(1000);
	}

}