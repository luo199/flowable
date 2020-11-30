package com.huasisoft.flow.business.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.huasisoft.flow.business.entity.ComPage;
import com.huasisoft.flow.business.service.ComPageService;
import com.huasisoft.flow.common.vo.JsonResult;
import com.huasisoft.flow.process.vo.ModelPageRequest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
/**
 * @Auther: flq
 * @Description:
 * @Date 2020/9/1
 */
@RestController
@RequestMapping("/api/business/commPage")
@Api("通用页面视图接口")
public class CommPageController {
	
	@Autowired
	private ComPageService comPageService;
	
	/**
     *	通用页面分页列表
     * @param page
     * @return
     */  
    @PostMapping("page")
    @ApiOperation("通用页面分页列表")
    public JsonResult<IPage<ComPage>> page(@RequestBody ModelPageRequest page) {
        return new JsonResult<IPage<ComPage>>(comPageService.page(page));
    }

    /**
     *	通用页面选择框
     * @param name
     * @return
     */
    @GetMapping("selectList")
    @ApiOperation("通用页面选择列表")
    public JsonResult<List<ComPage>> selectList(
    		@RequestParam(required=false) @ApiParam(required=false,value="页面名",name="name")String name) {
    	return new JsonResult<List<ComPage>>(comPageService.selectList(name));
    }
    
    @GetMapping("findOne")
    @ApiOperation("按主键查询")
    public JsonResult<ComPage> findOne(@ApiParam("页面主键")String  id) {
    	return new JsonResult<>(comPageService.findOne(id));
    }

    @PostMapping("save")
    @ApiOperation("保存数据")
    public JsonResult<Integer> save(@RequestBody ComPage comPage) {
    	comPageService.save(comPage);
        return new JsonResult<Integer>(1);
    }
    
    @GetMapping("deleteById")
    public JsonResult<Integer> deleteById(@ApiParam("页面主键")String id) {
        return new JsonResult<Integer>(comPageService.delete(id));
    }
}
