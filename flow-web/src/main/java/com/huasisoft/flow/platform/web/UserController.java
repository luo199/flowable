package com.huasisoft.flow.platform.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.huasisoft.flow.common.vo.JsonResult;
import com.huasisoft.flow.platform.service.PlatformEngine;
import com.huasisoft.flow.platform.vo.Unit;
import com.huasisoft.flow.platform.vo.UnitType;
import com.huasisoft.h1.model.ORGOrganization;
import com.huasisoft.h1.model.ORGUnit;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
@RestController
@RequestMapping("/api/platform/user")
@Api("用户相关接口")
public class UserController {
	
	@Autowired
	private PlatformEngine platformEngine;
	
	 @GetMapping("findByName")
    @ApiOperation("机构树名字模糊查询")
    public JsonResult<List<Unit>> findByName(
    		@RequestParam(required=true) @ApiParam(required=true,value="查询关键字",name="name")String name) {
    	return new JsonResult<>(platformEngine.getUserService().treeSearch(name));
    }
	 @GetMapping("parentNodes")
	 @ApiOperation("全部机构节点")
	 public JsonResult<List<Unit>> parentNodes() {
		 return new JsonResult<>(platformEngine.getUserService().treeNodes(null));
	 }
	 
	 @GetMapping("findByPid")
	 @ApiOperation("查找子节点")
	 public JsonResult<Object> findByPid(
			 @RequestParam(required=true) @ApiParam(required=true,value="上级节点ID",name="pid")String pid) {
		 return new JsonResult<>(platformEngine.getUserService().listUnit(pid));
	 }
	 
}
