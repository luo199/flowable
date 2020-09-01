package com.huasisoft.flow.business.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.huasisoft.flow.business.entity.BusinessBase;
import com.huasisoft.flow.business.entity.BusinessCatalog;
import com.huasisoft.flow.business.service.BusinessCatalogService;
import com.huasisoft.flow.common.vo.JsonResult;
import com.huasisoft.flow.process.vo.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Auther: yn
 * @Description:
 * @Date 2020/8/18
 */
@RestController
@RequestMapping("/api/businessCatalog")
public class BusinessCatalogController {

    @Autowired
    private BusinessCatalogService businessCatalogService;

    /**
     * 查询单个业务
     * @param code
     * @return
     */
    @GetMapping("findByCode")
    public JsonResult<BusinessCatalog> findByCode(String code) {
        return new JsonResult<BusinessCatalog>(businessCatalogService.findByCode(code));
    }

    /**
     *	新增业务分类
     * @param businessCatalog
     * @return
     */
    @PostMapping("createBusinessCatalog")
    public JsonResult<BusinessCatalog> createBusiness(@RequestBody BusinessCatalog businessCatalog) {
        BusinessCatalog result = businessCatalogService.createBusinessCatalog(businessCatalog);
        if (result!=null){
            return new JsonResult<BusinessCatalog>(result);
        }else {
            return new JsonResult("新增失败！",0);
        }
    }

    /**
     * 查询分类code值是否存在
     * @param code
     * @return
     */
    @GetMapping("findByCataLogCode")
    public JsonResult<Boolean> findByCataLogCode(String code) {
        return new JsonResult<>(businessCatalogService.findByCataLogCode(code));
    }


    /**
     * 查询所有的业务分类
     * @return
     */
    @PostMapping("listAllCataLog")
    public JsonResult<List<BusinessCatalog>> listAllCataLog(){
        return new JsonResult<>(businessCatalogService.listAll());
    }
    /**
     *	修改分类
     * @param businessCatalog
     * @return
     */
    @PostMapping("updateCataLog")
    public JsonResult<Integer> updateCataLog(@RequestBody BusinessCatalog businessCatalog) {
        return new JsonResult<Integer>(businessCatalogService.updateCataLog(businessCatalog));
    }
    /**
     *	根据请求参数分页查询流程列表
     * @param page
     * @return
     */
    @PostMapping("page")
    public JsonResult<IPage<BusinessCatalog>> page(@RequestBody PageRequest page) {
        return new JsonResult<IPage<BusinessCatalog>>(businessCatalogService.page(page));
    }

    /**
     * 删除业务类别--伪删除
     * @param code
     * @return
     */
    @GetMapping("updateStatus")
    public JsonResult<Integer> updateStatus(String code) {
        return new JsonResult<Integer>(businessCatalogService.updateStatus(code));
    }

    /**
     * 删除业务类别--删除
     * @param code
     * @return
     */
    @GetMapping("deleteByCode")
    public JsonResult<Integer> deleteByCode(String code) {
        return new JsonResult<Integer>(businessCatalogService.deleteByCode(code));
    }
}
