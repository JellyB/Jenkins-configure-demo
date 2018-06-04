package com.wdcloud.ptxtgl.menu;

import com.alibaba.fastjson.JSON;
import com.wdcloud.framework.core.util.PropertyXmlMgr;
import com.wdcloud.framework.web.springmvc.validation.Pattern;
import com.wdcloud.framework.web.springmvc.validation.impl.PatternImpl;
import com.wdcloud.ptxtgl.base.entity.Menu;
import com.wdcloud.ptxtgl.base.service.MenuService;
import com.wdcloud.ptxtgl.permissionResource.service.PermissionResourceService;
import com.wdcloud.ptxtgl.rolePermission.service.RolePermissionService;
import com.wdcloud.ptxtgl.userRole.service.UserRoleService;
import org.apache.log4j.PropertyConfigurator;
import org.hibernate.validator.internal.util.annotationfactory.AnnotationDescriptor;
import org.hibernate.validator.internal.util.annotationfactory.AnnotationFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

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
public class TestMenu {


	@Autowired
	private UserRoleService userRoleService;

	@Autowired
	private RolePermissionService rolePermissionService;

	@Autowired
	private PermissionResourceService permissionResourceService;
	@Autowired
	private MenuService menuService;
	private static Logger logger = LoggerFactory.getLogger(TestMenu.class);
	@Before
	public void setUp() throws Exception {
		PropertyConfigurator.configure("src/test/resources/conf/log4j.properties");
	}


	@Test
	public void testPattern(){
		AnnotationDescriptor<Pattern> descriptor = new AnnotationDescriptor<Pattern>(Pattern.class);
		Pattern pattern = AnnotationFactory.create(descriptor);
		PatternImpl patternImpl = new PatternImpl();
		patternImpl.initialize(pattern);
		Assert.assertTrue(patternImpl.isValid("15588631176", null));
	}

    @Test
	public void testMenus(){
		String appkey = "000001";
		Menu menu = this.menuService.getSystemMenus(appkey);
		logger.info("※※※※※※※※※※※※※※※※※※※※※※※※"+JSON.toJSONString(menu));
	}

    @Test
    public void testGetMenusByUser(){
    	String userid = "1100000000000000100";
    	String appkey = "03";
    	Menu menu = this.menuService.getMenusByUser(userid, appkey);
    	logger.info("※※※※※※※※※※※※※※※※※※※※※※※※"+JSON.toJSONString(menu));
    }

    /**
     * 获取用户菜单测试版
     */
    @Test
    public void testGetMenusByUserBeta(){
    	String userid  = "1100000000000000092";
    	String appkey = "05";
    	String moduleDm = PropertyXmlMgr.getString("CONF_COMMON", "PTXTGL_MODULE");
    	Set<String> roleSet = userRoleService.getRoleidsByUseridAndAppkey(userid, appkey);
    	Set<String> permissionSet = rolePermissionService.getPermissionIdsByRoleIdsAndAppkey(roleSet, appkey);
    	Set<String> resourceSet = permissionResourceService.getResourceIdsByPermissionIdsAndAppkey(permissionSet, appkey,moduleDm);
    	Set<String> moduleSet = menuService.getMenuCodesByReourceIdsAndAppkey(resourceSet, appkey);
    	logger.info("※※※※※※※※※※※※※※※※※※※※※※※※"+JSON.toJSONString(moduleSet));
    }

   /* @Test
	public void getRoleidsByuseridsAndAppkey(){
		String userid = "1100004000100007345";
		String appkey = "01";
		List<String> rolelist = userRoleService.getRoleidsByUseridAndAppkey(userid, appkey);

		List<String> permissionidlist = rolePermissionService.getPsermissionIdsByRoleIdandAppkey(rolelist, appkey);


		logger.info(JSON.toJSONString(permissionidlist));
	}*/
}
