package com.huasisoft.flow.platform.service;
/**
 * 人员。资源、角色等平台功能的统一入口
 * @author Administrator
 *
 */
public interface PlatformEngine {

	/**
	 * 人员相关接口
	 * @return
	 */
	UserService getUserService();
	/**
	 * 角色接口
	 * @return
	 */
	RoleService getRoleService();
	/**
	 * 资源接口
	 * @return
	 */
	ResourceService getResourceService();
	
}
