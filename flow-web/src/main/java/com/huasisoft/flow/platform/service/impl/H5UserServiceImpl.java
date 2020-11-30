package com.huasisoft.flow.platform.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.huasisoft.flow.platform.service.UserService;
import com.huasisoft.flow.platform.vo.Dept;
import com.huasisoft.flow.platform.vo.Group;
import com.huasisoft.flow.platform.vo.Org;
import com.huasisoft.flow.platform.vo.Person;
import com.huasisoft.flow.platform.vo.Position;
import com.huasisoft.flow.platform.vo.Unit;
import com.huasisoft.flow.platform.vo.UnitType;
import com.huasisoft.h1.model.ORGDepartment;
import com.huasisoft.h1.model.ORGGroup;
import com.huasisoft.h1.model.ORGOrganization;
import com.huasisoft.h1.model.ORGPerson;
import com.huasisoft.h1.model.ORGPosition;
import com.huasisoft.h1.model.ORGUnit;
import com.huasisoft.h1.service.OrgUnitService;
import com.huasisoft.h1.service.OrganizationService;
import com.huasisoft.h1.service.PersonService;

@Service("H5UserService")
public class H5UserServiceImpl implements UserService {
	
	@Reference
	private OrgUnitService orgUnitService;
	@Reference
	private PersonService personService;
	@Reference
	private OrganizationService organizationService;

	@Override
	public List<Unit> listUnit(String pid) {
		List<ORGUnit> units = new ArrayList<>();  
		try {
			units = orgUnitService.getSubUnits(pid, UnitType.allUnitTypes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(units==null) {
			 return Collections.<Unit>emptyList();
		}else {
			return units.stream().map(unit-> orgUnitToUnit(unit)).collect(Collectors.toList());
		}
	}

	@Override
	public List<Unit> treeSearch(String name) {
		List<ORGUnit> units = new ArrayList<>();  
		 try {
			 units = orgUnitService.findByNameOrgTypes(name, Arrays.asList(UnitType.allUnitTypes()));
			 String path = null;
			 Set<String> pids = new HashSet<>();
			 for (ORGUnit orgUnit : units) {
				 path = orgUnit.getPath();
				 if(StringUtils.isNotBlank(path)) {
					for (String  str: path.split(",")) {
						 pids.add(str);
					}
				 }
			 }
			 if(pids.size()>0) {
				 units.addAll(orgUnitService.getByIds(pids.toArray(new String[pids.size()])));
			 }
			 /*units.sort(new Comparator<ORGUnit>() {
				@Override
				public int compare(ORGUnit o1, ORGUnit o2) {
					return o1.getTabIndex()-o2.getTabIndex();
				}
			});*/
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 if(units==null) {
			 return Collections.<Unit>emptyList();
		 }else {
			 return units.stream().sorted(Comparator.comparing(ORGUnit::getTabIndex)).map(unit-> orgUnitToUnit(unit)).collect(Collectors.toList());
		 }
	}

	@Override
	public List<Unit> treeNodes(String id) {
		if(StringUtils.isBlank(id)) {
			List<ORGOrganization> units = new ArrayList<>();  
			 try {
				 units = organizationService.listAllOrganizations();

			 } catch (Exception e) {
				 // TODO Auto-generated catch block
				 e.printStackTrace();
			 }
			 if(units==null) {
				 return Collections.<Unit>emptyList();
			 }else {
				 return units.stream().map(unit-> orgUnitToUnit(unit)).collect(Collectors.toList());
			 }
		}else {
			return listUnit(id);
		}
	}

	@Override
	public Unit getUnit(String unitId) {
		ORGUnit orgUnit = null;
		try {
			orgUnit = orgUnitService.getOrgUnit(unitId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(orgUnit==null) {
			return null;
		}else {
			return orgUnitToUnit(orgUnit);
		}
	}


	@Override
	public Org getOrg(String orgId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Dept getDept(String deptID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Group getGroup(String groupId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Person getPerson(String personId) {
		ORGPerson orgPerson = null;
		try {
			orgPerson = personService.getByIdNum(personId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(orgPerson==null) {
			return null;
		}else {
			return orgPersonToPerson(orgPerson);
		}
	}
	
	@Override
	public List<Person> getAllPersonsByUnitId(List<String> unitIds) {
		List<ORGUnit> orgPersons = null;
		try {
			orgPersons = orgUnitService.listOrgUnitInIDsByOrgType(unitIds, Arrays.asList(UnitType.person.getName()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(orgPersons==null) {
			return Collections.<Person>emptyList();
		}else{
			return orgPersons.stream().map(orgPerson-> orgPersonToPerson(orgPerson)).collect(Collectors.toList());
		}
	}
	
	private Person orgPersonToPerson(ORGUnit orgUnit){
		Person person = new Person();
		BeanUtils.copyProperties(orgUnit, person);
		return person;
	}
	private Org orgOrganizationToOrg(ORGOrganization orgUnit){
		Org org = new Org();
		BeanUtils.copyProperties(orgUnit, org);
		return org;
	}
	private Dept orgDepartmentToDept(ORGDepartment orgUnit){
		Dept dept = new Dept();
		BeanUtils.copyProperties(orgUnit, dept);
		return dept;
	}
	private Group orgGroupToGroup(ORGGroup orgUnit){
		Group group = new Group();
		BeanUtils.copyProperties(orgUnit, group);
		return group;
	}
	private Position orgPositionToPosition(ORGPosition orgUnit){
		Position position = new Position();
		BeanUtils.copyProperties(orgUnit, position);
		return position;
	}
	
	private Unit orgUnitToUnit(ORGUnit orgUnit) {
		Unit unit = null;
		if(orgUnit instanceof ORGOrganization) {
			unit = orgOrganizationToOrg((ORGOrganization)orgUnit);
		}else if(orgUnit instanceof ORGDepartment) {
			unit = orgDepartmentToDept((ORGDepartment)orgUnit);
		}else if(orgUnit instanceof ORGGroup) {
			unit = orgGroupToGroup((ORGGroup)orgUnit);
		}else if(orgUnit instanceof ORGPosition) {
			unit = orgPositionToPosition((ORGPosition)orgUnit);
		//}else if(orgUnit instanceof OrgTree) {
			
		}else if(orgUnit instanceof ORGPerson) {
			unit = orgPersonToPerson((ORGPerson)orgUnit);
		}else {
			unit = new Unit();
			BeanUtils.copyProperties(orgUnit, unit);
			unit.setType(orgUnit.getOrgType());
		}
		return unit;
	}

}
