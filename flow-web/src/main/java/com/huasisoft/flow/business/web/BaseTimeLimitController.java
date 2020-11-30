package com.huasisoft.flow.business.web;

import com.huasisoft.flow.business.entity.BaseTimeLimit;
import com.huasisoft.flow.business.service.BusinessTimeLimitService;
import com.huasisoft.flow.common.vo.JsonResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Auther: yn
 * @Description:
 * @Date 2020/9/9
 */
@RestController
@RequestMapping("/api/business/manage/limit")
@Api("业务时限管理接口")
public class BaseTimeLimitController {

    @Autowired
    private BusinessTimeLimitService businessTimeLimitService;

    @ApiOperation("保存流程节点时限信息")
    @PostMapping("/save")
    public JsonResult<BaseTimeLimit> saveTimeLimit(@RequestBody BaseTimeLimit baseTimeLimit) {
        return new JsonResult<>(businessTimeLimitService.saveTimiLimit(baseTimeLimit));
    }

    @ApiOperation("删除流程节点时限信息")
    @GetMapping("/deleteByTaskId")
    public JsonResult<Integer> deleteTimeLimit(String taskId) {
        return new JsonResult<>(businessTimeLimitService.deleteByTaskId(taskId));
    }
    @ApiOperation("获取业务指定流程节点时限信息")
    @GetMapping("/{businessCode}/{flowId}")
    public JsonResult<BaseTimeLimit> timeLimit(
            @PathVariable("businessCode") @ApiParam(value="businessCode",name="业务Code",required=true,type="path")String businessCode,
            @PathVariable("flowId") @ApiParam(value="flowId",name="流程节点ID",required=true)String flowId) {
        return new JsonResult<>(businessTimeLimitService.getTimeLimit(businessCode,flowId));
    }
}
