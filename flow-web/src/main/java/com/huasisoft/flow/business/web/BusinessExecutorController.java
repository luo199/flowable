package com.huasisoft.flow.business.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.huasisoft.flow.business.entity.BaseAction;
import com.huasisoft.flow.business.entity.BaseExecutor;
import com.huasisoft.flow.business.entity.BusinessBase;
import com.huasisoft.flow.business.service.BusinessExecutorService;
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
@RequestMapping("/api/business/manage/executor")
@Api("业务执行人管理接口")
public class BusinessExecutorController {
 
    @Autowired
    private BusinessExecutorService businessExecutorService;

    @ApiOperation("获取业务指定流程节点人员信息")
    @GetMapping("/{businessCode}/{flowId}")
    public JsonResult<List<BaseExecutor>> getExecutors(
            @PathVariable("businessCode") @ApiParam(value="businessCode",name="业务Code",required=true,type="path")String businessCode,
            @PathVariable("flowId") @ApiParam(value="flowId",name="流程节点ID",required=true)String flowId) {
        return new JsonResult<>(businessExecutorService.getExecutors(businessCode, flowId));
    }
    
    @ApiOperation("保存执行人员信息")
    @PostMapping("save")
    public JsonResult<List<BaseExecutor>> save(@RequestBody List<BaseExecutor> baseExecutors) {
        return new JsonResult<>(businessExecutorService.save(baseExecutors));
    }

    @ApiOperation("删除执行人员信息")
    @GetMapping("deleteById/{id}")
    public JsonResult<Integer> deleteBaseAction(
    		@PathVariable("id") @ApiParam(value="id",name="执行者主键",required=true,type="path")String id) {
        return new JsonResult<>(businessExecutorService.deleteExecutorById(id));
    }
}
