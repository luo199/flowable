package com.huasisoft.flow.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huasisoft.flow.business.entity.BaseExecutor;
import com.huasisoft.flow.business.entity.BaseExecutorDetail;
import com.huasisoft.flow.business.entity.BaseHolidayForm;
import com.huasisoft.flow.business.entity.BusinessBase;
import com.huasisoft.flow.business.service.*;
import com.huasisoft.flow.business.vo.HolidayManage;
import com.huasisoft.flow.process.service.impl.ProcInfoService;
import com.huasisoft.flow.process.vo.ModelPageRequest;
import org.apache.commons.lang3.StringUtils;
import org.flowable.bpmn.model.UserTask;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class HolidayManageServiceImpl implements HolidayManageService {

    @Autowired
    private ProcessEngine processEngine;
    @Autowired
    private ProcInfoService procInfoService;
    @Autowired
    private BusinessBaseService businessBaseService;
    @Autowired
    private BaseHolidayFormService baseHolidayFormService;
    @Autowired
    private BusinessExecutorService businessExecutorService;
    @Autowired
    private BaseExecutorDetailService baseExecutorDetailService;


    @Override
    public List<HolidayManage> getHolidayManages(String businessCode,String status) {
        List<HolidayManage> holidayManages = new ArrayList<>();
        BusinessBase businessBase = businessBaseService.findByCode(businessCode);

        List<BaseHolidayForm> baseHolidayForms = baseHolidayFormService.getHolidayForms(businessCode,status);
        for (BaseHolidayForm baseHolidayForm : baseHolidayForms) {
            HolidayManage holidayManage = new HolidayManage();
            List<BaseExecutor> baseExecutors = new ArrayList<>();
            List<BaseExecutorDetail> executorDetails = new ArrayList<>();
            holidayManage.setBaseHolidayForm(baseHolidayForm);
            BaseExecutor executorOne = businessExecutorService.getExecutorById(baseHolidayForm.getExecutorOneId());
            baseExecutors.add(executorOne);
            BaseExecutor executorTwo = businessExecutorService.getExecutorById(baseHolidayForm.getExecutorTwoId());
            baseExecutors.add(executorTwo);
            holidayManage.setBaseExecutors(baseExecutors);
            executorDetails = baseExecutorDetailService.getExecutorDetails(baseHolidayForm.getId());
            holidayManage.setBaseExecutorDetails(executorDetails);
            holidayManages.add(holidayManage);
        }
        return holidayManages;
    }

    @Override
    public void startHolidayProcess(String id,String executorId) {
        BaseHolidayForm baseHolidayForm = baseHolidayFormService.findOne(id);
        if (baseHolidayForm.getPosition().equals("员工")) {
            baseHolidayForm.setFormStatus("1");
        } else {
            baseHolidayForm.setFormStatus("2");
        }
        baseHolidayForm.setExecutorOneId(executorId);
        baseHolidayFormService.save(baseHolidayForm);

       /* TaskService taskService = processEngine.getTaskService();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        runtimeService.startProcessInstanceById(businessBaseService.findByCode(baseHolidayForm.getBizCode()).getProcessId());
        Map<String, Object> variables = new HashMap<>();
        List<UserTask> userTasks = procInfoService.getUserTasks(procInfoService.getMainProcess(businessBaseService.findByCode(baseHolidayForm.getBizCode()).getProcessId()));
        List<Task> employeesTasks = taskService.createTaskQuery().taskCandidateGroup("employees").list();
        if (baseHolidayForm.getPosition().equals("员工")) {
            variables.put("TheLeader", false);
//            userTasks.get(1).getCandidateGroups().add(executorId);
            Task task = employeesTasks.get(0);
            taskService.complete(task.getId(),variables);
        } else {
            variables.put("TheLeader", true);
//            userTasks.get(2).getCandidateGroups().add(executorId);
            Task task = employeesTasks.get(0);
            taskService.complete(task.getId(),variables);
        }*/
    }

    @Override
    public void holidayApproveProcess(String id, String executorId) {
        BaseHolidayForm baseHolidayForm = baseHolidayFormService.findOne(id);
        if (baseHolidayForm.getPosition().equals("员工")) {
            baseHolidayForm.setFormStatus("2");
        } else {
            baseHolidayForm.setFormStatus("3");
        }
        baseHolidayForm.setExecutorTwoId(executorId);
        baseHolidayFormService.save(baseHolidayForm);
/*

        TaskService taskService = processEngine.getTaskService();
        List<Task> directorTasks = taskService.createTaskQuery().taskCandidateGroup("directors").list();
*/
    }

    @Override
    public void holidayFinish(String id) {
        BaseHolidayForm baseHolidayForm = baseHolidayFormService.findOne(id);
        baseHolidayForm.setFormStatus("6");
        baseHolidayFormService.save(baseHolidayForm);
    }

    @Override
    public void holidayReject(String id) {
        BaseHolidayForm baseHolidayForm = baseHolidayFormService.findOne(id);
        baseHolidayForm.setFormStatus("5");
        baseHolidayFormService.save(baseHolidayForm);
    }
}
