package com.wdcloud.ptxtgl.user.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.wdcloud.common.exception.CommonException;
import com.wdcloud.common.service.CommonGeneratorService;
import com.wdcloud.common.util.AdminLoginUtils;
import com.wdcloud.common.util.CheckUtils;
import com.wdcloud.common.util.WdcloudMD5PasswordEncoder;
import com.wdcloud.framework.core.util.I18NMessageReader;
import com.wdcloud.ptxtgl.base.entity.AuthReuslt;
import com.wdcloud.ptxtgl.constant.PtxtglConstant;
import com.wdcloud.ptxtgl.user.entity.User;
import com.wdcloud.ptxtgl.user.mapper.UserMapper;
import com.wdcloud.ptxtgl.user.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private CommonGeneratorService commonGeneratorService;
	
	private WdcloudMD5PasswordEncoder wdcloudMD5PasswordEncoder;
	
	public void setWdcloudMD5PasswordEncoder(WdcloudMD5PasswordEncoder wdcloudMD5PasswordEncoder) {
		this.wdcloudMD5PasswordEncoder = wdcloudMD5PasswordEncoder;
	}
	
	
	
	/**
	 * 检查用户是否存在
	 * @param loginname
	 * @param id
	 * @return
	 */
	@Override
	public Map<String,Object> checkUserExist(String loginname, String id) {
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,String> dataMap = new HashMap<String,String>();
		dataMap.put("loginname", loginname);
		if(StringUtils.isNotEmpty(id)){
			dataMap.put("id", id);
		}
		int count = this.userMapper.checkUserExist(dataMap);
		if(count >0 ){
			map.put(PtxtglConstant.RETURN_RESULT, false);
			map.put(PtxtglConstant.RETURN_MSG,I18NMessageReader.getMessage("user.nickname.occupied"));
		}else{
			map.put(PtxtglConstant.RETURN_RESULT, true);
			map.put(PtxtglConstant.RETURN_MSG, I18NMessageReader.getMessage("user.nickname.valid"));
		}
		return map;
	}
	
	/**
	 * 创建管理员
	 * @param user
	 * @return
	 */
	@Override
	public Map<String,Object> createUser(User user) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put(PtxtglConstant.RETURN_RESULT, false);
		Map<String, Object> checkMap = this.checkUserExist(user.getLoginname(),"");
		if((Boolean) checkMap.get(PtxtglConstant.RETURN_RESULT)){
			if(user.getPasswd().equals(user.getConfirmpasswd())){
				try {
					int excuteCount = 0;
					Long versions = this.commonGeneratorService.nextVersion("t_sysmgr_user");
					String id = this.commonGeneratorService.nextPrimaryId("t_sysmgr_user");
					long loginid = this.commonGeneratorService.nextVersion(PtxtglConstant.HBASE_INCREMENT_LOGINID);
					String encodePasswd = wdcloudMD5PasswordEncoder.encodePassword(user.getPasswd());
					user.setId(id);
					user.setLoginid(String.valueOf(loginid));
					user.setVersions(String.valueOf(versions));
					user.setStatus("1");
					user.setDelflag("A");
					user.setRegistertime(new Date());
					user.setCreatertime(new Date());
					user.setSex("1");
					user.setPasswd(encodePasswd);
					excuteCount = this.userMapper.createUser(user);
					if (excuteCount > 0) {
						map.put(PtxtglConstant.RETURN_RESULT, true);
						map.put(PtxtglConstant.RETURN_MSG, I18NMessageReader.getMessage("user.add.successed"));
					} else {
						map.put(PtxtglConstant.RETURN_MSG, I18NMessageReader.getMessage("user.add.failed"));
						throw new CommonException(I18NMessageReader.getMessage("user.add.failed"));
					} 
				} catch (DuplicateKeyException dke){
					logger.error(dke.getMessage(), dke);
					map.put(PtxtglConstant.RETURN_MSG, I18NMessageReader.getMessage("user.loginname.duplicate"));
					throw new CommonException(dke.getMessage(), dke);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
					throw new CommonException(e.getMessage(), e);
				}
			}else{
				map.put(PtxtglConstant.RETURN_MSG, I18NMessageReader.getMessage("user.passwd.inconsistent"));
				return map;
			}
		}else{
			return checkMap;
		}
		return map;
	}
	
	
	/**
	 * 根据ID返回用户信息
	 * @author bigd
	 * @param id
	 */
	@Override
	public User loadUserMesg(String id) {
		return this.userMapper.loadUserMesg(id);
	}
	
	/**
	 * 删除管理员
	 * @param id
	 * @return
	 */
	/*@Override*/
	/*public Map<String, Object> deleteUserByLogic(String ids,String operateCode) {
		List<String> list = Arrays.asList(ids.split(","));
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		try {
			map.put(PtxtglConstant.RETURN_RESULT, false);
			Long versions = this.commonGeneratorService.nextVersion("t_sysmgr_user");
			dataMap.put("list", list);
			dataMap.put("delflag", "D");
			dataMap.put("operatercode", operateCode);
			dataMap.put("operatetime", new Date());
			dataMap.put("versions", versions);
			int excuteCount = 0;
			excuteCount = this.userMapper.deleteUserByLogic(dataMap);
			if (excuteCount > 0) {
				map.put(PtxtglConstant.RETURN_RESULT, true);
				map.put(PtxtglConstant.RETURN_MSG, I18NMessageReader.getMessage("user.delete.successed"));
			} else {
				map.put(PtxtglConstant.RETURN_MSG, I18NMessageReader.getMessage("user.delete.failed"));
				throw new CommonException(I18NMessageReader.getMessage("user.delete.failed"));
			} 
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new CommonException(e.getMessage(), e);
		}
		return map;
	}*/
	@Override
	public Map<String, Object> deleteUserByLogic(String ids,String operateCode) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put(PtxtglConstant.RETURN_RESULT, false);
		List<String> list = Arrays.asList(ids.split(","));
		int excutNum = 0;
		try {
			for(String userid : list){
				Map<String,Object> dataMap = new HashMap<String,Object>();
				Long versions = this.commonGeneratorService.nextVersion("t_sysmgr_user");
				dataMap.put("id", userid);
				dataMap.put("list", list);
				dataMap.put("delflag", "D");
				dataMap.put("operatercode", operateCode);
				dataMap.put("operatetime", new Date());
				dataMap.put("versions", versions);
				int excuteCount = this.userMapper.deleteUserByLogic(dataMap);
				if (excuteCount == 1) {
					excutNum ++;
				} else {
					map.put(PtxtglConstant.RETURN_MSG, I18NMessageReader.getMessage("user.delete.failed"));
					throw new CommonException(I18NMessageReader.getMessage("user.delete.failed"));
				}
			}
			if(excutNum == list.size()){
				map.put(PtxtglConstant.RETURN_RESULT, true);
				map.put(PtxtglConstant.RETURN_MSG, I18NMessageReader.getMessage("user.delete.successed"));
			}else{
				map.put(PtxtglConstant.RETURN_MSG, I18NMessageReader.getMessage("user.delete.failed"));
				throw new CommonException(I18NMessageReader.getMessage("user.delete.failed"));
			}				
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new CommonException(e.getMessage(), e);
		}
		return map;
	}
	
	/**
	 * 更新管理员
	 * @param user
	 * @return
	 */
	@Override
	public Map<String, Object> updateUser(User user) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put(PtxtglConstant.RETURN_RESULT,false);
		Map<String, Object> checkMap = this.checkUserExist(user.getLoginname(),user.getId());
		if((Boolean) checkMap.get(PtxtglConstant.RETURN_RESULT)){
			int excuteCount = 0;
			try {
				long versions = this.commonGeneratorService.nextVersion("t_sysmgr_user");
				user.setDelflag("U");
				user.setOperatetime(new Date());
				user.setVersions(String.valueOf(versions));
				excuteCount = this.userMapper.updateUser(user);
				if (excuteCount > 0) {
					map.put(PtxtglConstant.RETURN_RESULT, true);
					map.put(PtxtglConstant.RETURN_MSG, I18NMessageReader.getMessage("user.update.successed"));
				} else {
					map.put(PtxtglConstant.RETURN_MSG, I18NMessageReader.getMessage("user.update.failed"));
					throw new CommonException(I18NMessageReader.getMessage("user.update.failed"));
				} 
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				throw new CommonException(I18NMessageReader.getMessage("user.update.failed"));
			}
		}else{
			return checkMap;
		}		
		return map;
	}
	
	/**
	 * 查询管理员
	 * @param user
	 * @return
	 */
	@Override
	public Map<String, Object> listUserByConditions(int pageStart, int pageSize, User user) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put(PtxtglConstant.RETURN_RESULT,false);
		Map<String,Object> dataMap = this.listUserConditions(pageStart,pageSize,user);
		try {
			int records = this.userMapper.listUserCountByConditions(dataMap);
			List<User> rows = this.userMapper.listUserByConditions(dataMap);
			map.put(PtxtglConstant.RETURN_RESULT,true);
			map.put(PtxtglConstant.RETURN_RECORDS, records);
			map.put(PtxtglConstant.RETURN_ROWS, rows);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return map;
	}
	
	
	/**
	 * 返回条件查询条件map
	 * @param pageStart
	 * @param pageSize
	 * @param user
	 * @return
	 */
	private Map<String, Object> listUserConditions(int pageStart, int pageSize, User user) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put(PtxtglConstant.PAGE_START, pageStart);
		map.put(PtxtglConstant.PAGE_SIZE, pageSize);
		if(StringUtils.isNotEmpty(user.getUsername())){
			map.put("username", user.getUsername());
		}
		if(StringUtils.isNotEmpty(user.getLoginid())){
			map.put("loginid", user.getLoginid());
		}
		if(StringUtils.isNotEmpty(user.getLoginname())){
			map.put("loginname",  user.getLoginname());
		}
		if(StringUtils.isNotEmpty(user.getNickname())){
			map.put("nickname", user.getNickname());
		}			
		return map;
	}

	/**
	 * 重置用户密码
	 * @param ids
	 * @param passwd
	 * @param confirmPasswd
	 * @return
	 */
	@Override
	public Map<String, Object> updatePassword(String ids, String passwd, String confirmPasswd, String operaterCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(PtxtglConstant.RETURN_RESULT, false);
		if (!passwd.equals(confirmPasswd)) {
			map.put(PtxtglConstant.RETURN_MSG, I18NMessageReader.getMessage("user.passwd.inconsistent"));
			return map;
		}
		String[] userIds = ids.split(",");	
		if (userIds.length <= 0) {
			map.put(PtxtglConstant.RETURN_MSG, I18NMessageReader.getMessage("ptxtgl.deal.data.null"));
			return map;
		}
		User u = null;
		String versions = "";
		Date operatetime = new Date();
		int count = 0;
		try {
			String encodePassword = this.wdcloudMD5PasswordEncoder.encodePassword(passwd);
			for (int i = 0; i < userIds.length; i++) {
				versions = String.valueOf(this.commonGeneratorService.nextVersion("t_sysmgr_user"));
				u = new User();
				u.setId(userIds[i]);
				u.setVersions(versions);
				u.setPasswd(encodePassword);
				u.setOperatercode(operaterCode);
				u.setOperatetime(operatetime);
				u.setDelflag("U");
				int excuteCount = this.userMapper.resetPasswd(u);
				if (excuteCount > 0) {
					count++;
				}else{
					String errMsg = I18NMessageReader.getMessage("user.passwd.reset.failed");
					map.put(PtxtglConstant.RETURN_MSG, errMsg);
					throw new CommonException(errMsg);
				} 
			}
			if (count != userIds.length) {
				String errMsg = I18NMessageReader.getMessage("user.passwd.reset.failed");
				map.put(PtxtglConstant.RETURN_MSG, errMsg);
				throw new CommonException(errMsg);
			} else {
				map.put(PtxtglConstant.RETURN_RESULT, true);
				map.put(PtxtglConstant.RETURN_MSG, I18NMessageReader.getMessage("user.passwd.reset.successed"));
			}
		} catch (Exception e) {
			String errMsg = I18NMessageReader.getMessage("user.passwd.reset.failed");
			map.put(PtxtglConstant.RETURN_MSG, errMsg);
			logger.error(e.getMessage(), e);
			throw new CommonException(errMsg, e);
		}
		return map;
	}


	
	/**
	 * 获取登录用户的基本信息
	 * @author bigd
	 * 
	 */
	@Override
	public User fetchUserInfo(String loginid) {
		User user = null;
		try {
			user = userMapper.fetchUserInfo(loginid);
		} catch (Exception e) {
			logger.error("fetchUserInfo caught an exception!", e);
		}
		return user;
	}



	/**
	 * 根据用户名+用户密码进行用户信息鉴权
	 * @author 	bigd
	 * @param 	loginName
	 * @param 	passWord
	 * @return 	AuthReuslt
	 */
	@Override
	public AuthReuslt authUser(String loginName, String passWord) {
		AuthReuslt result = new AuthReuslt();
		result.setCode(AuthReuslt.CODE_NO);
		result.setData(null);
		User user = null;
		try {
			if (AdminLoginUtils.isLoginId(loginName)) {
				user = this.userMapper.fetchUserByLoginId(loginName);
			} else if (CheckUtils.isNickName(loginName)) {
				user = this.userMapper.fetchUserByLoginName(loginName);
			} else {
				result.setMessage("Illegal argument loginName!");
				result.setData(null);
				return result;
			}
			if (null == user) {
				result.setMessage("No user found for this loginName!");
				result.setData(null);
				return result;
			} else {
				String passwd = user.getPasswd();
				String enpassWd = wdcloudMD5PasswordEncoder.encodePassword(passWord);
				if (StringUtils.isEmpty(passwd)) {
					throw new IllegalArgumentException("Illegal record!");
				} else {
					if (enpassWd.equals(passwd)) {
						user.setPasswd(null);
						result.setCode(AuthReuslt.CODE_YES);
						result.setMessage("user authenticated successfully!");
						result.setData(user);
						return result;
					} else {
						result.setMessage("user authenticated failed!");
						result.setData(null);
						return result;
					}
				}
			} 
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.setCode(AuthReuslt.CODE_NO);
			result.setMessage("system caught an unexpected exception");
			result.setData(null);
		}
		return result;
	}	
}
