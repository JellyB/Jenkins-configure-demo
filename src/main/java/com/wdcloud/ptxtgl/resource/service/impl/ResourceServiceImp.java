/**
 * @author CHENYB
 * @(#)ResourceServiceImp.java 2016年1月19日
 * 
 * wdcloud 版权所有2014~2016。
 */
package com.wdcloud.ptxtgl.resource.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.wdcloud.common.constant.UicConstants;
import com.wdcloud.common.exception.CommonException;
import com.wdcloud.common.service.CommonGeneratorService;
import com.wdcloud.framework.core.util.I18NMessageReader;
import com.wdcloud.ptxtgl.constant.PtxtglConstant;
import com.wdcloud.ptxtgl.resource.entity.Resource;
import com.wdcloud.ptxtgl.resource.mapper.ResourceMapper;
import com.wdcloud.ptxtgl.resource.service.ResourceService;

/**
 * @author CHENYB
 * @since 2016年1月19日
 */
public class ResourceServiceImp implements ResourceService {

	private static final Log logger = LogFactory.getLog(ResourceServiceImp.class);

	@Autowired
	private ResourceMapper resourceMapper;
	@Autowired
	private CommonGeneratorService commonGeneratorService;

	/**
	 * @author CHENYB
	 * @see com.wdcloud.ptxtgl.resource.service.ResourceService#getResourceById(java.lang.String)
	 * @since 2016年2月18日 下午2:10:06
	 */
	@Override
	public Resource getResourceById(String resourceid) {
		return this.resourceMapper.selectResourceById(resourceid);
	}

