package com.wdcloud.ptxtgl.user;

import com.alibaba.fastjson.JSON;
import com.wdcloud.common.util.PinyinUtils;
import com.wdcloud.ptxtgl.base.entity.AuthReuslt;
import com.wdcloud.ptxtgl.user.entity.User;
import com.wdcloud.ptxtgl.user.service.UserService;
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

import java.util.Date;
import java.util.Map;
import java.util.Random;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:spring/applicationContext.xml",
		                           "classpath:spring/applicationContext-dubbo.xml",
		                           "classpath:spring/applicationContext-cache.xml",		                           
		                           "classpath:spring/applicationContext-session.xml",
		                           "classpath:spring/applicationContext-security.xml",
		                           "classpath:spring/applicationContext-springmvc.xml"
		                           })
public class TestUser {
	
	private Logger logger = LoggerFactory.getLogger(TestUser.class);
	
	
	private String [] USER_ARRAYS = new String []{"曹丕","荀彧","贾诩","孙尚香",
		"何太后","李典","张昭","孙仲谋","悲画扇",
		"马腾","马岱","马超","马谡","法正","姜维","邓艾","郭嘉",
		"荀攸","诸葛诞","袁绍","孙琳","孙伯符","孙坚"};
	
	@Autowired
	private UserService userService;
	@Before
	public void setUp() throws Exception {
		PropertyConfigurator.configure("src/test/resources/conf/log4j.properties");
	}
	
	/**
	 * add user
	 */
	@Test
	public void testCreateUser(){
		Random random = new Random();
		Random loinidRandom = new Random();
		int loginid = loinidRandom.nextInt(1000);
		int index = random.nextInt(this.USER_ARRAYS.length);
		String userName = this.USER_ARRAYS[index];
		String nickname = PinyinUtils.getFullSpell(userName);
		User user = new User();
		user.setLoginid("10" + String.format("%07d", loginid));
		user.setNickname(nickname);
		user.setUsername(userName);
		user.setLoginname(nickname);
		user.setPasswd("111111");
		Map<String,Object> map = this.userService.createUser(user);
		logger.info(JSON.toJSONString(map));		
	}
	
	
	@Test
	public void loadUserMesg(){
		String id = "1100000000000000081";
		User user = this.userService.loadUserMesg(id);
		logger.info(JSON.toJSONString(user));
	}
	/**
	 * check user's loginid occupied?
	 */
	@Test
	public void testCheckUserExist(){
		String loginname = "sunshangxiang";
		String id = "";
		Map<String,Object> map = this.userService.checkUserExist(loginname,id);
		logger.info(JSON.toJSONString(map));
	}
	
	/**
	 * delete this user by logic 
	 * update field delflag = D
	 */
	@Test
	public void deleteUserByLogic(){
		String id = "1100000000000000078,1100000000000000079,1100000000000000080";
		String operateCode ="admin_0002";
		Map<String,Object> map = this.userService.deleteUserByLogic(id, operateCode);
		logger.info(JSON.toJSONString(map));
	}
	
	/**
	 * update user's fields
	 */
	
	@Test
	public void updateUser(){
		User user = new User();
		user.setId("1100000000000000086");
		user.setBirthday(new Date());
		user.setEmail("sunquan@sanguo.com");
		user.setCellphone("01024234");
		user.setNickname("sunquan");
		user.setOperatercode("admin_0002");
		user.setSex("1");
		user.setDescn("字仲谋，吴郡富春（今浙江富阳）人，"
				+ "三国时代东吴建立者。东汉末年，孙权父亲孙坚和兄长孙策在群雄割据中打下江东基业。"
				+ "孙权十九岁时，孙策遭刺杀身亡，后继掌事，成为一方诸侯。222年，他自称吴王，"
				+ "建立吴国；229年称帝，建立吴，即东吴。谥号大皇帝，"
				+ "庙号太祖，统治江东地区长达五十二年，是三国时代统治者中最长的");
		Map<String,Object> map = this.userService.updateUser(user);
		logger.info(JSON.toJSONString(map));
	}
	
	/**
	 * list users according to conditions
	 */
	@Test
	public void testListUserByConditions(){
		User user  = new User();
		user.setUsername("孙");
		Map<String,Object> map = this.userService.listUserByConditions(0, 10, user);
		logger.info(JSON.toJSONString(map));
		
	}
	/**
	 * add user batch
	 */
	@Test
	public void testCreateUserBatch(){
		Random loinidRandom = new Random();
		for(int i = 0 ;i< this.USER_ARRAYS.length; i++){
			int loginid = loinidRandom.nextInt(1000);			
			String userName = this.USER_ARRAYS[i];
			String nickname = PinyinUtils.getFullSpell(userName);
			User user = new User();
			user.setLoginid("10" + String.format("%07d", loginid));
			user.setNickname(nickname);
			user.setUsername(userName);
			user.setLoginname(nickname);
			user.setPasswd("111111");
			Map<String,Object> map = this.userService.createUser(user);
			logger.info(JSON.toJSONString(map));
		}
	}
	
	@Test
	public void testFetchUserInfo(){
		String loginid = "100000424";
		User u = this.userService.fetchUserInfo(loginid);
		logger.info(JSON.toJSONString(u));
	}
	
	@Test
	public void testFetchUserInfoByLoginID(){
		String loginName = "900049";
		String passWord = "11111";
		AuthReuslt result = userService.authUser(loginName, passWord);
		logger.info(JSON.toJSONString(result));
	}
}
