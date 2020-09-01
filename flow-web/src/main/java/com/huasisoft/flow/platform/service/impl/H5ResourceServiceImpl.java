package com.huasisoft.flow.platform.service.impl;

import static com.huasisoft.h1.constant.ACOperationConst.OPERATION_SYSTEM_BROWSE;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.huasisoft.flow.platform.service.ResourceService;
import com.huasisoft.flow.platform.vo.Resource;
import com.huasisoft.h1.model.ACResource;
import com.huasisoft.h1.service.AccessControlService;

@Service("H5ResourceService")
public class H5ResourceServiceImpl implements ResourceService{

	@Reference
	private AccessControlService accessControlService;
	
	@Value("${rootResourceID}")  
	String rootResourceID; 
	
	@Override
	public List<Resource> leftMenu(String currentUserId) {
		List<ACResource> acResources = null;
		try {
			acResources = accessControlService.getAllSubResources(currentUserId, OPERATION_SYSTEM_BROWSE, rootResourceID);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(acResources==null) {
			return Collections.<Resource>emptyList();
		}else {
			return acResources.stream().map(acResource->acResourceToResource(acResource)).collect(Collectors.toList());
		}
	}
	
	private Resource acResourceToResource(ACResource acResource) {
		Resource resource = new Resource();
		BeanUtils.copyProperties(acResource, resource);
		return resource;
	}
	
}
