package com.huasisoft.flow.platform.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.huasisoft.flow.platform.service.PlatformEngine;
import com.huasisoft.flow.platform.service.ResourceService;
import com.huasisoft.flow.platform.service.RoleService;
import com.huasisoft.flow.platform.service.UserService;

@Service
public class PlatformEngineImpl implements PlatformEngine{

	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private ResourceService resourceService;
	
	@Override
	public UserService getUserService() {
		return this.userService;
	}

	@Override
	public RoleService getRoleService() {
		return this.roleService;
	}

	@Override
	public ResourceService getResourceService() {
		return this.resourceService;
	}
	
	
}
