package com.huasisoft.flow.business.web;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.enums.ApiErrorCode;
import com.huasisoft.flow.business.entity.BaseExecutor;
import com.huasisoft.flow.business.entity.BaseHolidayForm;
import com.huasisoft.flow.business.entity.BusinessBase;
import com.huasisoft.flow.business.service.BaseHolidayFormService;
import com.huasisoft.flow.common.vo.JsonResult;
import com.huasisoft.flow.process.vo.ModelPageRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.sf.json.JSONObject;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/business/manage/HolidayForm")
@Api("请休假基本信息表接口")
public class BaseHolidayFormController {
    @Autowired
    private BaseHolidayFormService baseHolidayFormService;

    @PostMapping("page")
    @ApiOperation("请休假信息表分页列表")
    public JsonResult<IPage<BaseHolidayForm>> getHolidayForms(@RequestBody ModelPageRequest page){
        return new JsonResult<>(baseHolidayFormService.page(page));
    }

    
    @PostMapping("save")
    @ApiOperation("请休假基本信息表保存")
    public JsonResult<Integer> save(@RequestBody BaseHolidayForm baseHolidayForm){
        baseHolidayFormService.save(baseHolidayForm);
        return new JsonResult<>(1);
    }

    @GetMapping("findOne")
    @ApiOperation("根据主键查找请休假信息")
    public JsonResult<BaseHolidayForm> findOne(@ApiParam(name = "页面主键",type = "query") @RequestParam String id) {
        return new JsonResult<>(baseHolidayFormService.findOne(id));
    }

    @GetMapping("getNextExecutor")
    @ApiOperation("实例化流程执行人")
    public JsonResult<List<BaseExecutor>> getNextExecutor(
            @ApiParam(name = "bizCode")@RequestParam(value = "bizCode") String bizCode,
            @ApiParam(name = "id") @RequestParam(value = "id") String id) {
        return new JsonResult<>(baseHolidayFormService.getNextExecutor(bizCode, id));
    }

    @PostMapping("approvePage")
    @ApiOperation("请休假信息表分页列表")
    public JsonResult<IPage<BaseHolidayForm>> approvePage(@RequestBody ModelPageRequest page){
        return new JsonResult<>(baseHolidayFormService.approvePage(page));
    }

    @GetMapping("deleteById")
    @ApiOperation("删除")
    public JsonResult<Integer> deleteById(@ApiParam(name = "页面主键")String id) {
        return new JsonResult<>(baseHolidayFormService.delete(id));
    }

}
