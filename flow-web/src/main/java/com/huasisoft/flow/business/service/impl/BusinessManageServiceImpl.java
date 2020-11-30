package com.huasisoft.flow.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.flowable.bpmn.model.Process;
import org.flowable.bpmn.model.UserTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huasisoft.flow.business.entity.BusinessBase;
import com.huasisoft.flow.business.service.BusinessActionService;
import com.huasisoft.flow.business.service.BusinessBaseService;
import com.huasisoft.flow.business.service.BusinessExecutorService;
import com.huasisoft.flow.business.service.BusinessManageService;
import com.huasisoft.flow.business.service.BusinessTimeLimitService;
import com.huasisoft.flow.business.service.BusinessViewService;
import com.huasisoft.flow.business.vo.BusinessManage;
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
	private BusinessViewService businessViewService;
	@Autowired
	private BusinessTimeLimitService businessTimeLimitService;
	@Autowired
	private BusinessActionService businessActionService;
	@Autowired
	private BusinessExecutorService businessExecutorService;


	@Override
	public BusinessManage getViews(String businessCode) {
		BusinessManage businessManage = new BusinessManage();
		
		BusinessBase businessBase = businessBaseService.findByCode(businessCode);
		businessManage.setBase(businessBase);
		
		String processDefinitionId = businessBase.getProcessId();
		Process process = procInfoService.getMainProcess(processDefinitionId);
		
		ProcManage procManage = new ProcManage();
		procManage.setId(processDefinitionId);
		procManage.setName(process.getName());
		procManage.setViews(businessViewService.getViews(businessCode, processDefinitionId));
		businessManage.setProcManage(procManage);

		List<TaskManage> taskManages = new ArrayList<>();
		List<UserTask> userTasks = procInfoService.getUserTasks(process);
		for (UserTask userTask : userTasks) {
			TaskManage taskManage = new TaskManage();
			taskManage.setId(userTask.getId());
			taskManage.setName(userTask.getName());
			taskManage.setViews(businessViewService.getViews(businessCode, userTask.getId()));
			taskManages.add(taskManage);
		}
		businessManage.setTaskManages(taskManages);
		return businessManage;
	}


	@Override
	public BusinessManage getActions(String businessCode) {
		BusinessManage businessManage = new BusinessManage();

		BusinessBase businessBase = businessBaseService.findByCode(businessCode);
		businessManage.setBase(businessBase);

		String processDefinitionId = businessBase.getProcessId();
		Process process = procInfoService.getMainProcess(processDefinitionId);

		ProcManage procManage = new ProcManage();
		procManage.setId(processDefinitionId);
		procManage.setName(process.getName());
		procManage.setActions(businessActionService.getActions(businessCode, processDefinitionId));
		businessManage.setProcManage(procManage);

		List<TaskManage> taskManages = new ArrayList<TaskManage>();
		List<UserTask> userTasks = procInfoService.getUserTasks(process);
		for (UserTask userTask : userTasks) {
			TaskManage taskManage = new TaskManage();
			taskManage.setId(userTask.getId());
			taskManage.setName(userTask.getName());
			taskManage.setActions(businessActionService.getActions(businessCode, userTask.getId()));
			taskManages.add(taskManage);
		}
		businessManage.setTaskManages(taskManages);
		return businessManage;
	}


	@Override
	public BusinessManage getTimiLimit(String businessCode) {
		BusinessManage businessManage = new BusinessManage();

		BusinessBase businessBase = businessBaseService.findByCode(businessCode);
		businessManage.setBase(businessBase);

		String processDefinitionId = businessBase.getProcessId();
		Process process = procInfoService.getMainProcess(processDefinitionId);

		ProcManage procManage = new ProcManage();
		procManage.setId(processDefinitionId);
		procManage.setName(process.getName());
		procManage.setTimeLimit(businessTimeLimitService.getTimeLimit(businessCode, processDefinitionId));
		businessManage.setProcManage(procManage);
		
		List<TaskManage> taskManages = new ArrayList<TaskManage>();
		List<UserTask> userTasks = procInfoService.getUserTasks(process);
		for (UserTask userTask : userTasks) {
			TaskManage taskManage = new TaskManage();
			taskManage.setId(userTask.getId());
			taskManage.setName(userTask.getName());
			taskManage.setTimeLimit(businessTimeLimitService.getTimeLimit(businessCode, userTask.getId()));
			taskManages.add(taskManage);
		}
		businessManage.setTaskManages(taskManages);
		return businessManage;
	}

	


	@Override
	public BusinessManage getExecutors(String businessCode) {
		BusinessManage businessManage = new BusinessManage();
		
		BusinessBase businessBase = businessBaseService.findByCode(businessCode);
		businessManage.setBase(businessBase);
		
		String processDefinitionId = businessBase.getProcessId();
		Process process = procInfoService.getMainProcess(processDefinitionId);
		
		ProcManage procManage = new ProcManage();
		procManage.setId(processDefinitionId);
		procManage.setName(process.getName());
		procManage.setExecutors(businessExecutorService.getExecutors(businessCode, processDefinitionId));
		businessManage.setProcManage(procManage);

		List<TaskManage> taskManages = new ArrayList<>();
		List<UserTask> userTasks = procInfoService.getUserTasks(process);
		for (UserTask userTask : userTasks) {
			TaskManage taskManage = new TaskManage();
			taskManage.setId(userTask.getId());
			taskManage.setName(userTask.getName());
			taskManage.setExecutors(businessExecutorService.getExecutors(businessCode, userTask.getId()));
			taskManages.add(taskManage);
		}
		businessManage.setTaskManages(taskManages);
		return businessManage;
	}

}
