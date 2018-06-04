/**
 * @author CHENYB
 * @(#)ModuleServiceImp.java 2016年1月19日
 * 
 * wdcloud 版权所有2014~2016。
 */
package com.wdcloud.ptxtgl.module.service.impl;

import com.wdcloud.common.constant.UicConstants;
import com.wdcloud.common.exception.CommonException;
import com.wdcloud.common.service.CommonGeneratorService;
import com.wdcloud.framework.core.util.I18NMessageReader;
import com.wdcloud.ptxtgl.constant.PtxtglConstant;
import com.wdcloud.ptxtgl.module.entity.Module;
import com.wdcloud.ptxtgl.module.mapper.ModuleMapper;
import com.wdcloud.ptxtgl.module.service.ModuleService;
import com.wdcloud.ptxtgl.resource.entity.Resource;
import com.wdcloud.ptxtgl.resource.mapper.ResourceMapper;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author CHENYB
 * @since 2016年1月19日
 */
public class ModuleServiceImp implements ModuleService {

	private static final Log logger = LogFactory.getLog(ModuleServiceImp.class);

	@Autowired
	private ModuleMapper moduleMapper;
	@Autowired
	private ResourceMapper resourceMapper;
	@Autowired
	private CommonGeneratorService commonGeneratorService;

	/**
	 * @see com.wdcloud.ptxtgl.module.service.ModuleService#getModuleById(java.lang.String)
	 * @since 2016年2月18日 下午2:13:52
	 */
	@Override
	public Module getModuleById(String moduleid) {
		return this.moduleMapper.selectModuleById(moduleid);
	}

