package com.wdcloud.ptxtgl.sdk.impl;

import com.wdcloud.ptxtgl.base.entity.AuthReuslt;
import com.wdcloud.ptxtgl.base.entity.ListResult;
import com.wdcloud.ptxtgl.base.entity.Menu;
import com.wdcloud.ptxtgl.base.service.MenuService;
import com.wdcloud.ptxtgl.sdk.PtxtglService;
import com.wdcloud.ptxtgl.user.entity.User;
import com.wdcloud.ptxtgl.user.service.UserService;
import com.wdcloud.ptxtgl.userRole.service.UserRoleService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.QueryParam;

@Service
public class PtxtglServiceImpl implements PtxtglService{

	private static Logger logger = LoggerFactory.getLogger(PtxtglServiceImpl.class);

	@Autowired
	private UserRoleService userRoleService;

	@Autowired
	private MenuService menuService;

	@Autowired
	private UserService userService;


	/**
	 * 获取指定用户的菜单
	 * @param userid 用户唯一标识
	 * @return 菜单对象
	 */
	@Override
	public Menu getMenusByUser(@QueryParam("userid")String userid, @QueryParam("appkey")String appkey) {
		return this.menuService.getMenusByUser(userid, appkey);
	}

	/**
	 * 返回指定角色的人员信息
	 * @param rolecode 角色编码
	 * @param appkey 系统编码
	 * @return 查询对象集合
	 */
	@Override
	public ListResult getUsersByRole(@QueryParam("rolecode")String rolecode, @QueryParam("appkey")String appkey) {
		return this.userRoleService.getUsersByRole(rolecode, appkey);
	}

	/**
	 * 对外提供接口
	 * 根据当前用户id返回指定系统下的角色列表
	 * @param userid 用户唯一标识
	 * @param appkey 系统对象
	 * @return 查询结果集合
	 */
	@Override
	public ListResult getRolesByUserIdandAppkey(@QueryParam("userid")String userid, @QueryParam("appkey")String appkey) {
		return this.userRoleService.getRoleByUserIdAndAappkey(userid, appkey);
	}

	/**
	 *
	 * 根据用户loginid返回用户基本信息
	 * @param loginid 用户登录id
	 * @return User 用户对象
	 */
	@Override
	public User fetchUserInfo(@QueryParam("loginid")String loginid) {
		User user;
		if(StringUtils.isEmpty(loginid)){
			logger.error("fetchUserInfo: loginid can not be null!");
			return null;
		}else{
			user = userService.fetchUserInfo(loginid);
		}
		return user;
	}


	/**
	 * 对外提供接口校验此用户安全登录信息是否正确
	 * @param loginName 用户登录名
	 * @param passWord 用户密码
	 * @return 校验结果
	 */
	@Override
	public AuthReuslt authUser(String loginName, String passWord) {
		AuthReuslt result = new AuthReuslt();
		result.setCode(AuthReuslt.CODE_NO);
		try {
			if (StringUtils.isEmpty(loginName)) {
				throw new IllegalArgumentException("loginName is empty!");
			}
			if (StringUtils.isEmpty(passWord)) {
				throw new IllegalAccessError("passWord is empty!");
			}
			result = userService.authUser(loginName, passWord);
		} catch (Exception e) {
			result.setCode(AuthReuslt.CODE_NO);
			result.setData(null);
			result.setMessage("system caught an unexception!");
			logger.error(e.getMessage());
		}
		return result;
	}
}
