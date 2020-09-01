package com.huasisoft.flow.test.web;


import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.huasisoft.flow.common.vo.JsonResult;
import com.huasisoft.flow.test.entity.TestUser;
import com.huasisoft.flow.test.service.TestUserService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author flq
 * @since 2020-07-23
 */
@RestController
@RequestMapping("//test-user")
public class TestUserController {
	
	@Autowired
	private TestUserService testUserService;
	
	@GetMapping("findById")
	public  JsonResult<TestUser> findById(String id){
		return new JsonResult<>(testUserService.getById(id));
	}
	@GetMapping("listAll")
	public  JsonResult<List<TestUser>> listAll(){
		return new JsonResult<>(testUserService.list());
	}
	@GetMapping("page")
	public  JsonResult<IPage<TestUser>> page(){
		return new JsonResult<>(testUserService.page());
	}
	
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private ModelService modelService;
	
	BpmnXMLConverter bpmnXmlConverter = new BpmnXMLConverter();
	BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();

	@PostMapping("xmlDeploy")
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
                Model newModel = modelService.createModel(model, modelNode.toString(), SecurityUtils.getCurrentUserObject());
                
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
       // return new JsonResult<>("模型数据为空，请先设计流程并成功保存，再进行发布。",500);
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
	
	@GetMapping("deployList")
	public  JsonResult<List<org.flowable.engine.repository.Model>> deploy(){
		/*select t.*, t.rowid from ACT_DE_MODEL t;
		select * from act_re_deployment;
		select * from act_re_procdef;
		--select * from act_re_model;
		select * from act_ge_bytearray;*/
		List<org.flowable.engine.repository.Model> models = repositoryService.createModelQuery().listPage(1, 10);
		return new JsonResult<>(models);
	}
}
