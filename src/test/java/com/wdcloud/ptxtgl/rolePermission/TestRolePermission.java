package com.wdcloud.ptxtgl.rolePermission;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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

import com.alibaba.fastjson.JSON;
import com.wdcloud.ptxtgl.rolePermission.entity.RolePermission;
import com.wdcloud.ptxtgl.rolePermission.service.RolePermissionService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:spring/applicationContext.xml",
							        "classpath:spring/applicationContext-dubbo.xml",
							        "classpath:spring/applicationContext-cache.xml",		                           
							        "classpath:spring/applicationContext-session.xml",
							        "classpath:spring/applicationContext-security.xml",
							        "classpath:spring/applicationContext-common.xml",
							        "classpath:spring/applicationContext-springmvc.xml",
							        "classpath:applicationContext-hbase.xml"
							        })
public class TestRolePermission {
	
	private Logger logger = LoggerFactory.getLogger(TestRolePermission.class);
	
	
	@Autowired
	private RolePermissionService rolePermissionService;
	@Before
	public void setUp() throws Exception {
		PropertyConfigurator.configure("src/test/resources/conf/log4j.properties");
	}
	
	
	@Test
	public void getPermissiondIdsByRoleSetAndAppkey(){
		Set<String> roleSet = new HashSet<String>();
		roleSet.add("1100000000000000021");
		roleSet.add("1100000000000000038");
		roleSet.add("1100000000000000037");
		roleSet.add("1100000000000000039");
		String appkey = "05";
		Set<String> permission = this.rolePermissionService.
				getPermissionIdsByRoleIdsAndAppkey(roleSet, appkey);
		logger.info(JSON.toJSONString(permission));
		
	}
	/**
	 * add 
	 */
	/*@Test
	public void dealRolePermissionDatas(){
		String roleid = "1100000000000000025";
		String permissionids = "1100000000000000008,1100000000000000006,1100000000000000005,1100000000000000004";
		Map<String,Object> map = this.rolePermissionService.dealRolePermissionDatas(roleid, permissionids);
		logger.info(JSON.toJSONString(map));
	}*/
	
	@Test
	public void addRolePermissionDatas(){
		String roleid = "1100000000000000035";
		String permissionids = "1100000000000000008,1100000000000000006,1100000000000000005,1100000000000000004";
		Map<String,Object> map = this.rolePermissionService.addRolePermissionsByRoleId(roleid, permissionids);
		logger.info(JSON.toJSONString(map));
	}
	
	/**
	 * list role permisionids
	 */
	@Test
	public void listRolePermissionsByRoleId(){
		String roleid = "1100000000000000025";
		RolePermission rolePermission = new RolePermission();
		rolePermission.setRoleid(roleid);
		Map<String,Object> map = this.rolePermissionService.listRolePermissionsByRoleId(0, 10, rolePermission);
		logger.info(JSON.toJSONString(map));
	}
	
	/**
	 * list role not permissionids
	 */
	@Test
	public void listRoleNotPermissionsByRoleId(){
		String roleid = "1100000000000000025";
		RolePermission rolePermission = new RolePermission();
		rolePermission.setRoleid(roleid);
		Map<String,Object> map = this.rolePermissionService.listRoleNotPermissionsByRoleId(0, 10, rolePermission);
		logger.info(JSON.toJSONString(map));
	}
	
	@Test
	public void deleteRolePermission(){
		String roleid = "1100000000000000035";
		String permissionids = "1100000000000000008,1100000000000000006";
		Map<String,Object> map = this.rolePermissionService.deleteRolePermissionsByRoleId(roleid, permissionids);
		logger.info(JSON.toJSONString(map));
		
	}
}
