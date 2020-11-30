package com.huasisoft.flow.business.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.huasisoft.flow.business.entity.BusinessBase;
import com.huasisoft.flow.business.service.BusinessBaseService;
import com.huasisoft.flow.business.vo.BusinessPageRequest;
import com.huasisoft.flow.common.vo.JsonResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Auther: yn
 * @Description:
 * @Date 2020/8/14
 */
@RestController
@RequestMapping("/api/business")
public class BusinessDefController {

    @Autowired
    private BusinessBaseService businessBaseService;

    /**
     * 查询单个业务
     * @param code
     * @return
     */
    @ApiOperation("根据主键查询")
    @GetMapping("findByCode")
    public JsonResult<BusinessBase> findByCode(String code) {
        return new JsonResult<BusinessBase>(businessBaseService.findByCode(code));
    }

    /**
     *	根据请求参数分页查询流程列表
     * @param page
     * @return
     */
    @ApiOperation("分页查询列表")
    @PostMapping("page")
    public JsonResult<IPage<BusinessBase>> page(@RequestBody BusinessPageRequest page) {
        return new JsonResult<IPage<BusinessBase>>(businessBaseService.page(page));
    }

    /**
     *	新增业务
     * @param businessBase
     * @return
     */
    @ApiOperation("新增业务")
    @PostMapping("createBusiness")
    public JsonResult<BusinessBase> createBusiness(@RequestBody BusinessBase businessBase) {
        BusinessBase businessResult = businessBaseService.createBusiness(businessBase);
        if (businessResult!=null){
            return new JsonResult<BusinessBase>(businessResult);
        }else {
            return new JsonResult("新增失败！",0);
        }
    }

    /**
     *	修改业务
     * @param businessBase
     * @return
     */
    @ApiOperation("更新业务")
    @PostMapping("updateBusiness")
    public JsonResult<Integer> updateBusiness(@RequestBody BusinessBase businessBase) {
            return new JsonResult<Integer>(businessBaseService.updateBusiness(businessBase));
    }
    /**
     * 查询流程code值是否存在
     * @param code
     * @return
     */
    @ApiOperation("查询流程code值是否存在")
    @GetMapping("findByBusinessCode")
    public JsonResult<Boolean> findByBusinessCode(String code) {
        return new JsonResult<>(businessBaseService.findByBusinessCode(code));
    }

    /**
     * 查询流程code值是否存在
     * @param cataCode
     * @return
     */
    @ApiOperation("查询分类是否有业务列表")
    @GetMapping("checkBusinessByCatacode")
    public JsonResult<Boolean> checkBusinessByCatacode(String cataCode) {
        return new JsonResult<>(businessBaseService.checkBusinessByCatacode(cataCode));
    }
    /**
     * 删除业务--伪删除
     * @param code
     * @return
     */
    @ApiOperation("删除业务--伪删除")
    @GetMapping("updateStatus")
    public JsonResult<Integer> updateStatus(String code) {
        return new JsonResult<Integer>(businessBaseService.updateStatus(code));
    }

    /**
     * 删除业务--删除
     * @param code
     * @return
     */
    @ApiOperation("删除业务")
    @GetMapping("deleteByCode")
    public JsonResult<Integer> deleteByCode(String code) {
        return new JsonResult<Integer>(businessBaseService.deleteByCode(code));
    }
}
