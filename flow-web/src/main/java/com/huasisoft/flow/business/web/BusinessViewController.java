package com.huasisoft.flow.business.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.huasisoft.flow.business.entity.BaseView;
import com.huasisoft.flow.business.service.BusinessViewService;
import com.huasisoft.flow.common.vo.JsonResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @Auther: yn
 * @Description:
 * @Date 2020/9/9
 */
@RestController
@RequestMapping("/api/business/manage/views")
@Api("业务视图管理接口")
public class BusinessViewController {

    @Autowired
    private BusinessViewService businessViewService;

    @ApiOperation("获取业务指定流程节点视图信息")
    @GetMapping("/{businessCode}/{flowId}")
    public JsonResult<List<BaseView>> views(
    		@PathVariable("businessCode") @ApiParam(value="businessCode",name="业务Code",required=true,type="path")String businessCode,
    		@PathVariable("flowId") @ApiParam(value="flowId",name="流程节点ID",required=true)String flowId) {
    	return new JsonResult<>(businessViewService.getViews(businessCode,flowId));
    }

    @ApiOperation("保存配置视图")
    @PostMapping("/save")
    public JsonResult<BaseView> saveView(@RequestBody BaseView baseView) {
        return new JsonResult<>(businessViewService.save(baseView));
    }

    @ApiOperation("删除配置视图")
    @GetMapping("/deleteByTaskId")
    public JsonResult<Integer> deleteTimeLimit(String taskId) {
        return new JsonResult<>(businessViewService.deleteByTaskId(taskId));
    }


}
