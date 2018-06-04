package com.wdcloud.ptxtgl.role;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.PropertyConfigurator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.fastjson.JSON;
import com.wdcloud.ptxtgl.role.entity.Role;
import com.wdcloud.ptxtgl.role.service.RoleService;



@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = 
{"classpath:spring/applicationContext.xml",
    "classpath:spring/applicationContext-dubbo.xml",
    "classpath:spring/applicationContext-cache.xml",		                           
    "classpath:spring/applicationContext-session.xml",
    "classpath:spring/applicationContext-security.xml",
    "classpath:spring/applicationContext-common.xml",
    "classpath:spring/applicationContext-springmvc.xml",
    "classpath:applicationContext-hbase.xml"
    })

public class testRole {
	
	private Logger logger = LoggerFactory.getLogger(testRole.class);
	
	
	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;
	
	@Autowired
	private RoleService roleService;
	
	
	private String PREFIX_ROLE = "Role_";
	private String [] ROLE_ARRAYS = {"太尉","大将军","骠骑将军","车骑将军","卫将军","镇军大将军",
			"镇北大将军","镇南大将军","镇西大将军","镇东大将军",
			"征东大将军","征西大将军","征南大将军","征北大将军"};
	@Before
	public void setUp() throws Exception {
		PropertyConfigurator.configure("src/test/resources/conf/log4j.properties");
	}
	
	/**
	 * 校验
	 */
	@Test
	public void testCheckExist(){
		String roleCode2 = "Role_01211237053";
		String id = "";
		Map<String, Object> map = this.roleService.checkRoleExist(roleCode2,id);
		logger.info(JSON.toJSONString(map));
	}
	/**
	 * 添加角色
	 */
	@Test
	public void testCreaterole(){
		Role role = new Role();
		role.setRolename("080219834018209340210921843091234");
		role.setRolecode("1028430192834091823490-92183498-09");
		role.setRoletype("1");
		role.setDescn("你0213849821-39482948932-92183498092");
		role.setAppkey("000001");
		role.setDelflag("A");
		role.setCreatertime(new Date());
		role.setCreatercode("admin0001");
		Map<String, Object> map = this.roleService.createrole(role);
		logger.info(JSON.toJSONString(map));
	}
   /**
    * 删除
    */
	
	@Test
	public void deleteRole(){
		String id = "1100000000000000033,1100000000000000034,1100000000000000035";
		String operateCode = "admin_00002";
		Map<String, Object> map = this.roleService.deleteroleByLogic(id, operateCode);
		logger.info(JSON.toJSONString(map));
	}
	/**
	 * 更新
	 */
	@Test
	public void updateRole(){ 
		String id = "1100000000000000035";
		Role role = new Role();
		role.setId(id);
		role.setRolename("紧邻十二钗");
		role.setRolecode("role_adsefsdf");
		role.setRoletype("B");
		role.setOperatercode("admin_00002");
		Map<String, Object> map = this.roleService.updaterole(role);
		logger.info(JSON.toJSONString(map));
	}
	/**
	 * 查询
	 */
	@Test
	public void testListRole(){
		Role role1 = new Role(); 
		role1.setRolename("将军");
		Role role2 = new Role();
		role2.setRolename("东");
		Map<String, Object> map = this.roleService.listroleByConditions(0, 10, role1);
		logger.info(JSON.toJSONString(map));
	}
	
	
	@Test
	public void getAccount() throws Exception {
		/*MockMultipartFile file = new MockMultipartFile("file", "orig.txt", null, "bar".getBytes());*/
		//mockMvc.perform(fileUpload("/user/upload").file(file).characterEncoding("UTF-8").contentType(MediaType.MULTIPART_FORM_DATA)).andExpect(status().isOk());		
		mockMvc.perform((post("/role/save").param("rolename", "1000000001").param("rolecode", "w31234143"))).andExpect(status().isOk()).andDo(print());
	}
}
