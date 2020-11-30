package com.huasisoft.flow.business.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.huasisoft.flow.business.entity.BusinessBase;
import com.huasisoft.flow.business.entity.BusinessCatalog;
import com.huasisoft.flow.business.service.BusinessCatalogService;
import com.huasisoft.flow.business.vo.CataLogManage;
import com.huasisoft.flow.common.vo.JsonResult;
import com.huasisoft.flow.process.vo.PageRequest;
import io.swagger.annotations.ApiOperation;
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
    @ApiOperation("主键查询")
    @GetMapping("findByCode")
    public JsonResult<BusinessCatalog> findByCode(String code) {
        return new JsonResult<BusinessCatalog>(businessCatalogService.findByCode(code));
    }

    /**
     *	新增业务分类
     * @param businessCatalog
     * @return
     */
    @ApiOperation("新增分类")
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
     *	保存父节点下的子节点列表
     * @param cataLogManage
     * @return
     */
    @ApiOperation("保存子节点列表")
    @PostMapping("saveChildCataLogList")
    public JsonResult<List<BusinessCatalog>> saveChildCataLogList(@RequestBody CataLogManage cataLogManage) {
        List<BusinessCatalog> result = businessCatalogService.saveChildCataLogList(cataLogManage);
        if (result!=null){
            return new JsonResult<List<BusinessCatalog>>(result);
        }else {
            return new JsonResult("新增子节点失败！",0);
        }
    }
    /**
     * 查询分类code值是否存在
     * @param code
     * @return
     */
    @ApiOperation("查询分类code值是否存在")
    @GetMapping("findByCataLogCode")
    public JsonResult<Boolean> findByCataLogCode(String code) {
        return new JsonResult<>(businessCatalogService.findByCataLogCode(code));
    }
    /**
     * 根据编码查询父节点编码
     * @param code
     * @return
     */
    @ApiOperation("根据code查询父节点code")
    @GetMapping("findPcodeByCode")
    public JsonResult<String> findPcodeByCode(String code) {
        return new JsonResult<>(businessCatalogService.findPcodeByCode(code));
    }

    /**
     * 查询分类Pcode值是否存在
     * @param pcode
     * @return
     */
    @ApiOperation("查询分类Pcode值是否存在")
    @GetMapping("findByCataLogPcode")
    public JsonResult<Boolean> findByCataLogPcode(String pcode) {
        return new JsonResult<>(businessCatalogService.findByCataLogPcode(pcode));
    }

    /**
     * 查询指定父节点及其子节点集合
     * @return
     */
    @ApiOperation("查询指定父节点及其子节点集合")
    @GetMapping("treeListCataLog")
    public JsonResult<List<BusinessCatalog>> treeListCataLog(String... pcodes){
        return new JsonResult<>(businessCatalogService.treeListAll(pcodes));
    }
    /**
     * 查询指定父节点下的一级节点（不包含父节点）
     * @return
     */
    @ApiOperation("查询指定父节点下的一级节点（不包含父节点）")
    @GetMapping("childListCataLog")
    public JsonResult<List<BusinessCatalog>> childListCataLog(String... pcodes){
        return new JsonResult<>(businessCatalogService.childListCataLog(pcodes));
    }

    /**
     * 根据子节点code查询所有父级节点
     * @param codes
     * @return
     */
    @ApiOperation("根据子节点code查询所有父级节点")
    @GetMapping("findCataLogListByChildCode")
    public JsonResult<List<BusinessCatalog>> findCataLogListByChildCode(String... codes){
        return new JsonResult<>(businessCatalogService.findCataLogListByChildCode(codes));
    }


    /**
     *	修改分类
     * @param businessCatalog
     * @return
     */
    @ApiOperation("修改分类")
    @PostMapping("updateCataLog")
    public JsonResult<Integer> updateCataLog(@RequestBody BusinessCatalog businessCatalog) {
        return new JsonResult<Integer>(businessCatalogService.updateCataLog(businessCatalog));
    }
    /**
     *	根据请求参数分页查询流程列表
     * @param page
     * @return
     */
    @ApiOperation("分页查询")
    @PostMapping("page")
    public JsonResult<IPage<BusinessCatalog>> page(@RequestBody PageRequest page) {
        return new JsonResult<IPage<BusinessCatalog>>(businessCatalogService.page(page));
    }

    /**
     * 删除业务类别--伪删除
     * @param code
     * @return
     */
    @ApiOperation("删除业务类别--伪删除")
    @GetMapping("updateStatus")
    public JsonResult<Integer> updateStatus(String code) {
        return new JsonResult<Integer>(businessCatalogService.updateStatus(code));
    }

    /**
     * 删除业务类别--删除
     * @param code
     * @return
     */
    @ApiOperation("删除业务类别")
    @GetMapping("deleteByCode")
    public JsonResult<Integer> deleteByCode(String code) {
        return new JsonResult<Integer>(businessCatalogService.deleteByCode(code));
    }
}
