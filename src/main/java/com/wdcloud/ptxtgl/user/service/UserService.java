package com.wdcloud.ptxtgl.user.service;

import java.util.Map;

import com.wdcloud.ptxtgl.base.entity.AuthReuslt;
import com.wdcloud.ptxtgl.user.entity.User;

public interface UserService {
	
	/**
	 * 检查用户是否存在
	 * @param id 
	 * @param loginid
	 * @return
	 */
	Map<String, Object> checkUserExist(String loginname, String id);
	
	/**
	 * 创建管理员
	 * @param user
	 * @return
	 */
	Map<String, Object> createUser(User user);
	
	/**
	 * 根据ID返回用户信息
	 * @param id
	 * @return
	 */
	User loadUserMesg(String id);
	/**
	 * 删除管理员
	 * @param id
	 * @param operateCode 
	 * @return
	 */
	Map<String, Object> deleteUserByLogic(String id, String operateCode);
	
	/**
	 * 更新管理员
	 * @param user
	 * @return
	 */
	Map<String, Object> updateUser(User user);
	
	/**
	 * 查询管理员
	 * @param pageSize 
	 * @param pageStart 
	 * @param user
	 * @return
	 */
	Map<String, Object> listUserByConditions(int pageStart, int pageSize, User user);
	
	/**
	 * 重置用户密码
	 * @param ids
	 * @param passwd
	 * @param confirmPasswd
	 * @return
	 */
	Map<String, Object> updatePassword(String ids, String passwd, String confirmPasswd, String operaterCode);
	
	/**
	 * 对外提供接口：日期2016-0718
	 * @author bigd
	 * @param loginid
	 * @return User
	 */
	User fetchUserInfo(String loginid);
	
	
	/**
	 * 对外提供接口校验此用户安全登录信息是否正确
	 * @author bigd
	 * @time  2016-09-06
	 * @param loginName
	 * @param passW
	 * @return
	 */
	AuthReuslt authUser(String loginName, String passWord);

	
}
