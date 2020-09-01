package com.huasisoft.flow.platform.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.alibaba.dubbo.config.annotation.Reference;
import com.huasisoft.flow.platform.msg.PlatformValidMessage;
import com.huasisoft.flow.platform.service.RoleService;
import com.huasisoft.flow.platform.service.UserService;
import com.huasisoft.flow.platform.vo.Person;
import com.huasisoft.flow.platform.vo.RoleNode;
import com.huasisoft.flow.platform.vo.Unit;
import com.huasisoft.h1.model.ACRoleNode;
import com.huasisoft.h1.model.ACRoleNodeMapping;
import com.huasisoft.h1.service.ACRoleNodeMappingService;
import com.huasisoft.h1.service.ACRoleNodeService;
import com.huasisoft.h1.service.OrgUnitService;
import com.huasisoft.h1.service.PersonService;

@Service("H5RoleService")
public class H5RoleServiceImpl implements RoleService {
	
	
	@Reference
	private ACRoleNodeService aCRoleNodeService;
	@Reference
	private ACRoleNodeMappingService acRoleNodeMappingService;
	@Reference
	private OrgUnitService orgUnitService;
	@Reference
	private PersonService personService;
	
	@Autowired
	private UserService userService;

	/*@Override
	public List<Unit> getUnits(String... roleIds) {
		
		try {
			
			aCRoleNodeService.listByOrgUnitID(arg0);
			aCRoleNodeService.listByParentID(arg0)
			aCRoleNodeService.listChildRoleNode(arg0)
			
			acRoleNodeMappingService.listByRoleNodeIDs(arg0)
			acRoleNodeMappingService.listByorgUnitIDAndRoleNodeIDs(arg0, arg1);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}*/

	@Override
	public List<RoleNode> listAllRole() {
		List<ACRoleNode> acRoleNodes = null;
		try {
			acRoleNodes = aCRoleNodeService.listAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(acRoleNodes==null) {
			return Collections.<RoleNode>emptyList();
		}else {
			return acRoleNodes.stream()
					.map(acRoleNode->acRoleNodeToRoleNode(acRoleNode))
					.collect(Collectors.toList());
		}
	}
	

	@Override
	public List<RoleNode> listByPid(String roleId) {
		List<RoleNode> result = new ArrayList<RoleNode>();
		
		List<ACRoleNode> acRoleNodes = null;
		try {
			acRoleNodes = aCRoleNodeService.listChildRoleNode(roleId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(acRoleNodes!=null) {
			result.addAll(acRoleNodes.stream()
					.map(acRoleNode->acRoleNodeToRoleNode(acRoleNode))
					.collect(Collectors.toList()));
		}
		return result;
	}

	@Override
	public List<Unit> getRoleUnits(String... roleIds) {
		Assert.notEmpty(roleIds,PlatformValidMessage.ROLE_ID_MUST_NOT_BE_NULL);
		List<ACRoleNodeMapping> mappings = null;
		try {
			 mappings = acRoleNodeMappingService.listByRoleNodeIDs(Arrays.asList(roleIds));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(mappings==null) {
			return Collections.<Unit>emptyList();
		}else {
			;
			return mappings.stream().map(mapping->userService.getUnit(mapping.getOrgUnitID())).collect(Collectors.toList());
		}
	}

	@Override
	public List<Unit> getRoleUnits(String unitId, String... roleIds) {
		Assert.hasText(unitId, PlatformValidMessage.UNIT_ID_MUST_NOT_BE_NULL);
		Assert.notEmpty(roleIds,PlatformValidMessage.ROLE_ID_MUST_NOT_BE_NULL);
		List<ACRoleNodeMapping> mappings = null;
		try {
			mappings = acRoleNodeMappingService.listByorgUnitIDAndRoleNodeIDs(unitId, new HashSet<>(Arrays.asList(roleIds)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(mappings==null) {
			return Collections.<Unit>emptyList();
		}else {
			return mappings.stream().map(mapping->userService.getUnit(mapping.getOrgUnitID())).collect(Collectors.toList());
		}
	}
	
	@Override
	public List<Person> getRolePersons(String... roleIds) {
		Assert.notEmpty(roleIds,PlatformValidMessage.ROLE_ID_MUST_NOT_BE_NULL);
		List<ACRoleNodeMapping> mappings = null;
		try {
			 mappings = acRoleNodeMappingService.listByRoleNodeIDs(Arrays.asList(roleIds));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(mappings==null) {
			return Collections.<Person>emptyList();
		}else {
			return userService.getAllPersonsByUnitId(mappings.stream()
					.map(mapping->mapping.getOrgUnitID())
					.collect(Collectors.toList()));
		}
	}
	
	@Override
	public List<Person> getRolePersons(String unitId, String... roleIds) {
		Assert.hasText(unitId, PlatformValidMessage.UNIT_ID_MUST_NOT_BE_NULL);
		Assert.notEmpty(roleIds,PlatformValidMessage.ROLE_ID_MUST_NOT_BE_NULL);
		List<ACRoleNodeMapping> mappings = null;
		try {
			mappings = acRoleNodeMappingService.listByorgUnitIDAndRoleNodeIDs(unitId, new HashSet<>(Arrays.asList(roleIds)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(mappings==null) {
			return Collections.<Person>emptyList();
		}else {
			return userService.getAllPersonsByUnitId(mappings.stream()
					.map(mapping->mapping.getOrgUnitID())
					.collect(Collectors.toList()));
		}
	}

	@Override
	public RoleNode getRoleNode(String roleId) {
		ACRoleNode acRoleNode = null;
		try {
			acRoleNode = aCRoleNodeService.get(roleId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(acRoleNode!=null) {
			return acRoleNodeToRoleNode(acRoleNode);
		}else {
			return null;
		}
		
	}

	
	public RoleNode acRoleNodeToRoleNode(ACRoleNode acRoleNode) {
		RoleNode roleNode = new RoleNode();
		BeanUtils.copyProperties(acRoleNode, roleNode);
		return roleNode;
	}

}
