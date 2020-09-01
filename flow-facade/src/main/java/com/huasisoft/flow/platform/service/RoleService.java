package com.huasisoft.flow.platform.service;

import java.util.List;

import com.huasisoft.flow.platform.vo.Person;
import com.huasisoft.flow.platform.vo.RoleNode;
import com.huasisoft.flow.platform.vo.Unit;

/**
 * 角色接口
 * RoleService
 * @author Administrator
 *
 */
public interface RoleService {

	/**
	 * 获取全部角色
	 * @return
	 */
	List<RoleNode> listAllRole();
	/**
	 * 获取子角色
	 * @param roleId
	 * @return
	 */
	List<RoleNode> listByPid(String roleId);
	
	/**
	 * 获取角色成员（包含全部类型机构节点）
	 * @param roleIds
	 * @return
	 */
	List<Unit> getRoleUnits(String... roleIds);
	/**
	 * 获取指定机构节点下的角色成员（包含全部类型机构节点）
	 * @param unitId
	 * @param roleIds
	 * @return
	 */
	List<Unit> getRoleUnits(String unitId,String... roleIds);
	
	/**
	 * 获取角色成员（其他类型机构节点都转换成人员）
	 * @param pid
	 * @return
	 */
	List<Person> getRolePersons(String... roleIds);
	/**
	 * 获取指定机构节点下的角色成员（其他类型机构节点都转换成人员）
	 * @param unitId
	 * @param roleIds
	 * @return
	 */
	List<Person> getRolePersons(String unitId,String... roleIds);
	
	/**
	 * 获取角色信息
	 * @param roleId
	 * @return
	 */
	RoleNode getRoleNode(String roleId);
	
}
