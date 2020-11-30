package com.huasisoft.flow.business.web;

import com.huasisoft.flow.business.entity.BusinessBase;
import com.huasisoft.flow.business.service.BusinessBaseService;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.huasisoft.flow.business.entity.BaseAction;
import com.huasisoft.flow.business.service.BusinessActionService;
import com.huasisoft.flow.common.vo.JsonResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/business/manage/actions")
@Api("业务管理接口")
public class BusinessActionController {

    @Autowired
    private BusinessActionService businessActionService;
    @Autowired
    private BusinessBaseService businessBaseService;

    @ApiOperation("获取业务指定流程节点按钮配置信息")
    @GetMapping("/{businessCode}/{flowId}")
    public JsonResult<List<BaseAction>> actions(
    		@PathVariable("businessCode") @ApiParam(value="businessCode",name="业务Code",required=true,type="path")String businessCode,
    		@PathVariable("flowId") @ApiParam(value="flowId",name="流程节点ID",required=true,type="path")String flowId) {
    	return new JsonResult<>(businessActionService.getActions(businessCode,flowId));
    }
    @ApiOperation("保存流程节点配置按钮信息")
    @PostMapping("save")
    public JsonResult<BaseAction> saveBaseAction(@RequestBody BaseAction baseAction) {
        String actionId = baseAction.getActionId();
        String taskId = baseAction.getTaskId();
        String id = baseAction.getId();
        String bizCode = baseAction.getBizCode();

        BusinessBase businessBase = businessBaseService.findByCode(bizCode);
        if(businessBase.getProcessId().equals(taskId)){
            //流程
            List<BaseAction> actions = businessActionService.getActions(bizCode, taskId);
            for (BaseAction temp:
                    actions) {
                if(temp.getActionId().equals(actionId)&&!temp.getId().equals(id)){
                    return new JsonResult<>("重复按钮",300);
                }
            }
            businessActionService.deleteByBizCodeAndActionId(bizCode,actionId,id);
        }else{
            //任务
            List<BaseAction> actions = new ArrayList<>();
            actions.addAll(businessActionService.getActions(bizCode, businessBase.getProcessId()));
            actions.addAll(businessActionService.getActions(bizCode, taskId));
            for (BaseAction temp:
                 actions) {
                if(temp.getActionId().equals(actionId)&&!temp.getId().equals(id)){
                    return new JsonResult<>("重复按钮",300);
                }
            }
        }
        return new JsonResult<>(businessActionService.saveAction(baseAction));
    }

    @ApiOperation("删除流程节点配置按钮信息")
    @GetMapping("deleteById")
    public JsonResult<Integer> deleteBaseAction(String Id) {
        return new JsonResult<>(businessActionService.delete(Id));
    }

    @ApiOperation("根据ID查找单个流程按钮")
    @GetMapping("findOne")
    public JsonResult<BaseAction> findOne(String id) {
        return new JsonResult<BaseAction>(businessActionService.findOne(id));
    }
   /* @ApiOperation("根据名字删除任务按钮")
    @GetMapping("deleteByBizCodeAndActionId")
    public JsonResult<Integer> deleteByBizCodeAndActionId(String bizCode,String actionId) {
        return new JsonResult<Integer>(businessActionService.deleteByBizCodeAndActionId(bizCode,actionId));
    }*/
}
