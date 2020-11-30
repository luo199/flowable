package com.huasisoft.flow.business.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.huasisoft.flow.business.service.BusinessManageService;
import com.huasisoft.flow.business.vo.BusinessManage;
import com.huasisoft.flow.common.vo.JsonResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @Auther: yn
 * @Description:
 * @Date 2020/8/14
 */
@RestController
@RequestMapping("/api/business/manage")
@Api("业务管理接口")
public class BusinessManageController {

    @Autowired
    private BusinessManageService businessManageService;

    
    @ApiOperation("获取业务按钮配置信息")
    @GetMapping("actions/{businessCode}")
    public JsonResult<BusinessManage> actions(
    		@PathVariable("businessCode") @ApiParam(value="businessCode",name="业务Code",required=true,type="path")String businessCode) {
    	return new JsonResult<BusinessManage>(businessManageService.getActions(businessCode));
    }
    
    @ApiOperation("获取业务指定流程节点视图信息")
    @GetMapping("views/{businessCode}")
    public JsonResult<BusinessManage> views(
    		@PathVariable("businessCode") @ApiParam(value="businessCode",name="业务Code",required=true,type="path")String businessCode) {
    	return new JsonResult<BusinessManage>(businessManageService.getViews(businessCode));
    }
   
    @ApiOperation("获取业务指定流程节点时限信息")
    @GetMapping("timeLimit/{businessCode}")
    public JsonResult<BusinessManage> timeLimit(
    		@PathVariable("businessCode") @ApiParam(value="businessCode",name="业务Code",required=true,type="path")String businessCode) {
    	return new JsonResult<>(businessManageService.getTimiLimit(businessCode));
    }
    @ApiOperation("获取业务指定流程节点人员配置信息")
    @GetMapping("executors/{businessCode}")
    public JsonResult<BusinessManage> executors(
    		@PathVariable("businessCode") @ApiParam(value="businessCode",name="业务Code",required=true,type="path")String businessCode) {
    	return new JsonResult<>(businessManageService.getExecutors(businessCode));
    }
    

}
