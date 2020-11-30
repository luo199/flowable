package com.huasisoft.flow.business.web;


import com.huasisoft.flow.business.service.*;
import com.huasisoft.flow.business.vo.HolidayManage;
import com.huasisoft.flow.common.vo.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/holiday/manage")
@Api("请假管理接口")
public class HolidayManageController {

    @Autowired
    private HolidayManageService holidayManageService;


    @ApiOperation("获取业务流程信息")
    @GetMapping("{businessCode}/{status}")
    public JsonResult<List<HolidayManage>> getHolidayManage(
            @PathVariable @ApiParam(value = "businessCode", name = "业务Code", required = true, type = "path") String businessCode,
            @PathVariable  @ApiParam(value = "status", name = "status", required = true, type = "path")String status){
        return new JsonResult<>(holidayManageService.getHolidayManages(businessCode,status));
    }

    @PostMapping("startHolidayProcess")
    @ApiOperation("实例化流程开始执行")
    public JsonResult<Integer> startHolidayProcess(@RequestBody Map<String,String> map) {//map存放表Id和执行人Id
        holidayManageService.startHolidayProcess(map.get("holidayFormId"),map.get("executorId"));
        return new JsonResult<>(1);
    }

    @PostMapping("holidayApproveProcess")
    @ApiOperation("实例化流程开始执行")
    public JsonResult<Integer> holidayApproveProcess(@RequestBody Map<String,String> map) {//map存放表Id和执行人Id
        holidayManageService.holidayApproveProcess(map.get("holidayFormId"),map.get("executorId"));
        return new JsonResult<>(1);
    }

    @ApiOperation("流程完成")
    @GetMapping("holidayFinish")
    public JsonResult<Integer> holidayFinish(@ApiParam(value = "FormId", name = "表单id", required = true)String id) {
        holidayManageService.holidayFinish(id);
        return new JsonResult<>(1);
    }

    @ApiOperation("流程取消")
    @GetMapping("holidayReject")
    public JsonResult<Integer> holidayReject(@ApiParam(value = "FormId", name = "表单id", required = true)String id) {
        holidayManageService.holidayReject(id);
        return new JsonResult<>(1);
    }



}
