package com.huasisoft.flow.process.web;


import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

import com.huasisoft.flow.process.service.impl.ProcInfoService;
import org.apache.commons.lang3.StringUtils;
import org.flowable.bpmn.BpmnAutoLayout;
import org.flowable.bpmn.converter.BpmnXMLConverter;
import org.flowable.bpmn.model.*;
import org.flowable.common.engine.api.FlowableObjectNotFoundException;
import org.flowable.editor.language.json.converter.BpmnJsonConverter;
import org.flowable.editor.language.json.converter.util.CollectionUtils;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.ui.common.security.SecurityUtils;
import org.flowable.ui.common.service.exception.BadRequestException;
import org.flowable.ui.common.util.XmlUtil;
import org.flowable.ui.modeler.domain.AbstractModel;
import org.flowable.ui.modeler.domain.Model;
import org.flowable.ui.modeler.model.ModelRepresentation;
import org.flowable.ui.modeler.serviceapi.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.huasisoft.flow.common.vo.JsonResult;
import com.huasisoft.flow.process.entity.ActDeModel;
import com.huasisoft.flow.process.entity.ActReProcdef;
import com.huasisoft.flow.process.service.ActDeModelService;
import com.huasisoft.flow.process.service.ActReProcdefService;
import com.huasisoft.flow.process.vo.ManagePageRequest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author flq
 * @since 2020-07-23
 */
@RestController
@RequestMapping("/api/process/manage")
@Api(value="流程部署及管理1",description="流程部署及管理2",tags="流程部署及管理s")
public class ProcessManageController {
	
	@Autowired
	private ProcessEngine processEngine;
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private ModelService modelService;
	@Autowired
	private ActReProcdefService actReProcdefService;
	@Autowired
	private ProcInfoService procInfoService;
	
	@Autowired
	private ActDeModelService actDeModelService;
	
	BpmnXMLConverter bpmnXmlConverter = new BpmnXMLConverter();
	BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();
	
	/*@PostMapping("zipDeploy")
	public void deploymentProcessDefinition_zip(){
		InputStream in = this.getClass().getClassLoader().getResourceAsStream("diagrams/helloworld.zip");
		ZipInputStream zipInputStream = new ZipInputStream(in);
		Deployment deployment = processEngine.getRepositoryService()//与流程定义和部署对象相关的Service
						.createDeployment()//创建一个部署对象
						.name("流程定义")//添加部署的名称
						.addZipInputStream(zipInputStream)//指定zip格式的文件完成部署
						.deploy();//完成部署
		System.out.println("部署ID："+deployment.getId());//
		System.out.println("部署名称："+deployment.getName());//
	}
	*/
	
	@PostMapping("xmlDeploy")
	@ApiOperation("通过xml直接部署流程")
	public  JsonResult<Deployment> xmlDeploy(@RequestParam("file") MultipartFile file) throws IOException{
		if (file.isEmpty()) {
			return new JsonResult<>("上传失败，请选择文件",500);
        }
		String fileName = file.getOriginalFilename();
        if (fileName != null && (fileName.endsWith(".bpmn") || fileName.endsWith(".bpmn20.xml"))) {
            try {
                XMLInputFactory xif = XmlUtil.createSafeXmlInputFactory();
                InputStreamReader xmlIn = new InputStreamReader(file.getInputStream(), "UTF-8");
                XMLStreamReader xtr = xif.createXMLStreamReader(xmlIn);
                BpmnModel bpmnModel = bpmnXmlConverter.convertToBpmnModel(xtr);
                if (CollectionUtils.isEmpty(bpmnModel.getProcesses())) {
                    throw new BadRequestException("No process found in definition " + fileName);
                }

                if (bpmnModel.getLocationMap().size() == 0) {
                    BpmnAutoLayout bpmnLayout = new BpmnAutoLayout(bpmnModel);
                    bpmnLayout.execute();
                }

                ObjectNode modelNode = bpmnJsonConverter.convertToJson(bpmnModel);

                org.flowable.bpmn.model.Process process = bpmnModel.getMainProcess();
                String name = process.getId();
                if (StringUtils.isNotEmpty(process.getName())) {
                    name = process.getName();
                }
                String description = process.getDocumentation();

                ModelRepresentation model = new ModelRepresentation();
                model.setKey(process.getId());
                model.setName(name);
                model.setDescription(description);
                model.setModelType(AbstractModel.MODEL_TYPE_BPMN);
                ActDeModel existsModel = actDeModelService.getByModelKey(model.getKey());
                Model newModel = null;
                if(existsModel==null) {
                	newModel = modelService.createModel(model, modelNode.toString(), SecurityUtils.getCurrentUserObject());
                }else {
                	newModel = modelService.saveModel(existsModel.getId(), name, process.getId(), description, 
                			modelNode.toString(), true, model.getComment(), SecurityUtils.getCurrentUserObject());
                }
                byte[] bytes = modelService.getBpmnXML(newModel);
        		if(bytes==null){
        		    return new JsonResult<>("模型数据为空，请先设计流程并成功保存，再进行发布。",500);
        		}

        		//BpmnModel bpmnModel = modelService.getBpmnModel(newModel);
        		if(bpmnModel.getProcesses().size()==0){
        		    return new JsonResult<>("数据模型不符要求，请至少设计一条主线流程。",500);
        		}
        		byte[] bpmnBytes = new BpmnXMLConverter().convertToXML(bpmnModel);
        		String processName = newModel.getName()+".bpmn20.xml";
        		Deployment deployment = repositoryService.createDeployment()
        		        .name(newModel.getName())
        		        .addBytes(processName,bpmnBytes)
        		        .deploy();
        		return new JsonResult<>(deployment);
            } catch (BadRequestException e) {
                throw e;

            } catch (Exception e) {
                throw new BadRequestException("Import failed for " + fileName + ", error message " + e.getMessage());
            }
        } else {
        	return new JsonResult<>("Invalid file name, only .bpmn and .bpmn20.xml files are supported not " + fileName,500);
        }
	}
	