	/**
	 * @see com.wdcloud.ptxtgl.module.service.ModuleService#loadModules(int, int, com.wdcloud.ptxtgl.module.entity.Module)
	 * @since 2016年1月25日 上午11:04:27
	 */
	@Override
	public Map<String, Object> loadModules(int pageStart, int pageSize, Module module) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> params = this.assembleModuleConditions(pageStart, pageSize, module);
		int records = this.moduleMapper.selectModulesCount(params);
		List<Module> rows = this.moduleMapper.selectModules(params);
		result.put(PtxtglConstant.RETURN_RECORDS, records);
		result.put(PtxtglConstant.RETURN_ROWS, rows);
		return result;
	}

	/**
	 * 组装菜单查询条件
	 * @param pageStart 起始页
	 * @param pageSize	 每页行数
	 * @param module	 菜单实体
	 * @return  map
	 * @since 2016年1月25日 上午11:21:26
	 */
	private Map<String, Object> assembleModuleConditions(int pageStart, int pageSize, Module module) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(PtxtglConstant.PAGE_START, pageStart);
		params.put(PtxtglConstant.PAGE_SIZE, pageSize);
		if (StringUtils.isNotEmpty(module.getModulename())) {
			params.put("modulename", module.getModulename());
		}
		if (StringUtils.isNotEmpty(module.getModulecode())) {
			params.put("modulecode", module.getModulecode());
		}
		if (module.getModulelevel() != null) {
			params.put("modulelevel", module.getModulelevel());
		}
		if (StringUtils.isNotEmpty(module.getAppkey())) {
			params.put("appkey", module.getAppkey());
		}
		return params;
	}

	/**
	 * @see com.wdcloud.ptxtgl.module.service.ModuleService#saveModule(com.wdcloud.ptxtgl.module.entity.Module)
	 * @since 2016年1月20日 下午5:54:00
	 */
	@Override
	public Map<String, Object> saveModule(Module module) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(PtxtglConstant.RETURN_RESULT, false);
		if (this.moduleCodeExist(module.getModulecode(), module.getId())) {
			result.put(PtxtglConstant.RETURN_MSG,
					I18NMessageReader.getMessage("ptxtgl.action.module.modulecode.exist", module.getModulecode()));
			return result;
		}
		Date date = new Date();
		int rs;
		try {
			module.setDelflag("A");
			module.setCreatertime(date);
			module.setOperatetime(date);
			Long version = this.commonGeneratorService.nextVersion("t_sysmgr_module");
			String id = this.commonGeneratorService.nextPrimaryId("t_sysmgr_module");
			module.setId(id);
			module.setVersions(UicConstants.getStringFromObject(version));
			//修正不选父菜单报错的Bug
			if (StringUtils.isBlank(module.getParentid())) {
				module.setParentid(null);
			}
			this.setDisplayorder(module);
			rs = this.moduleMapper.insertModule(module);
			if (rs == 0) {
				result.put(PtxtglConstant.RETURN_MSG, I18NMessageReader.getMessage("ptxtgl.action.module.create.error"));
				return result;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new CommonException(I18NMessageReader.getMessage("operate.error"));
		}
		result.put(PtxtglConstant.RETURN_RESULT, true);
		return result;
	}

	/**
	 * @see com.wdcloud.ptxtgl.module.service.ModuleService#moduleCodeExist(java.lang.String, java.lang.String)
	 * @since 2016年1月22日 上午9:56:19
	 */
	@Override
	public boolean moduleCodeExist(String modulecode, String moduleid) {
		int count = this.moduleMapper.getModuleCodeCount(modulecode, moduleid);
		return count > 0;
	}

	/**
	 * @see com.wdcloud.ptxtgl.module.service.ModuleService#deleteModules(java.lang.String, java.lang.String)
	 * @since 2016年1月20日 下午6:16:48
	 */
	@Override
	public Map<String, Object> deleteModules(String moduleids, String opratercode) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(PtxtglConstant.RETURN_RESULT, false);
		if (StringUtils.isBlank(moduleids)) {
			result.put(PtxtglConstant.RETURN_MSG, I18NMessageReader.getMessage("ptxtgl.action.delete.unselect"));
			return result;
		}
		String[] ids = moduleids.split(",");
		try {
			for(String s: ids) {
				this.deleteModuleById(s, opratercode);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CommonException(I18NMessageReader.getMessage("operate.error"));
		}
		result.put(PtxtglConstant.RETURN_RESULT, true);
		return result;

	}

	/**
	 * 通过id删除Module
	 * @param moduleid   菜单唯一标识
	 * @param opratercode	操作人编码
	 * @throws Exception
     */
	private void deleteModuleById(String moduleid, String opratercode) throws Exception {
		try {
			Module module = this.moduleMapper.selectModuleById(moduleid);
			if (module == null) {
				return;
			}
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("moduleid", moduleid);
			params.put(UicConstants.FIELD_DELFLAG, "D");
			params.put(UicConstants.FIELD_OPERATERCODE, opratercode);
			params.put(UicConstants.FIELD_OPERATETIME, new Date());
			params.put(UicConstants.FIELD_VERSIONS, this.commonGeneratorService.nextVersion("t_sysmgr_module"));
			this.moduleMapper.deleteModuleById(params);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new CommonException(I18NMessageReader.getMessage("operate.error"));
		}
	}

	/**
	 * @see com.wdcloud.ptxtgl.module.service.ModuleService#updateModule(com.wdcloud.ptxtgl.module.entity.Module)
	 * @since 2016年1月20日 下午6:41:22
	 */
	@Override
	public Map<String, Object> updateModule(Module module) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(PtxtglConstant.RETURN_RESULT, false);
		if (module == null || module.getId() == null) {
			result.put(PtxtglConstant.RETURN_MSG, I18NMessageReader.getMessage("ptxtgl.action.module.update.param.error"));
			return result;
		}
		int rs;
		try {
			Module mod = this.moduleMapper.selectModuleById(module.getId());
			if (mod == null) {
				result.put(PtxtglConstant.RETURN_MSG, I18NMessageReader.getMessage("ptxtgl.action.module.notexist"));
				return result;
			}
			if (this.moduleCodeExist(module.getModulecode(), module.getId())) {
				result.put(PtxtglConstant.RETURN_MSG,
						I18NMessageReader.getMessage("ptxtgl.action.module.modulecode.duplicate", module.getModulecode()));
				return result;
			}
			module.setDelflag("U");
			module.setOperatetime(new Date());
			Long version = this.commonGeneratorService.nextVersion("t_sysmgr_module");
			module.setVersions(UicConstants.getStringFromObject(version));
			this.setDisplayorder(module);
			rs = this.moduleMapper.updateModule(module);
			if (rs == 0) {
				result.put(PtxtglConstant.RETURN_MSG, I18NMessageReader.getMessage("ptxtgl.action.module.update.error"));
				return result;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new CommonException(I18NMessageReader.getMessage("operate.error"));
		}
		result.put(PtxtglConstant.RETURN_RESULT, true);
		return result;
	}

	/**
	 * @see com.wdcloud.ptxtgl.module.service.ModuleService#loadAllowedResourcesForMenu(int, int, com.wdcloud.ptxtgl.resource.entity.Resource)
	 * @since 2016年1月22日 下午5:22:02
	 */
	@Override
	public Map<String, Object> loadAllowedResourcesForMenu(int pageStart, int pageSize, Resource resource) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> params = this.assembleModuleResourceConditions(pageStart, pageSize, resource);
		int records = this.moduleMapper.selectAllowedResourcesCountForMenu(params);
		List<Resource> rows = this.moduleMapper.selectAllowedResourcesForMenu(params);
		result.put(PtxtglConstant.RETURN_RECORDS, records);
		result.put(PtxtglConstant.RETURN_ROWS, rows);
		return result;
	}

	/**
	 * 组装菜单关联资源时的条件
	 * @param pageStart		起始页
	 * @param pageSize		每页行数
	 * @param resource		资源实体对象
	 * @return map
	 * @since 2016年1月22日 下午5:45:05
	 */
	private Map<String, Object> assembleModuleResourceConditions(int pageStart, int pageSize, Resource resource) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(PtxtglConstant.PAGE_START, pageStart);
		params.put(PtxtglConstant.PAGE_SIZE, pageSize);
		if (StringUtils.isNotEmpty(resource.getResourcename())) {
			params.put("resourcename", resource.getResourcename());
		}
		if (StringUtils.isNotEmpty(resource.getResourcecode())) {
			params.put("resourcecode", resource.getResourcecode());
		}
		if (StringUtils.isNotEmpty(resource.getAppkey())) {
			params.put("appkey", resource.getAppkey());
		}
		return params;
	}

	/**
	 * @see com.wdcloud.ptxtgl.module.service.ModuleService#saveResourceForMenu(java.util.Map)
	 * @since 2016年1月22日 下午7:07:35
	 */
	@Override
	public void saveResourceForMenu(Map<String, Object> params) {
		String moduleid = UicConstants.getStringFromObject(params.get("moduleid"));
		Module module = this.moduleMapper.selectModuleById(moduleid);
		if (module == null) {
			throw new CommonException(I18NMessageReader.getMessage("ptxtgl.action.module.notexist"));
		}
		String resourceid = UicConstants.getStringFromObject(params.get("resourceid"));
		Resource resource = this.resourceMapper.selectResourceById(resourceid);
		if (resource == null) {
			throw new CommonException(I18NMessageReader.getMessage("ptxtgl.action.resource.notexist"));
		}
		params.put(UicConstants.FIELD_DELFLAG, "U");
		params.put(UicConstants.FIELD_OPERATETIME, new Date());
		params.put(UicConstants.FIELD_VERSIONS, this.commonGeneratorService.nextVersion("t_sysmgr_module"));
		this.moduleMapper.saveResourceForMenu(params);
	}

	/**
	 * @see com.wdcloud.ptxtgl.module.service.ModuleService#loadModulesByAppkey(java.lang.String)
	 * @since 2016年3月3日 下午2:30:43
	 */
	@Override
	public Map<String, Object> loadModulesByAppkey(String appkey) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, String>> list = this.moduleMapper.selectModulesByAppkey(appkey);
		result.put(PtxtglConstant.RETURN_DATA, list);
		return result;
	}

	/**
	 * @see com.wdcloud.ptxtgl.module.service.ModuleService#hasRootModule(java.lang.String)
	 * @since 2016年4月27日 下午9:15:45
	 */
	@Override
	public boolean hasRootModule(String appkey) {
		return this.moduleMapper.selectRootModuleCount(appkey) > 0;
	}

	/**
	 * 设置菜单展示顺序，如果菜单展示顺序为空{
	 *     如果为根菜单，设置为0
	 *     否则，设置为同级max(displayorder) +1
	 * }
     */
	public void setDisplayorder(Module module){
		if(StringUtils.isEmpty(module.getDisplayorder())){
			int order = 0;
			if(StringUtils.isNotEmpty(module.getParentid())){
				order = this.moduleMapper.getMaxDisplayOrderByParentId(module.getParentid());
			}
			module.setDisplayorder(String.valueOf(order));
		}
	}
}
