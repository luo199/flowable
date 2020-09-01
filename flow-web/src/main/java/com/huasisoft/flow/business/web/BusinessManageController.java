package com.huasisoft.flow.business.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.huasisoft.flow.business.entity.BaseAction;
import com.huasisoft.flow.business.entity.BaseView;
import com.huasisoft.flow.business.entity.BusinessBase;
import com.huasisoft.flow.business.service.BusinessBaseService;
import com.huasisoft.flow.business.service.BusinessManageService;
import com.huasisoft.flow.business.vo.FlowManage;
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
    private BusinessBaseService businessBaseService;
    @Autowired
    private BusinessManageService businessManageService;

    /**
     *	修改业务
     * @param businessBase
     * @return
     */
    @PostMapping("updateBusiness")
    public JsonResult<Integer> updateBusiness(@RequestBody BusinessBase businessBase) {
            return new JsonResult<Integer>(businessBaseService.updateBusiness(businessBase));
    }
    
    @ApiOperation("获取业务按钮配置信息")
    @GetMapping("actions/{businessCode}")
    public JsonResult<List<FlowManage>> actions(
    		@PathVariable("businessCode") @ApiParam(value="businessCode",name="业务Code",required=true)String businessCode) {
    	List<FlowManage> result = businessManageService.getActions(businessCode);
    	return new JsonResult<List<FlowManage>>(result);
    }
    
    @ApiOperation("获取业务指定流程节点按钮配置信息")
    @GetMapping("actions/{businessCode}/{flowId}")
    public JsonResult<List<BaseAction>> actions(
    		@PathVariable("businessCode") @ApiParam(value="businessCode",name="业务Code",required=true)String businessCode,
    		@PathVariable("flowId") @ApiParam(value="flowId",name="流程节点ID",required=true)String flowId) {
    	return new JsonResult<>(businessManageService.getActions(businessCode,flowId));
    }
    
    @ApiOperation("获取业务指定流程节点视图信息")
    @GetMapping("views/{businessCode}")
    public JsonResult<List<FlowManage>> views(
    		@PathVariable("businessCode") @ApiParam(value="businessCode",name="业务Code",required=true)String businessCode) {
    	return new JsonResult<List<FlowManage>>(businessManageService.getViews(businessCode));
    }
    @ApiOperation("获取业务指定流程节点视图信息")
    @GetMapping("views/{businessCode}/{flowId}")
    public JsonResult<List<BaseView>> views(
    		@PathVariable("businessCode") @ApiParam(value="businessCode",name="业务Code",required=true)String businessCode,
    		@PathVariable("flowId") @ApiParam(value="flowId",name="流程节点ID",required=true)String flowId) {
    	return new JsonResult<>(businessManageService.getViews(businessCode,flowId));
    }
    
    @ApiOperation("获取业务指定流程节点时限信息")
    @GetMapping("timeLimit/{businessCode}")
    public JsonResult<List<FlowManage>> timeLimit(
    		@PathVariable("businessCode") @ApiParam(value="businessCode",name="业务Code",required=true)String businessCode) {
    	return new JsonResult<List<FlowManage>>(businessManageService.getViews(businessCode));
    }
    @ApiOperation("获取业务指定流程节点时限信息")
    @GetMapping("timeLimit/{businessCode}/{flowId}")
    public JsonResult<List<BaseView>> timeLimit(
    		@PathVariable("businessCode") @ApiParam(value="businessCode",name="业务Code",required=true)String businessCode,
    		@PathVariable("flowId") @ApiParam(value="flowId",name="流程节点ID",required=true)String flowId) {
    	return new JsonResult<>(businessManageService.getViews(businessCode,flowId));
    }

}
