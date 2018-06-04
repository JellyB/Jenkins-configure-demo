package com.wdcloud.ptxtgl.sdk.rest;

import com.wdcloud.ptxtgl.sdk.PtxtglService;
import com.wdcloud.ptxtgl.user.entity.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.ws.rs.QueryParam;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by bigd on 2017/8/3.
 * ptxtgl 对外rest接口封装
 */
@Controller
@RequestMapping("/services/v1")
public class RestControllerV1 {


    final static String RESULT = "result";

    final static String MESSAGE = "msg";

    final static String DATA = "data";

    @Autowired
    private PtxtglService ptxtglService;

    @RequestMapping(value = "/fetchUserInfo" ,method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Map fetchUserInfo(@QueryParam("loginid")String loginid) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put(RESULT,false);
        User user;
        if(StringUtils.isEmpty(loginid)){
            String string = "fetchUserInfo: loginid can not be null!";
            map.put(MESSAGE,string);
            map.put(DATA,null);
            return map;
        }else{
            user = ptxtglService.fetchUserInfo(loginid);
            if(null == user){
                map.put(DATA,null);
                map.put(RESULT,false);
                map.put(MESSAGE,"user does not existed!");
            }else{
                map.put(DATA,user);
                map.put(RESULT,true);
                map.put(MESSAGE,"fetchUserInfo successfully!");
            }
            return map;
        }
    }
}
