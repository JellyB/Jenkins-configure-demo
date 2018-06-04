package com.wdcloud.ptxtgl.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.wdcloud.ptxtgl.user.web.controller.UserController;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by bigd on 2017/8/10.
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
public class UserControllerTest {


    @Autowired
    private UserController userController;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        PropertyConfigurator.configure("src/test/resources/conf/log4j.properties");
        //this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void load () throws Exception{

        String userId = "1100000000000000092";
        Map<String, Object> map = new HashMap<String, Object>();

        MvcResult result =mockMvc.perform(post("/user/load?id="+ userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSONObject.toJSONString(map)))
                .andExpect(status().isOk())
                //.andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result").value("true"))
                .andReturn();

        System.err.println(result.getResponse().getContentAsString());
    }
}
