package com.huasisoft.flow.process.web;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.FlowElement;
import org.flowable.engine.HistoryService;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.image.ProcessDiagramGenerator;
import org.flowable.image.impl.DefaultProcessDiagramGenerator;
import org.flowable.ui.modeler.serviceapi.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huasisoft.flow.common.vo.JsonResult;

import io.netty.handler.codec.json.JsonObjectDecoder;
import io.swagger.annotations.Api;

@Controller
@RequestMapping("/api/draw")
@Api(value="流程图相关接口")
public class ProcessDrawController {
	
	@Autowired
	private ProcessEngine processEngine;
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private ModelService modelService;
	
	public boolean isFinished(String processInstanceId) {
        return historyService.createHistoricProcessInstanceQuery().finished()
                .processInstanceId(processInstanceId).count() > 0;
    }
	
	@GetMapping("/instance/bmp")
	 public void genProcessDiagram(HttpServletResponse httpServletResponse, String processId) {
	        /**
	         * 获得当前活动的节点
	         */
	        String processDefinitionId = "";
	        if (this.isFinished(processId)) {// 如果流程已经结束，则得到结束节点
	            HistoricProcessInstance pi = historyService.createHistoricProcessInstanceQuery().processInstanceId(processId).singleResult();

	            processDefinitionId=pi.getProcessDefinitionId();
	        } else {// 如果流程没有结束，则取当前活动节点
	            // 根据流程实例ID获得当前处于活动状态的ActivityId合集
	            ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(processId).singleResult();
	            processDefinitionId=pi.getProcessDefinitionId();
	        }
	        List<String> highLightedActivitis = new ArrayList<String>();

	        /**
	         * 获得活动的节点
	         */
	        List<HistoricActivityInstance> highLightedActivitList =  historyService.createHistoricActivityInstanceQuery().processInstanceId(processId).orderByHistoricActivityInstanceStartTime().asc().list();

	        for(HistoricActivityInstance tempActivity : highLightedActivitList){
	            String activityId = tempActivity.getActivityId();
	            highLightedActivitis.add(activityId);
	        }

	        List<String> flows = new ArrayList<>();
	        //获取流程图
	        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
	        ProcessEngineConfiguration engconf = processEngine.getProcessEngineConfiguration();

	        ProcessDiagramGenerator diagramGenerator = engconf.getProcessDiagramGenerator();
	        InputStream in = diagramGenerator.generateDiagram(bpmnModel, "bmp", highLightedActivitis, flows, "宋体",
	        		"宋体", "宋体", engconf.getClassLoader(), 1.0, true);
	        OutputStream out = null;
	        byte[] buf = new byte[1024];
	        int legth = 0;
	        try {
	            out = httpServletResponse.getOutputStream();
	            while ((legth = in.read(buf)) != -1) {
	                out.write(buf, 0, legth);
	            }
	        } catch (IOException e) {
	        } finally {
	            IOUtils.closeQuietly(out);
	            IOUtils.closeQuietly(in);
	        }
	        
	    }
	 
	 
	 @GetMapping("/process/png")
	 public void genProcessPngImage(HttpServletResponse httpServletResponse, String processDefinitionId) {

	        //获取流程图
	        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
	        ProcessEngineConfiguration engconf = processEngine.getProcessEngineConfiguration();

	        ProcessDiagramGenerator diagramGenerator = engconf.getProcessDiagramGenerator();
	        /*InputStream in = diagramGenerator.generateDiagram(bpmnModel, "bmp", Collections.<String>emptyList(), Collections.<String>emptyList(), engconf.getActivityFontName(),
	                engconf.getLabelFontName(), engconf.getAnnotationFontName(), engconf.getClassLoader(), 1.0, true);*/
	        InputStream in = diagramGenerator.generateDiagram(bpmnModel, "png", Collections.<String>emptyList(), Collections.<String>emptyList(), "宋体",
	        		"宋体", "宋体", engconf.getClassLoader(), 1.0, true);
	        httpServletResponse.setHeader("Content-Type", "image/png");
	        OutputStream out = null;
	        byte[] buf = new byte[1024];
	        int legth = 0;
	        try {
	            out = httpServletResponse.getOutputStream();
	            while ((legth = in.read(buf)) != -1) {
	                out.write(buf, 0, legth);
	            }
	        } catch (IOException e) {
	        } finally {
	            IOUtils.closeQuietly(out);
	            IOUtils.closeQuietly(in);
	        }
	    }
	 
