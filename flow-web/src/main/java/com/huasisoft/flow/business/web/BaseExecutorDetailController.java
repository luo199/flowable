package com.huasisoft.flow.business.web;


import com.huasisoft.flow.business.entity.BaseExecutorDetail;
import com.huasisoft.flow.business.service.BaseExecutorDetailService;
import com.huasisoft.flow.common.vo.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Hashtable;


@RestController
@RequestMapping("/api/business/manage/executorDetail")
@Api("业务执行人意见接口")
public class BaseExecutorDetailController {
    @Autowired
    private BaseExecutorDetailService baseExecutorDetailService;

    @ApiOperation("保存执行人意见")
    @PostMapping("save")
    public JsonResult<BaseExecutorDetail> save(@RequestBody BaseExecutorDetail baseExecutorDetail) {
        return new JsonResult<>(baseExecutorDetailService.saveExecutorDetail(baseExecutorDetail));
    }


}
