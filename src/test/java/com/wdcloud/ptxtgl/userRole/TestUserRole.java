package com.wdcloud.ptxtgl.userRole;

import com.alibaba.fastjson.JSON;
import com.wdcloud.ptxtgl.base.entity.ListResult;
import com.wdcloud.ptxtgl.userRole.entity.UserRole;
import com.wdcloud.ptxtgl.userRole.service.UserRoleService;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Map;
import java.util.Set;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:spring/applicationContext.xml",
		                           "classpath:spring/applicationContext-dubbo.xml",
		                           "classpath:spring/applicationContext-cache.xml",		                           
		                           "classpath:spring/applicationContext-session.xml",
		                           "classpath:spring/applicationContext-security.xml",
		                           "classpath:spring/applicationContext-springmvc.xml"
		                           })
public class TestUserRole {
	
	private Logger logger = LoggerFactory.getLogger(TestUserRole.class);
	
	
	@Autowired
	private UserRoleService userRoleService;
	
	@Before
	public void setUp() throws Exception {
		PropertyConfigurator.configure("src/test/resources/conf/log4j.properties");
	}
	
	/**
	 * test function : addUserRolesByUserId
	 */
	@Test
	public void testAddUserRoles(){
		String roleids = "1100000000000000031,1100000000000000033,1100000000000000034,"
				+ "1100000000000000035,1100000000000000036,1100000000000000037,"
				+ "1100000000000000038,1100000000000000039";
		String userid = "1100000000000000086";
		Map<String,Object> map = this.userRoleService.addUserRolesByUserId(userid, roleids);
		logger.info(JSON.toJSONString(map));
	}
	
	/**
	 * test function: listUserRolesByUserId
	 */
	@Test
	public void listUserRolesByUserId(){
		String userid = "1100000000000000086";
		UserRole userRole = new UserRole();
		userRole.setUserid(userid);
		userRole.setRolename("");
		Map<String,Object> map = this.userRoleService.listUserRolesByUserId(0, 10, userRole);
		logger.info(JSON.toJSONString(map));
	}
	
	/**
	 * test function : listUserNotRolesByUserId
	 */
	@Test
	public void listUserNotRolesByUserId(){ 
		String userid = "1100000000000000086";
		UserRole userRole = new UserRole();
		userRole.setUserid(userid);
		userRole.setRolename("");
		Map<String,Object> map = this.userRoleService.listUserNotRolesByUserId(0, 10, userRole);
		logger.info(JSON.toJSONString(map));
	}
	
	
	/**
	 * test function : deleteUserRolesByUserId
	 */
	@Test
	public void deleteUserRolesByUserId(){
		String roleids = "1100000000000000031,1100000000000000033,1100000000000000034";
		String userid = "1100000000000000086";
		Map<String,Object> map = this.userRoleService.deleteUserRolesByUserId(userid, roleids);
		logger.info(JSON.toJSONString(map));
	}
	
	@Test
	public void  getUsersByRole(){
		String rolecode = "Role_01221928038";
		String appkey = "000001";
		@SuppressWarnings("rawtypes")
		ListResult map = this.userRoleService.getUsersByRole(rolecode, appkey);
		logger.info(JSON.toJSONString(map));
	}
	
	@Test
	public void getRolesByUserIdandAppkey(){
		String userid = "1100000000000000092";
		String appkey = "000001";
		@SuppressWarnings("rawtypes")
		ListResult map = this.userRoleService.getRoleByUserIdAndAappkey(userid, appkey);
		logger.error("#######################");
		logger.info(JSON.toJSONString(map));
	}
	
	@Test
	public void getRoleidsByUserIdsAndAppkey(){
		String userid = "1100000000000000092";
		String appkey = "01";
		Set<String> roleIds = this.userRoleService.getRoleidsByUseridAndAppkey(userid, appkey);
		logger.error("#######################");
		logger.info(JSON.toJSONString(roleIds));
	}
}
