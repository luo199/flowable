package com.huasisoft.flow.platform.service;

import java.util.List;

import com.huasisoft.flow.platform.vo.Dept;
import com.huasisoft.flow.platform.vo.Group;
import com.huasisoft.flow.platform.vo.Org;
import com.huasisoft.flow.platform.vo.Person;
import com.huasisoft.flow.platform.vo.Unit;
/**
 * 用户体系接口
 * @author Administrator
 *
 */
public interface UserService {
	
	/**
	 * 根据上级节点查下级节点（只查下一级）
	 * @param pid
	 * @return
	 */
	List<Unit> listUnit(String pid); 
	/**
	 * 根据上级节点查下级节点（递推查询全部下级）
	 * @param pid
	 * @return
	 */
	List<Unit> listUnitCascad(String pid);
	
	/**
	 * 获取unit
	 * @param unitId
	 * @return
	 */
	Unit getUnit(String unitId);
	
	/**
	 * 获取机构
	 * @param orgId
	 * @return
	 */
	Org getOrg(String orgId);
	/**
	 * 获取部门
	 * @param deptId
	 * @return
	 */
	Dept getDept(String deptID);
	/**
	 * 获取用户组
	 * @param groupId
	 * @return
	 */
	Group getGroup(String groupId);
	/**
	 * 根据主键查询用户信息
	 * @param personId
	 * @return
	 */
	Person getPerson(String personId);
	
	/**
	 * 查询指定机构节点下面的全部人员
	 * @param unitIds
	 * @return
	 */
	List<Person> getAllPersonsByUnitId(List<String> unitIds);
	
}
