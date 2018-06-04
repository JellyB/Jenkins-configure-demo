package com.wdcloud.ptxtgl.sdk;

import com.wdcloud.ptxtgl.base.entity.AuthReuslt;
import com.wdcloud.ptxtgl.base.entity.ListResult;
import com.wdcloud.ptxtgl.base.entity.Menu;
import com.wdcloud.ptxtgl.user.entity.User;


public interface PtxtglService {


    /**
	 * 获取用户响应菜单 rest&dubbo 接口
	 * @param userid	用户唯一标识
	 * @param appkey	系统标识
     */


	Menu getMenusByUser( String userid, String appkey);

	/**
	 * 返回指定角色的人员信息
	 * @param rolecode		角色编码
	 * @param appkey		系统标识
     * @return				返回对象
     */
	@SuppressWarnings("rawtypes")
	ListResult getUsersByRole(String rolecode,String appkey);

	/**
	 * 对外提供接口
	 * 根据当前用户id返回指定系统下的角色列表
	 * @param userid		用户唯一标识
	 * @param appkey		系统标识
     * @return				返回对象
     */
	@SuppressWarnings("rawtypes")
	ListResult getRolesByUserIdandAppkey(String userid, String appkey);

	/**
	 * 根据用户loginid 获取用户基本信息
	 * @param loginid		用户登录标识
	 * @return				返回对象
     */
	User fetchUserInfo(String loginid);


	/**
	 * 对外提供接口校验此用户安全登录信息是否正确
 	 * @param loginName	用户名
	 * @param passWord		密码
	 * @return				返回结果
     */
	AuthReuslt authUser (String loginName, String passWord);
}
