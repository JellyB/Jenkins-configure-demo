package com.wdcloud.ptxtgl.base.service.imp;

import com.wdcloud.framework.core.util.I18NMessageReader;
import com.wdcloud.framework.core.util.PropertyXmlMgr;
import com.wdcloud.ptxtgl.base.entity.Menu;
import com.wdcloud.ptxtgl.base.mapper.MenuMapper;
import com.wdcloud.ptxtgl.base.service.MenuService;
import com.wdcloud.ptxtgl.permissionResource.service.PermissionResourceService;
import com.wdcloud.ptxtgl.rolePermission.service.RolePermissionService;
import com.wdcloud.ptxtgl.userRole.service.UserRoleService;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MenuServiceImpl implements MenuService{
	
	private static Logger logger = LoggerFactory.getLogger(MenuServiceImpl.class);
	
	private String  PTXTGL_MODULE = PropertyXmlMgr.getString("CONF_COMMON", "PTXTGL_MODULE");
	
	@Autowired
	private MenuMapper menuMapper;
	
	@Autowired
	private UserRoleService userRoleService;
	
	@Autowired
	private RolePermissionService rolePermissionService;
	
	@Autowired
	private PermissionResourceService permissionResourceService;
	
	/**
	 * 获取系统的所有菜单列表
	 * @author bigd
	 * @param appkey
	 * @return
	 */	
	@Override
	public Menu getSystemMenus(String appkey) {
		Menu root = null;
		try {
			root = this.getRootMenu(appkey);
			if (root.isResult()) {
				this.traversalParentMenu(root, appkey);
			} 
		} catch (Exception e) {
			root = new Menu();
			root.setResult(Menu.FAILURE);
			String errMsg = I18NMessageReader.getMessage("ptxtgl.getroot.error");
			if(null == root.getMsg()){
				root.setMsg(errMsg);
			}
			logger.error(errMsg + appkey, e);
		}
		return root;
	}
	
	/**
	 * 遍历根菜单Root下的menu及menu下的孩子们
	 * @author bigd
	 * @param parentMenu
	 * @param appkey
	 * @param systemMenus
	 * @return
	 */
	public void traversalParentMenu(Menu parentMenu, String appkey){
		Map<String,String> map = new HashMap<String,String>();
		map.put("appkey",  appkey);
		map.put("parentId", parentMenu.getId());
		List<Menu> childrenMenus = null;
		try {
			childrenMenus = this.menuMapper.getChildrenMenus(map);
			if(childrenMenus.size() > 0){
				parentMenu.setChild(childrenMenus);
				for(Menu menu : childrenMenus){
					/*menu.setDisplay(Menu.FAILURE);*/
					menu.setAuth(Menu.FAILURE);
					menu.setRoot(Menu.FAILURE);					
					menu.setParent(parentMenu);
					menu.setParentId(parentMenu.getId());
					this.traversalParentMenu(menu, appkey);
				}
			}
		} catch (Exception e) {
			logger.error(I18NMessageReader.getMessage("ptxtgl.getchildmenus.error"), e);
		}		
	}
	
	/**
	 * 获取 appkey 下的根节点
	 * @author bigd
	 * @param appkey
	 * @return
	 */
	private Menu getRootMenu(String appkey) {
		Menu root = null;
		try {
			root = this.menuMapper.getRootMenu(appkey);
			if(null == root){
				root = new Menu();
				String errMsg = I18NMessageReader.getMessage("ptxtgl.getroot.noexist");
				root.setResult(Menu.FAILURE);
				root.setMsg(errMsg);
				logger.error(errMsg + appkey);
				return root;
			}else{
				root.setAuth(Menu.SUCCESS);
				root.setParent(null);
				root.setParentId(null);
				root.setRoot(Menu.SUCCESS);
				root.setDisplay(Menu.FAILURE);
				root.setResult(Menu.SUCCESS);
			}			
		} catch (Exception e) {
			root = new Menu();
			root.setResult(Menu.FAILURE);
			if( e.getCause() != null && e.getCause() instanceof TooManyResultsException ){
				String errMsg = I18NMessageReader.getMessage("ptxtgl.getroot.toomany.error");
    			root.setMsg(errMsg);
    			logger.error(errMsg + appkey, e);
            }else{
            	String errMsg = I18NMessageReader.getMessage("ptxtgl.getroot.error");
    			root.setMsg(errMsg);
    			logger.error(errMsg + appkey, e);
            }
			
		}
		return root;
	}

	/**
	 * 获取指定用户的菜单
	 * 1、获取appkey下根节点root菜单
	 * 2、构造appkey下的菜单树
	 * 3、遍历appkey下的菜单树，对菜单赋权处理
	 * 4、删减appkey下没有赋权的菜单，返回个人树
	 * @author bigd
	 * @param userid
	 * @return
	 */
	@Override
	public Menu getMenusByUser(String userid, String appkey) {
		/*Map<String,String> dataMap = new HashMap<String,String>();
		dataMap.put("userid", userid);
		dataMap.put("appkey", appkey);*/
		Set<String> menuCodes = null;
		Menu root = null;
		try {
			root = this.getSystemMenus(appkey);
			if(root.isResult()){
				boolean adminAuth = this.checkRolesContanisAdministratorByUserId(userid, appkey);
				if(adminAuth){
					this.dealAdminMenus(root, appkey);
				}else{
					menuCodes = this.getMenuCodesByUserIdAndAppkey(userid, appkey);
					/*menuCodes = this.menuMapper.getMenuCodesByUserIdandAppkey(dataMap);*/
					if(menuCodes.size() == 0){
						this.deprivatedAuthToChildMenu(root);
					}else{
						this.dealUserMenusAuths(root,menuCodes);
					}					
					this.buildUserMenusTree(root);
				}				
				root.setMsg(I18NMessageReader.getMessage("ptxtgl.getusermenus.success"));
			}else{
				return root;
			}
		} catch (Exception e) {
			logger.error(I18NMessageReader.getMessage("ptxtgl.getusermenus.error"), e);
		}		
		return root;
	}

	
	/**
	 * 处理管理员菜单树
	 * @param parentMenu
	 * @param appkey
	 */
	private void dealAdminMenus(Menu parentMenu, String appkey) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("appkey",  appkey);
		map.put("parentId", parentMenu.getId());
		List<Menu> childrenMenus = null;
		try {
			childrenMenus = this.menuMapper.getChildrenMenus(map);
			if(childrenMenus.size() > 0){
				parentMenu.setChild(childrenMenus);
				for(Menu menu : childrenMenus){
					menu.setDisplay(Menu.SUCCESS);
					menu.setAuth(Menu.SUCCESS);
					menu.setRoot(Menu.FAILURE);					
					menu.setParent(parentMenu);
					menu.setParentId(parentMenu.getId());
					this.dealAdminMenus(menu, appkey);
				}
			}
		} catch (Exception e) {
			logger.error("处理管理员菜单树异常：", e);
		}		
	}

	/**
	 * 用户菜单tree展示数据权限处理
	 * @author bigd
	 * @param menuList
	 * @param menuIds
	 * @return
	 */
	private void dealUserMenusAuths(Menu menu, Set<String> menuCodes) {
		try {
			Iterator<Menu> childMenus = menu.getChild().iterator();
			while (childMenus.hasNext()) {
				Menu child = childMenus.next();
				if (menuCodes.contains(child.getModulecode())) {
					child.setAuth(Menu.SUCCESS);
					child.setDisplay(Menu.SUCCESS);
					this.profitedParentByChild(menu);										
				} else {
					child.setAuth(Menu.FAILURE);
					child.setDisplay(Menu.FAILURE);
				}
				if (null != child.getChild()) {
					this.dealUserMenusAuths(child, menuCodes);
				}
			}
		} catch (Exception e) {
			logger.error(I18NMessageReader.getMessage("ptxtgl.dealusermenusauths.error"), e);
		}		
	}
	
	/**
	 * 构造用户个人菜单树，删减没有权限菜单
	 * @param root
	 * @author bigd
	 */
	private void buildUserMenusTree(Menu root){
		try {
			Iterator<Menu> childMenus = root.getChild().iterator();
			while (childMenus.hasNext()) {
				Menu child = childMenus.next();
				if (child.isAuth()) {
					if(null != child.getChild()){
						this.buildUserMenusTree(child);
					}					
				} else {
					childMenus.remove();
				}
			} 
		} catch (Exception e) {
			logger.error(I18NMessageReader.getMessage("ptxtgl.buildusermenustree.error"), e);
		}
	}
	
	/**
	 * 子菜单拥有访问权限，祖祖辈辈都有访问权限
	 * @author bigd
	 * @param menu
	 */
	private void profitedParentByChild(Menu menu){
		try {
			if (null != menu.getParent()) {
				menu.setAuth(Menu.SUCCESS);
				menu.setDisplay(Menu.SUCCESS);
				Menu parent = menu.getParent();
				if (!parent.isRoot()) {
					this.profitedParentByChild(parent);
				}
			} 
		} catch (Exception e) {
			logger.error("ptxtgl.profitparentmenu.error", e);
		}
	}
	
	/**
	 * 没收所有root孩子的访问权限
	 * @param root
	 * @author bigd
	 * 
	 */
	private void deprivatedAuthToChildMenu(Menu root) {
		try {
			Iterator<Menu> childMenus = root.getChild().iterator();
			while (childMenus.hasNext()) {
				Menu child = childMenus.next();
				child.setAuth(Menu.FAILURE);
				child.setDisplay(Menu.FAILURE);
				if (null != child.getChild()) {
					this.deprivatedAuthToChildMenu(child);
				}
			} 
		} catch (Exception e) {
			logger.error("ptxtgl.deprivatechildmenu.error", e);
		}
	}
	
	
	/**
	 * 查看此用户在此归属系统下是否拥有管理员角色
	 * @author bigd
	 * @param userid
	 * @param appkey
	 * @return
	 */
	private boolean checkRolesContanisAdministratorByUserId(String userid, String appkey){
		boolean result = false;
		String roleCode = PropertyXmlMgr.getString("CONF_COMMON", "ROLE_CODE_ADMINISTRATOR");
		Set<String> roleCodeSet = userRoleService.getRoleCodesByUserIdAndAppkey(userid, appkey);
		if(roleCodeSet.size() > 0){
			if(roleCodeSet.contains(roleCode)){
				result = true;
				return result;
			}
		}
		return result;
	}
	
	
	/**
	 * 获取用户分配菜单编码
	 * @author bigd
	 * @param userid
	 * @param appkey
	 * @return
	 */
	public Set<String> getMenuCodesByUserIdAndAppkey(String userid, String appkey){
		Set<String> menuCodeSet = new HashSet<String>();
		try {
			Set<String> roleSet = this.userRoleService.getRoleidsByUseridAndAppkey(userid, appkey);
			if(roleSet.size() > 0){
				Set<String> permissionSet = this.rolePermissionService.getPermissionIdsByRoleIdsAndAppkey(roleSet, appkey);
				if(permissionSet.size() > 0){
					Set<String> resourceSet = this.permissionResourceService
							.getResourceIdsByPermissionIdsAndAppkey(permissionSet, appkey,this.PTXTGL_MODULE);
					if(resourceSet.size() > 0){
						menuCodeSet = this.getMenuCodesByReourceIdsAndAppkey(resourceSet, appkey);
					}else{
						return menuCodeSet;
					}
				}else{
					return menuCodeSet;
				}
			}else{
				return menuCodeSet;
			}			
		} catch (Exception e) {
			logger.error(I18NMessageReader.getMessage("ptxtgl.getusermunucodes.error"), e);
		}
		return menuCodeSet;
	}
	
	/**
	 * 获取此系统下关联资源的菜单编码
	 * @author bigd
	 * @param resourceSet
	 * @param appkey
	 * @return menuCodeSet
	 */
	@Override
	public Set<String> getMenuCodesByReourceIdsAndAppkey(Set<String> resourceSet, String appkey) {
		Set<String> menuCodeSet = null;
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("resourceSet", resourceSet);
			map.put("appkey", appkey);
			menuCodeSet = this.menuMapper.getMenuCodesByReourceIdsAndAppkey(map);
		} catch (Exception e) {
			menuCodeSet = new HashSet<String>();
			logger.error(I18NMessageReader.getMessage("ptxtgl.getusermenucodesbyresourceid.error"), e);
		}
		return menuCodeSet;
	}

}
