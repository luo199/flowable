package com.huasisoft.flow.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huasisoft.flow.business.entity.BaseExecutor;
import com.huasisoft.flow.business.entity.BaseHolidayForm;
import com.huasisoft.flow.business.entity.BusinessBase;
import com.huasisoft.flow.business.mapper.BaseExecutorDmMapper;
import com.huasisoft.flow.business.mapper.BaseHolidayFormMapper;
import com.huasisoft.flow.business.mapper.BusinessBaseMapper;
import com.huasisoft.flow.business.service.BaseHolidayFormService;
import com.huasisoft.flow.business.service.BusinessBaseService;
import com.huasisoft.flow.business.service.BusinessExecutorService;
import com.huasisoft.flow.business.service.BusinessManageService;
import com.huasisoft.flow.business.vo.BusinessManage;
import com.huasisoft.flow.common.util.IDCreator;
import com.huasisoft.flow.platform.service.impl.CurrentUserHelper;
import com.huasisoft.flow.platform.vo.Person;
import com.huasisoft.flow.process.service.impl.ProcInfoService;
import com.huasisoft.flow.process.vo.ModelPageRequest;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.flowable.bpmn.model.UserTask;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BaseHolidayFormServiceImpl implements BaseHolidayFormService {

    @Autowired
    private ProcessEngine processEngine;
    @Autowired
    private BaseHolidayFormMapper baseHolidayFormMapper;
    @Autowired
    private BusinessBaseService businessBaseService;
    @Autowired
    private BusinessExecutorService businessExecutorService;
    @Autowired
    private BaseExecutorDmMapper baseExecutorDmMapper;
    @Autowired
    private ProcInfoService procInfoService;


    @Override
    public IPage<BaseHolidayForm> page(ModelPageRequest page) {
        IPage<BaseHolidayForm> iPage = new Page<>(page.getCurrent(), page.getSize());
        IPage<BaseHolidayForm> result = baseHolidayFormMapper.selectPage(iPage, catalogQueryWrapper(page.getName(), page.getCatalogCode()));
        return result;
    }

    private QueryWrapper<BaseHolidayForm> catalogQueryWrapper(String name, String catalogCode) {
        QueryWrapper<BaseHolidayForm> queryWrapper = null;
        if (StringUtils.isBlank(catalogCode) && StringUtils.isBlank(name)) {
            queryWrapper = new QueryWrapper<BaseHolidayForm>().eq("FORM_STATUS", "0");
        } else if (StringUtils.isNotBlank(catalogCode)) {
            queryWrapper = new QueryWrapper<BaseHolidayForm>().eq("FORM_STATUS", "0").eq("ID", catalogCode).like("NAME", name);
        } else {
            queryWrapper = new QueryWrapper<BaseHolidayForm>().eq("FORM_STATUS", "0").like("NAME", name);
        }
        return queryWrapper;
    }

    @Override
    public List<BaseHolidayForm> getHolidayForms(String bizCode,String status) {
        QueryWrapper<BaseHolidayForm> queryWrapper=new QueryWrapper<BaseHolidayForm>().eq("BIZ_CODE", bizCode).eq("FORM_STATUS", status);
        return baseHolidayFormMapper.selectList(queryWrapper);
    }

    @Override
    public IPage<BaseHolidayForm> approvePage(ModelPageRequest page) {
        IPage<BaseHolidayForm> iPage = new Page<>(page.getCurrent(), page.getSize());
        IPage<BaseHolidayForm> result = baseHolidayFormMapper.selectPage(iPage, approveQueryWrapper(page.getName(), page.getCatalogCode()));
        return result;
    }

    private QueryWrapper<BaseHolidayForm> approveQueryWrapper(String name, String catalogCode) {
        QueryWrapper<BaseHolidayForm> queryWrapper = null;
        if (StringUtils.isBlank(catalogCode) && StringUtils.isBlank(name)) {
            queryWrapper = new QueryWrapper<BaseHolidayForm>().eq("FORM_STATUS", "1");
        } else if (StringUtils.isNotBlank(catalogCode)) {
            queryWrapper = new QueryWrapper<BaseHolidayForm>().eq("FORM_STATUS", "1").eq("ID", catalogCode).like("NAME", name);
        } else {
            queryWrapper = new QueryWrapper<BaseHolidayForm>().eq("FORM_STATUS", "1").like("NAME", name);
        }
        return queryWrapper;
    }

    @Override
    public BaseHolidayForm save(BaseHolidayForm baseHolidayForm) {
        Assert.notNull(baseHolidayForm, "不能为空");
        if (StringUtils.isBlank(baseHolidayForm.getId())) {
            baseHolidayForm.setId(IDCreator.genId());
            baseHolidayForm.setFormStatus("0");
            baseHolidayFormMapper.insert(baseHolidayForm);
        } else {
            baseHolidayFormMapper.updateById(baseHolidayForm);
        }
        return baseHolidayForm;
    }

    @Override
    public BaseHolidayForm findOne(String id) {
        return baseHolidayFormMapper.selectById(id);
    }


    @Override
    public List<BaseExecutor> getNextExecutor(String bizCode, String id) {
        List<BaseExecutor> executors =null;
        BaseHolidayForm baseHolidayForm = baseHolidayFormMapper.selectById(id);
        if (baseHolidayForm.getPosition().equals("员工")&&baseHolidayForm.getFormStatus().trim().equals("0")) {
            executors = businessExecutorService.getExecutors(bizCode, "directorTask");
        } else if (baseHolidayForm.getPosition().equals("员工")&&baseHolidayForm.getFormStatus().trim().equals("1")){
            executors = businessExecutorService.getExecutors(bizCode, "leaderChargeTask");
        } else if (baseHolidayForm.getPosition().equals("科室负责人") && baseHolidayForm.getFormStatus().trim().equals("0")) {
            executors = businessExecutorService.getExecutors(bizCode, "leaderChargeTask");
        } else {
            executors = businessExecutorService.getExecutors(bizCode, "mainLeader");
        }
        return executors;
    }

    @Override
    public int delete(String id) {
        return baseHolidayFormMapper.deleteById(id);
    }
}
