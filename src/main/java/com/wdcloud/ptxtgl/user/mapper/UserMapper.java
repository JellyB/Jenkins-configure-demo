package com.wdcloud.ptxtgl.user.mapper;

import java.util.List;
import java.util.Map;

import com.wdcloud.ptxtgl.user.entity.User;

public interface UserMapper {
	
	/**
	 * 检查用户登录名是否存在
	 * @param loginid
	 * @return
	 */
	int checkUserExist(Map<String, String> map);
	
	/**
	 * 创建管理员
	 * @param user
	 * @return
	 */
	int createUser(User user);
	
	/**
	 * 根据ID加载用户信息
	 * @param id
	 * @return
	 */
	User loadUserMesg(String id);
	/**
	 * 删除管理员
	 * @param id
	 * @return
	 */
	int deleteUserByLogic(Map<String, Object> dataMap);
	
	/**
	 * 更新管理员
	 * @param user
	 * @return
	 */
	int updateUser(User user);
	
	/**
	 * 查询管理员-条数
	 * @param dataMap
	 * @return
	 */
	int listUserCountByConditions(Map<String, Object> dataMap);
	
	/**
	 * 查询管理员-记录
	 * @param dataMap
	 * @return
	 */
	List<User> listUserByConditions(Map<String, Object> dataMap);
	
	/**
	 * 重置用户密码
	 * @param ids
	 * @param passwd
	 * @param confirmPasswd
	 * @return
	 */
	int resetPasswd(User user);
	
	/**
	 * 获取登录的用户的基本信息
	 * @param loginid
	 * @return User
	 */
	User fetchUserInfo(String loginid);
	
	/**
	 * 根据loginId用户鉴权
	 * @author bigd
	 * @param  loginId
	 * @return user
	 */
	User fetchUserByLoginId(String loginId);
	
	/**
	 * 根据loginName用户鉴权
	 * @author bigd
	 * @param  loginName
	 * @return user
	 */
	User fetchUserByLoginName(String loginName);
}
