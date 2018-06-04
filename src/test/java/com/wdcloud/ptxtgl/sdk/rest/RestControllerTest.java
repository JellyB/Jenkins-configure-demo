package com.wdcloud.ptxtgl.sdk.rest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.PropertyConfigurator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by bigd on 2017/8/4.
 * Rest接口测试类
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:spring/applicationContext.xml",
                                    "classpath:spring/applicationContext-dubbo.xml",
                                    "classpath:spring/applicationContext-cache.xml",
                                    "classpath:spring/applicationContext-session.xml",
                                    "classpath:spring/applicationContext-security.xml",
                                    "classpath:spring/applicationContext-springmvc.xml"
                                    })
public class RestControllerTest {

    final static Logger logger = LoggerFactory.getLogger(RestControllerTest.class);

    final static String TestUrl = "http://yfsysadmin.wdcloud.cc/ptxtgl";

    final  static String LocalUrl = "http://192.168.8.245:8009/ptxtgl";

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private RestController restController;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        PropertyConfigurator.configure("src/test/resources/conf/log4j.properties");
        //this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        this.mockMvc = MockMvcBuilders.standaloneSetup(restController).build();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetMenusByUser() throws Exception {

        String userId = "1100000000000000092";
        String appKey = "05";
        Map<String, Object> map = new HashMap<String, Object>();

        MvcResult result =mockMvc.perform(get("/services/getMenusByUser?userid="
                + userId + "&appkey=" +appKey)
                 .contentType(MediaType.APPLICATION_JSON)
                .content(JSONObject.toJSONString(map)))
                .andExpect(status().isOk())
                //.andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result").value("true"))
                .andReturn();

        MockHttpServletRequest request = result.getRequest();
        System.err.println(JSON.toJSONString(request));
        System.err.println("※※※※※※※※※※※※※※※※※");
        MockHttpServletResponse response = result.getResponse();
        System.err.println(JSON.toJSONString(response));
        System.err.println("※※※※※※※※※※※※※※※※※");
        System.err.println(result.getResponse().getContentAsString());
    }


    @Test
    public void testGetMenusByUser1() {
        MvcResult result;
        String userId = "1100000000000000092";
        String appKey = "05";
        try {
            result = mockMvc.perform(MockMvcRequestBuilders.get("/services/getMenusByUser?userid="
                    + userId + "&appkey=" +appKey)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(""))
                    .andExpect(status().isOk())
                    .andReturn();
            System.out.println("relult: " + result.getResponse().getContentAsString());
            MockHttpServletRequest request = result.getRequest();
            logger.info(JSON.toJSONString(request));
            logger.info("※※※※※※※※※※※※※※※※※");
            MockHttpServletResponse response = result.getResponse();
            logger.info(JSON.toJSONString(response));
            logger.info("※※※※※※※※※※※※※※※※※");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testGetUsersByRole() throws Exception {

    }

    @Test
    public void testGetRolesByUserIdandAppkey() throws Exception {

    }

    @Test
    public void testFetchUserInfo() throws Exception {

    }
}