	@GetMapping("/suspend")
	@ApiOperation("挂起流程")
	public JsonResult<Integer>  suspendProcessDefinitionById(String processDefinitionId) {
		JsonResult<Integer> returnVo = null;
        repositoryService.suspendProcessDefinitionById(processDefinitionId, true, null);
        returnVo = new JsonResult<>(1);
        return returnVo;
    }
	@GetMapping("/activate")
	@ApiOperation("激活流程")
	public JsonResult<Integer>  activateProcessDefinitionById(String processDefinitionId) {
		JsonResult<Integer> returnVo = null;
		repositoryService.activateProcessDefinitionById(processDefinitionId, true, null);
		returnVo = new JsonResult<>(1);
		return returnVo;
	}
	@GetMapping("/delete")
	@ApiOperation("删除流程部署")
	public JsonResult<Integer>  deleteProcessDefinitionById(String processDefinitionId) {
		JsonResult<Integer> returnVo = null;
		ProcessDefinition definition = repositoryService//与流程定义和部署对象相关的Service
		.createProcessDefinitionQuery()//创建一个流程定义的查询
		.processDefinitionId(processDefinitionId)
		.singleResult();
		repositoryService.deleteDeployment(definition.getDeploymentId(), false);
		returnVo = new JsonResult<>(1);
		return returnVo;
	}
	
	
	@PostMapping("/definition/page")
	@ApiOperation("流程管理列表")
	public  JsonResult<IPage<ActReProcdef>> pageProcdef(@RequestBody ManagePageRequest page){
				
		return new JsonResult<>(actReProcdefService.procPage(page));
	}
	
	@PostMapping("/info")
	@ApiOperation("获取流程信息")
	public  JsonResult<Map<String,Object>> procInfo(String processDefinitionId){
		Map<String,Object> result = new HashMap<String, Object>();
		BpmnModel bpmnModel = null;
		try {
			bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
		} catch (FlowableObjectNotFoundException e) {
		}
		
		if(bpmnModel==null) {
			return new JsonResult<>("未找到流程信息",600);
		}
		org.flowable.bpmn.model.Process process = bpmnModel.getMainProcess();
		List<SequenceFlow> sequeceFlows = process.findFlowElementsOfType(SequenceFlow.class, true);
		List<UserTask> userTasks = process.findFlowElementsOfType(UserTask.class, true);
		//result.put("bpmnModel", bpmnModel);
		result.put("sequeceFlows", sequeceFlows);
		result.put("userTasks", userTasks);
		return new JsonResult<>(result);
	}
	
	

	@PostMapping("/listActivateProcess")
	@ApiOperation("查询所有已部署并且激活的流程")
	public JsonResult<List<ActReProcdef>>  listActivateProcess() {
		return new JsonResult<List<ActReProcdef>>(actReProcdefService.listActivateProcess());
	}

	
}
