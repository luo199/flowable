package com.huasisoft.flow.form.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.huasisoft.flow.common.vo.JsonResult;
import com.huasisoft.flow.form.entity.BusinessComForm;
import com.huasisoft.flow.form.service.BusinessComFormService;
import com.huasisoft.flow.form.vo.FormPageRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @Auther: yn
 * @Description:
 * @Date 2020/9/17
 */
@RestController
@RequestMapping("/api/form")
@Api("表单定义接口")
public class BusinessComFormController {

    @Autowired
    private BusinessComFormService businessComFormService;

    @ApiOperation("分页列表查询")
    @PostMapping("page")
    public JsonResult<IPage<BusinessComForm>> page(@RequestBody  FormPageRequest formPageRequest){
        return new JsonResult<>(businessComFormService.page(formPageRequest));
    }

    @ApiOperation("查询列表")
    @GetMapping("findAll")
    public JsonResult<List<BusinessComForm>> findAll(){
        return new JsonResult<>(businessComFormService.findAll());
    }

    @ApiOperation("主键查询")
    @GetMapping("getById")
    public JsonResult<BusinessComForm> getById(String id){
        return new JsonResult<>(businessComFormService.getById(id));
    }

    @ApiOperation("保存")
    @PostMapping("save")
    public JsonResult<BusinessComForm> save(@RequestBody  BusinessComForm businessComForm){
        return new JsonResult<>(businessComFormService.saveForm(businessComForm));
    }

    @ApiOperation("删除")
    @GetMapping("delete")
    public JsonResult<Integer> delete(String id){
        return new JsonResult<>(businessComFormService.deleteById(id));
    }

}
