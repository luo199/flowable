package com.huasisoft.flow.business.web;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.huasisoft.flow.business.entity.ComAction;
import com.huasisoft.flow.business.service.ComActionService;
import com.huasisoft.flow.common.vo.JsonResult;
import com.huasisoft.flow.process.vo.ModelPageRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/business/commAction")
@Api("通用按钮定义接口")
public class CommActionController {

    @Autowired
    private ComActionService comActionService;

    /**
     * 通用按钮分页列表
     * @param page
     * @return
     */
    @PostMapping("page")
    @ApiOperation("通用按钮分页列表")
    public JsonResult<IPage<ComAction>> page(@RequestBody ModelPageRequest page) {
        return new JsonResult<>(comActionService.page(page));
    }
    
    @PostMapping("save")
    @ApiOperation("通用按钮保存")
    public JsonResult<Integer> save(@RequestBody ComAction comAction) {
        comActionService.save(comAction);
        return new JsonResult<>(1);
    }

    /**
     * 根据主键查询
     * @param id
     * @return
     */
    @GetMapping("findOne")
    @ApiOperation("按主键查询")
    public JsonResult<ComAction> findOne(@ApiParam(name = "页面主键")String id) {
        return new JsonResult<>(comActionService.findOne(id));
    }
    
    
    @GetMapping("deleteById")
    public JsonResult<Integer> deleteById(@ApiParam(name = "页面主键")String id) {
        return new JsonResult<>(comActionService.delete(id));
    }

    /**
     * 通用按纽全部列表
     * @return
     */
    @PostMapping("actionsList")
    @ApiOperation("通用按纽全部列表")
    public JsonResult<List<ComAction>> listAll(){
        return new JsonResult<List<ComAction>>(comActionService.listAll());
    }
    
    
    
}
