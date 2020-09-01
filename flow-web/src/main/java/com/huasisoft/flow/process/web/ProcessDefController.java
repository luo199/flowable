package com.huasisoft.flow.process.web;


import java.io.IOException;
import java.io.InputStreamReader;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.huasisoft.flow.process.entity.ActDeModel;
import com.huasisoft.flow.process.service.ActDeModelService;
import com.huasisoft.flow.process.vo.ModelPageRequest;
import org.apache.commons.lang3.StringUtils;
import org.flowable.bpmn.BpmnAutoLayout;
import org.flowable.bpmn.converter.BpmnXMLConverter;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.editor.language.json.converter.BpmnJsonConverter;
import org.flowable.editor.language.json.converter.util.CollectionUtils;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Deployment;
import org.flowable.ui.common.security.SecurityUtils;
import org.flowable.ui.common.service.exception.BadRequestException;
import org.flowable.ui.common.util.XmlUtil;
import org.flowable.ui.modeler.domain.AbstractModel;
import org.flowable.ui.modeler.domain.Model;
import org.flowable.ui.modeler.model.ModelRepresentation;
import org.flowable.ui.modeler.serviceapi.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.huasisoft.flow.common.vo.JsonResult;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author flq
 * @since 2020-07-23
 */
@RestController
@RequestMapping("/api/process")
public class ProcessDefController {

	@Autowired
	private ActDeModelService actDeModelService;
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private ModelService modelService;
	
	BpmnXMLConverter bpmnXmlConverter = new BpmnXMLConverter();
	BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();

	/**
	 * 根据id查询流程模型
	 * @param id
	 * @return
	 */
	@GetMapping("findById")
	public JsonResult<ActDeModel> findById(String id) {
		return new JsonResult<>(actDeModelService.getById(id));
	}

	/**
	 * 根据流程id删除
	 *
	 * @param modelId
	 * @return
	 */
	@DeleteMapping("deleteById")
	public JsonResult<ActDeModel> deleteById(String modelId) {
		ActDeModel model = this.actDeModelService.getById(modelId);

		try {
			this.modelService.deleteModel(model.getId());
			return new JsonResult<>(model);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new JsonResult("删除失败！", 0);
		}
	}

	/**
	 * 查询流程key值是否存在
	 * @param modelKey
	 * @return
	 */
	@GetMapping("findByModelKey")
	public JsonResult<Boolean> findByModelKey(String modelKey) {
		return new JsonResult<>(actDeModelService.findByModelKey(modelKey));
	}

	/**
	 *	根据请求参数分页查询流程列表
	 * @param page
	 * @return
	 */
	@PostMapping("page")
	public JsonResult<IPage<ActDeModel>> page(@RequestBody ModelPageRequest page) {
		return new JsonResult<>(actDeModelService.page(page.getCurrent(), page.getSize(), page.getName()));
	}

	
	@GetMapping("deploy")
	public  JsonResult<Deployment> deploy(@RequestParam String modelId){
		Model modelData =modelService.getModel(modelId);
		byte[] bytes = modelService.getBpmnXML(modelData);
		if(bytes==null){
		    return new JsonResult<>("模型数据为空，请先设计流程并成功保存，再进行发布。",500);
		}

		BpmnModel model = modelService.getBpmnModel(modelData);
		if(model.getProcesses().size()==0){
		    return new JsonResult<>("数据模型不符要求，请至少设计一条主线流程。",500);
		}
		byte[] bpmnBytes = new BpmnXMLConverter().convertToXML(model);
		String processName = modelData.getName()+".bpmn20.xml";
		Deployment deployment = repositoryService.createDeployment()
		        .name(modelData.getName())
		        .addBytes(processName,bpmnBytes)
		        .deploy();
		return new JsonResult<>(deployment);
	}
	
	

	
}
