package com.wdcloud.ptxtgl.sdk.rest;

import com.wdcloud.ptxtgl.base.entity.ListResult;
import com.wdcloud.ptxtgl.base.entity.Menu;
import com.wdcloud.ptxtgl.sdk.PtxtglService;
import com.wdcloud.ptxtgl.user.entity.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.ws.rs.QueryParam;

/**
 * Created by bigd on 2017/8/3.
 * ptxtgl对外rest接口
 */
@Controller
@RequestMapping("/services")
public class RestController {

    @Autowired
    private PtxtglService ptxtglService;

    /**
     * 获取指定用户的菜单
     * @param userid 用户唯一标识
     * @return 菜单对象
     */
    @RequestMapping(value = "/getMenusByUser",
            method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Menu getMenusByUser(@QueryParam("userid")String userid, @QueryParam("appkey")String appkey) {
        return this.ptxtglService.getMenusByUser(userid, appkey);
    }


    /**
     * 返回指定角色的人员信息
     * @param rolecode 角色编码
     * @param appkey 系统标识
     * @return 查询结果集
     */
    @RequestMapping(value = "/getUsersByRole" ,
            method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ListResult getUsersByRole(@QueryParam("rolecode")String rolecode, @QueryParam("appkey")String appkey) {
        return this.ptxtglService.getUsersByRole(rolecode, appkey);
    }


    /**
     * 对外提供接口
     * 根据当前用户id返回指定系统下的角色列表
     * @param userid 用户唯一标识
     * @param appkey 系统标识
     * @return 查询结果集
     */
    @RequestMapping(value = "/getRolesByUserIdandAppkey",
            method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ListResult getRolesByUserIdandAppkey(@QueryParam("userid")String userid, @QueryParam("appkey")String appkey) {
        return this.ptxtglService.getRolesByUserIdandAppkey(userid, appkey);
    }


    /**
     *
     * 根据用户loginid返回用户基本信息
     * @param loginid 用户登录id
     * @return User User对象
     */
    @RequestMapping(value = "/fetchUserInfo",
            method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public User fetchUserInfo(@QueryParam("loginid")String loginid) {
        User user;
        if(StringUtils.isEmpty(loginid)){
            return null;
        }else{
            user = ptxtglService.fetchUserInfo(loginid);
        }
        return user;
    }
}