	/**
	 * @author CHENYB
	 * @see com.wdcloud.ptxtgl.resource.service.ResourceService#loadResources(int, int, com.wdcloud.ptxtgl.resource.entity.Resource)
	 * @since 2016年1月25日 上午11:55:21
	 */
	@Override
	public Map<String, Object> loadResources(int pageStart, int pageSize, Resource resource) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> params = this.assembleResourceConditions(pageStart, pageSize, resource);
		int records = this.resourceMapper.selectResourcesCount(params);
		List<Resource> rows = this.resourceMapper.selectResources(params);
		result.put(PtxtglConstant.RETURN_RECORDS, records);
		result.put(PtxtglConstant.RETURN_ROWS, rows);
		return result;
	}

	/**
	 * 组装资源查询条件
	 * @author CHENYB
	 * @param pageStart
	 * @param pageSize
	 * @param resource
	 * @return
	 * @since 2016年1月25日 下午12:44:49
	 */
	private Map<String, Object> assembleResourceConditions(int pageStart, int pageSize, Resource resource) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(PtxtglConstant.PAGE_START, pageStart);
		params.put(PtxtglConstant.PAGE_SIZE, pageSize);
		if (StringUtils.isNotEmpty(resource.getResourcename())) {
			params.put("resourcename", resource.getResourcename());
		}
		if (StringUtils.isNotEmpty(resource.getResourcecode())) {
			params.put("resourcecode", resource.getResourcecode());
		}
		if (StringUtils.isNotEmpty(resource.getResourcetype())) {
			params.put("resourcetype", resource.getResourcetype());
		}
		if (StringUtils.isNotEmpty(resource.getAppkey())) {
			params.put("appkey", resource.getAppkey());
		}
		return params;
	}

	/**
	 * @author CHENYB
	 * @see com.wdcloud.ptxtgl.resource.service.ResourceService#saveResource(com.wdcloud.ptxtgl.resource.entity.Resource)
	 * @since 2016年1月21日 下午1:43:38
	 */
	@Override
	public Map<String, Object> saveResource(Resource resource) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(PtxtglConstant.RETURN_RESULT, false);
		if (this.resourceCodeExist(resource.getResourcecode(), resource.getId())) {
			result.put(PtxtglConstant.RETURN_MSG, I18NMessageReader.getMessage("ptxtgl.action.resource.resourcecode.exist", resource.getResourcecode()));
			return result;
		}
		Date date = new Date();
		int rs = 0;
		try {
			resource.setDelflag("A");
			resource.setCreatertime(date);
			resource.setOperatetime(date);
			Long version = this.commonGeneratorService.nextVersion("t_sysmgr_resource");
			String id = this.commonGeneratorService.nextPrimaryId("t_sysmgr_resource");
			resource.setId(id);
			resource.setVersions(UicConstants.getStringFromObject(version));
			rs = this.resourceMapper.insertResource(resource);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			if (rs == 0) {
				result.put(PtxtglConstant.RETURN_MSG, I18NMessageReader.getMessage("ptxtgl.action.resource.create.error"));
				return result;
			}
			throw new CommonException();
		}
		result.put(PtxtglConstant.RETURN_RESULT, true);
		return result;
	}

	/**
	 * @author CHENYB
	 * @see com.wdcloud.ptxtgl.resource.service.ResourceService#resourceCodeExist(java.lang.String, java.lang.String)
	 * @since 2016年1月22日 上午9:57:28
	 */
	@Override
	public boolean resourceCodeExist(String resourcecode, String resourceid) {
		int count = this.resourceMapper.getPermissionCodeCount(resourcecode, resourceid);
		return count > 0;
	}

	/**
	 * @author CHENYB
	 * @see com.wdcloud.ptxtgl.resource.service.ResourceService#deleteResources(java.lang.String, java.lang.String)
	 * @since 2016年1月21日 下午1:49:29
	 */
	@Override
	public Map<String, Object> deleteResources(String resourceids, String opratercode) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(PtxtglConstant.RETURN_RESULT, false);
		if (StringUtils.isBlank(resourceids)) {
			result.put(PtxtglConstant.RETURN_MSG, I18NMessageReader.getMessage("ptxtgl.action.delete.unselect"));
			return result;
		}
		String[] ids = resourceids.split(",");
		try {
			for (int i = 0; i < ids.length; i++) {
				this.deleteResourceById(ids[i], opratercode);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CommonException(I18NMessageReader.getMessage("operate.error"));
		}
		result.put(PtxtglConstant.RETURN_RESULT, true);
		return result;
	}

	/**
	 * 根据id删除资源
	 * @author CHENYB
	 * @param string
	 * @param opratercode
	 * @since 2016年1月25日 下午4:46:38
	 */
	private void deleteResourceById(String resourceid, String opratercode) throws Exception {
		try {
			Resource resource = this.resourceMapper.selectResourceById(resourceid);
			if (resource == null) {
				return;
			}
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("resourceid", resourceid);
			params.put(UicConstants.FIELD_DELFLAG, "D");
			params.put(UicConstants.FIELD_OPERATERCODE, opratercode);
			params.put(UicConstants.FIELD_OPERATETIME, new Date());
			params.put(UicConstants.FIELD_VERSIONS, this.commonGeneratorService.nextVersion("t_sysmgr_resource"));
			this.resourceMapper.deleteResourceById(params);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new CommonException(I18NMessageReader.getMessage("operate.error"));
		}
	}

	/**
	 * @author CHENYB
	 * @see com.wdcloud.ptxtgl.resource.service.ResourceService#updateResource(com.wdcloud.ptxtgl.resource.entity.Resource)
	 * @since 2016年1月21日 下午2:07:04
	 */
	@Override
	public Map<String, Object> updateResource(Resource resource) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(PtxtglConstant.RETURN_RESULT, false);
		if (resource == null || resource.getId() == null) {
			result.put(PtxtglConstant.RETURN_MSG, I18NMessageReader.getMessage("ptxtgl.action.resource.update.param.error"));
			return result;
		}
		int rs = 0;
		try {
			Resource res = this.resourceMapper.selectResourceById(resource.getId());
			if (res == null) {
				result.put(PtxtglConstant.RETURN_MSG, I18NMessageReader.getMessage("ptxtgl.action.resource.notexist"));
				return result;
			}
			if (this.resourceCodeExist(resource.getResourcecode(), resource.getId())) {
				result.put(PtxtglConstant.RETURN_MSG, I18NMessageReader.getMessage("ptxtgl.action.resource.resourcecode.duplicate", resource.getResourcecode()));
				return result;
			}
			resource.setDelflag("U");
			resource.setOperatetime(new Date());
			Long version = this.commonGeneratorService.nextVersion("t_sysmgr_resource");
			resource.setVersions(UicConstants.getStringFromObject(version));
			rs = this.resourceMapper.updateResource(resource);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			if (rs == 0) {
				result.put(PtxtglConstant.RETURN_MSG, I18NMessageReader.getMessage("ptxtgl.action.resource.update.error"));
				return result;
			}
			throw new CommonException(I18NMessageReader.getMessage("operate.error"));
		}
		result.put(PtxtglConstant.RETURN_RESULT, true);
		return result;
	}
	
	/**
	 * 根据UserId及Appkey返回用户可访问资源Url set 集合；
	 * 查询参数为userId 及Appkey用 # 拼接；并放在缓存中
	 * @author bigd
	 * @param userIdAppkey
	 * @return
	 */
	@Override
	public Set<String> getResourceUrlByUserIdandAppkey(String userIdAppkey) {
		Set<String> urlSet = null;
		Map<String,String> map = new HashMap<String,String>();
		if(StringUtils.isEmpty(userIdAppkey)){
			return urlSet;
		}else{
			String userId = userIdAppkey.split(PtxtglConstant.USERID_APPKEY_SEPARATOR)[0];
			String appkey = userIdAppkey.split(PtxtglConstant.USERID_APPKEY_SEPARATOR)[1];
			if(StringUtils.isEmpty(userId)){
				return urlSet;
			}
			if(StringUtils.isEmpty(appkey)){
				return urlSet;
			}
			map.put("userid", userId);
			map.put("appkey", appkey);
			urlSet = this.resourceMapper.getResourceUrlByUserIdAndAppkey(map);			
		}
		return urlSet;
	}
}