	 @GetMapping("/process/png/base64")
	 @ResponseBody
	 public JsonResult<Map<String,Object>> genProcessPngImageBase64(String processDefinitionId) throws IOException {
		 Map<String,Object> result = new HashMap<>();
		 //获取流程图
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
        ProcessEngineConfiguration engconf = processEngine.getProcessEngineConfiguration();

        DefaultProcessDiagramGenerator diagramGenerator = new DefaultProcessDiagramGenerator();
        BufferedImage image = diagramGenerator.generateImage(bpmnModel, "png", Collections.<String>emptyList(), Collections.<String>emptyList(),
        		"宋体","宋体", "宋体", engconf.getClassLoader(), 1.0,true);
        System.out.println(image.getTileHeight());
        System.out.println(image.getTileWidth());
        System.out.println(image.getHeight());
        System.out.println(image.getWidth());
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(image, "png", os);
        byte[] obt = os.toByteArray();
        String imgStr = Base64Utils.encodeToString(obt);
        result .put("width", image.getWidth());
        result .put("height", image.getHeight());
        result .put("image", imgStr);
        return new JsonResult<>(result);
	 }
	 
	 @GetMapping("/process/jpg")
	 public void genProcessJpgImage(HttpServletResponse httpServletResponse, String processDefinitionId) {

	        //获取流程图
	        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
	        ProcessEngineConfiguration engconf = processEngine.getProcessEngineConfiguration();

	        ProcessDiagramGenerator diagramGenerator = engconf.getProcessDiagramGenerator();
	        /*InputStream in = diagramGenerator.generateDiagram(bpmnModel, "bmp", Collections.<String>emptyList(), Collections.<String>emptyList(), engconf.getActivityFontName(),
	                engconf.getLabelFontName(), engconf.getAnnotationFontName(), engconf.getClassLoader(), 1.0, true);*/
	        InputStream in = diagramGenerator.generateDiagram(bpmnModel, "jpg", Collections.<String>emptyList(), Collections.<String>emptyList(), "宋体",
	        		"宋体", "宋体", engconf.getClassLoader(), 1.0, true);
	        httpServletResponse.setHeader("Content-Type", "image/jpeg");
	        OutputStream out = null;
	        byte[] buf = new byte[1024];
	        int legth = 0;
	        try {
	            out = httpServletResponse.getOutputStream();
	            while ((legth = in.read(buf)) != -1) {
	                out.write(buf, 0, legth);
	            }
	        } catch (IOException e) {
	        } finally {
	            IOUtils.closeQuietly(out);
	            IOUtils.closeQuietly(in);
	        }
	    }
	 

	/**
	 * 获取模型所有节点属性
	 */
	public Collection<FlowElement> getProcessNodes(String modelId) {
	    org.flowable.ui.modeler.domain.Model modelData = modelService.getModel(modelId);
	    //获取模型
	    BpmnModel model = modelService.getBpmnModel(modelData);
	    //Process对象封装了全部的节点、连线、以及关口等信息。拿到这个对象就能够为所欲为了。
	    org.flowable.bpmn.model.Process process = model.getProcesses().get(0);
	    //获取全部的FlowElement（流元素）信息
	    Collection<FlowElement> flowElements = process.getFlowElements();
	    return flowElements;
	}


}
