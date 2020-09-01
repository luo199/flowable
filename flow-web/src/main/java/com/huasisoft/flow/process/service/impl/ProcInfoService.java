package com.huasisoft.flow.process.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.SequenceFlow;
import org.flowable.bpmn.model.UserTask;
import org.flowable.common.engine.api.FlowableObjectNotFoundException;
import org.flowable.engine.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import  org.flowable.bpmn.model.Process;

@Service
public class ProcInfoService {

	@Autowired
	private RepositoryService repositoryService;
	
	public BpmnModel getBpmnModel(String processDefinitionId){
		BpmnModel bpmnModel = null;
		try {
			bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
		} catch (FlowableObjectNotFoundException e) {
		}
		Assert.notNull(bpmnModel, "流程模型数据不能为空");
		return bpmnModel;
	}
	
	public Process getMainProcess(String processDefinitionId){
		BpmnModel bpmnModel = getBpmnModel(processDefinitionId);
		Process process = bpmnModel.getMainProcess();
		Assert.notNull(process, "主流程不能为空");
		return process;
	}
	
	public List<UserTask> getUserTasks(String processDefinitionId){
		Process process = getMainProcess(processDefinitionId);
		List<UserTask> userTasks = process.findFlowElementsOfType(UserTask.class, true);
		return userTasks;
	}
	
	public List<UserTask> getUserTasks(Process process){
		Assert.notNull(process, "流程不能为空");
		List<UserTask> userTasks = process.findFlowElementsOfType(UserTask.class, true);
		return userTasks;
	}
	
	public List<SequenceFlow> getSequeceFlows(String processDefinitionId){
		Process process = getMainProcess(processDefinitionId);
		List<SequenceFlow> sequeceFlows = process.findFlowElementsOfType(SequenceFlow.class, true);
		return sequeceFlows;
	}
	
	public List<SequenceFlow> getSequeceFlows(Process process){
		Assert.notNull(process, "流程不能为空");
		List<SequenceFlow> sequeceFlows = process.findFlowElementsOfType(SequenceFlow.class, true);
		return sequeceFlows;
	}
}
