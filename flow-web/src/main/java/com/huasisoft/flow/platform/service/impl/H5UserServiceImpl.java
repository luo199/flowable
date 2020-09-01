package com.huasisoft.flow.platform.service.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.huasisoft.flow.platform.service.UserService;
import com.huasisoft.flow.platform.vo.Dept;
import com.huasisoft.flow.platform.vo.Group;
import com.huasisoft.flow.platform.vo.Org;
import com.huasisoft.flow.platform.vo.Person;
import com.huasisoft.flow.platform.vo.Unit;
import com.huasisoft.flow.platform.vo.UnitType;
import com.huasisoft.h1.model.ORGPerson;
import com.huasisoft.h1.model.ORGUnit;
import com.huasisoft.h1.service.OrgUnitService;
import com.huasisoft.h1.service.PersonService;

@Service("H5UserService")
public class H5UserServiceImpl implements UserService {
	
	@Reference
	private OrgUnitService orgUnitService;
	@Reference
	private PersonService personService;

	@Override
	public List<Unit> listUnit(String pid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Unit> listUnitCascad(String pid) {
		// TODO Auto-generated method stub
		return null;
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
			return orgUnitToPerson(orgPerson);
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
			return orgPersons.stream().map(orgPerson-> orgUnitToPerson(orgPerson)).collect(Collectors.toList());
		}
	}
	
	private Person orgUnitToPerson(ORGUnit orgUnit){
		Person person = new Person();
		BeanUtils.copyProperties(orgUnit, person);
		return person;
	}
	
	private Unit orgUnitToUnit(ORGUnit orgUnit) {
		Unit unit = new Unit();
		BeanUtils.copyProperties(orgUnit, unit);
		unit.setType(orgUnit.getOrgType());
		return unit;
	}

}
