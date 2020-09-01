package com.huasisoft.flow.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.flowable.bpmn.model.Process;
import org.flowable.bpmn.model.UserTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.huasisoft.flow.business.entity.BaseAction;
import com.huasisoft.flow.business.entity.BaseTimeLimit;
import com.huasisoft.flow.business.entity.BaseView;
import com.huasisoft.flow.business.entity.BusinessBase;
import com.huasisoft.flow.business.mapper.BaseTimeLimitDmMapper;
import com.huasisoft.flow.business.mapper.BaseViewDmMapper;
import com.huasisoft.flow.business.service.BusinessBaseService;
import com.huasisoft.flow.business.service.BusinessManageService;
import com.huasisoft.flow.business.vo.FlowManage;
import com.huasisoft.flow.business.vo.ProcManage;
import com.huasisoft.flow.business.vo.TaskManage;
import com.huasisoft.flow.process.service.impl.ProcInfoService;

@Service
public class BusinessManageServiceImpl implements BusinessManageService {
	
	@Autowired
	private ProcInfoService procInfoService;
	@Autowired
	private BusinessBaseService businessBaseService;
	@Autowired
	private BaseViewDmMapper baseViewMapper;
	@Autowired
	private BaseTimeLimitDmMapper baseTimeLimitDmMapper;

	@Override
	public List<FlowManage> getViews(String businessCode) {
		BusinessBase businessBase = businessBaseService.findByCode(businessCode);
		String processDefinitionId = businessBase.getProcessId();
		Process process = procInfoService.getMainProcess(processDefinitionId);
		
		List<FlowManage> flowManageList = new ArrayList<FlowManage>();
		ProcManage procManage = new ProcManage();
		procManage.setId(process.getId());
		procManage.setName(process.getName());
		procManage.setViews(getViews(businessCode, process.getId()));
		flowManageList.add(procManage);
		
		List<UserTask> userTasks = procInfoService.getUserTasks(process);
		for (UserTask userTask : userTasks) {
			TaskManage taskManage = new TaskManage();
			taskManage.setId(userTask.getId());
			taskManage.setName(userTask.getName());
			taskManage.setViews(getViews(businessCode, userTask.getId()));
			flowManageList.add(taskManage);
		}
		return flowManageList;
	}

	@Override
	public List<BaseView> getViews(String businessCode, String flowId) {
		QueryWrapper<BaseView> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("BIZ_CODE","0" ).eq("TASK_ID","0" ).orderByAsc("ORDER_NUMBER");
        return baseViewMapper.selectList(queryWrapper);
	}

	@Override
	public List<FlowManage> getActions(String businessCode) {
		BusinessBase businessBase = businessBaseService.findByCode(businessCode);
		String processDefinitionId = businessBase.getProcessId();
		Process process = procInfoService.getMainProcess(processDefinitionId);
		
		List<FlowManage> flowManageList = new ArrayList<FlowManage>();
		ProcManage procManage = new ProcManage();
		procManage.setId(process.getId());
		procManage.setName(process.getName());
		procManage.setActions(getActions(businessCode, process.getId()));
		flowManageList.add(procManage);
		
		List<UserTask> userTasks = procInfoService.getUserTasks(process);
		for (UserTask userTask : userTasks) {
			TaskManage taskManage = new TaskManage();
			taskManage.setId(userTask.getId());
			taskManage.setName(userTask.getName());
			taskManage.setActions(getActions(businessCode, userTask.getId()));
			flowManageList.add(taskManage);
		}
		return flowManageList;
	}

	@Override
	public List<BaseAction> getActions(String businessCode, String flowId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FlowManage> getTimiLimit(String businessCode) {
		BusinessBase businessBase = businessBaseService.findByCode(businessCode);
		String processDefinitionId = businessBase.getProcessId();
		Process process = procInfoService.getMainProcess(processDefinitionId);
		
		List<FlowManage> flowManageList = new ArrayList<FlowManage>();
		ProcManage procManage = new ProcManage();
		procManage.setId(process.getId());
		procManage.setName(process.getName());
		procManage.setTimeLimit(getTimeLimit(businessCode, process.getId()));
		flowManageList.add(procManage);
		
		List<UserTask> userTasks = procInfoService.getUserTasks(process);
		for (UserTask userTask : userTasks) {
			TaskManage taskManage = new TaskManage();
			taskManage.setId(userTask.getId());
			taskManage.setName(userTask.getName());
			taskManage.setTimeLimit(getTimeLimit(businessCode, userTask.getId()));
			flowManageList.add(taskManage);
		}
		return flowManageList;
	}

	@Override
	public BaseTimeLimit getTimeLimit(String businessId, String flowId) {
		QueryWrapper<BaseTimeLimit> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("BIZ_CODE","0" ).eq("TASK_ID","0" ).orderByAsc("ORDER_NUMBER");
        return baseTimeLimitDmMapper.selectOne(queryWrapper);
	}

}
