package com.huasisoft.flow;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.huasisoft.flow.platform.vo.Resource;
import com.huasisoft.flow.test.entity.TestUser;
import com.huasisoft.flow.test.service.TestUserService;
import com.huasisoft.h1.model.ACResource;

import io.swagger.annotations.ApiModelProperty;

public class Test1 {
	
	
	
	public static void main(String[] args) {
		ACResource acResource = new ACResource();
		acResource.setId("id");
		acResource.setName("name");
		acResource.setAliasName("aliasName");
		acResource.setResourceIcon("resourceIcon");
		acResource.setResourceUrl("resourceUrl");
		acResource.setDescription("description");
		acResource.setTabIndex(111);
		acResource.setCustomID("customID");
		acResource.setPath("path");
		acResource.setParentID("parentID");
		Resource resource = new Resource();
		resource.setId(acResource.getId());
		BeanUtils.copyProperties(acResource, resource);
		System.out.println(resource);
	}
}
