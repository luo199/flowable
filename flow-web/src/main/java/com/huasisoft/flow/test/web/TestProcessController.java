package com.huasisoft.flow.test.web;

import com.huasisoft.flow.business.entity.BaseHolidayForm;
import com.huasisoft.flow.business.entity.BusinessBase;
import com.huasisoft.flow.business.service.*;
import com.huasisoft.flow.business.vo.HolidayManage;
import com.huasisoft.flow.common.vo.JsonResult;
import com.huasisoft.flow.process.service.impl.ProcInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.flowable.bpmn.model.UserTask;
import org.flowable.engine.*;
import org.flowable.engine.FormService;
import org.flowable.engine.form.FormProperty;
import org.flowable.engine.form.StartFormData;
import org.flowable.engine.form.TaskFormData;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.form.api.*;
import org.flowable.form.engine.FormEngine;
import org.flowable.form.model.FormField;
import org.flowable.form.model.SimpleFormModel;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/test")
@Api("请假管理接口")
public class TestProcessController {

    @Autowired
    private HolidayManageService holidayManageService;
    @Autowired
    private ProcessEngine processEngine;
    @Autowired
    private BusinessBaseService businessBaseService;
    @Autowired
    private BaseHolidayFormService baseHolidayFormService;
    @Autowired
    private BusinessExecutorService businessExecutorService;
    @Autowired
    private BaseExecutorDetailService baseExecutorDetailService;
    @Autowired
    private FormService formService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private FormRepositoryService formRepositoryService;
    @Autowired
    private ProcInfoService procInfoService;
    @Autowired
    private FormEngine formEngine;

    @GetMapping("startHolidayProcess")
    @ApiOperation("实例化流程开始执行")
    public JsonResult<Integer> startHolidayProcess(
            @ApiParam(value = "businessCode", name = "业务Code", required = true) String businessCode
           ) {
        businessCode = "biaodan";
        TaskService taskService = processEngine.getTaskService();
        RuntimeService runtimeService = processEngine.getRuntimeService();

        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionKey("qxjlc_test").latestVersion().singleResult();
        Deployment deployment = repositoryService.createDeploymentQuery().deploymentKey("qxjlc_test").singleResult();
        FormDeployment formDeployment = formRepositoryService.createDeployment()
                .name("definition-one")
                .parentDeploymentId(deployment.getId())
                .deploy();
        FormDefinition formDefinition = formRepositoryService.createFormDefinitionQuery().deploymentId(formDeployment.getId()).singleResult();
        System.out.println(formDefinition.getName());

       /* ProcessInstance processInstance = runtimeService.startProcessInstanceWithForm(processDefinition.getId(), null, null, "测试实例");
        System.out.println("id:"+processInstance.getId());
        System.out.println("getBusinessKey"+processInstance.getBusinessKey());
        System.out.println(processInstance.toString());*/
        String processInstanceId = "ca237085-307c-11eb-8260-14dda9ddf274";
        String formKey = formService.getTaskFormKey(processDefinition.getId(), "applicantTask");
        System.out.println(formKey);
        StartFormData form = formService.getStartFormData(processDefinition.getId());
        System.out.println(form);
        List<FormProperty> formProperties = form.getFormProperties();
        for (FormProperty formProperty:formProperties ) {
            System.out.println(formProperty.getName());
        }

        FormInfo info = formRepositoryService.getFormModelByKey(formKey);
        System.out.println(info);

        FormInfo formModel = runtimeService.getStartFormModel(processDefinition.getId(),processInstanceId);//processInstance.getId()
        TaskFormData formData = formService.getTaskFormData("applicantTask");
        System.out.println(formData);
        FormModel taskFormModel = (FormModel) taskService.getTaskFormModel("applicantTask");
        info.getFormModel();
        System.out.println("------------------------------line-----");
        /*SimpleFormModel model = (SimpleFormModel) formModel.getFormModel();
        List<FormField> fields = model.getFields();
        for (FormField ff : fields) {
            System.out.println();
            System.out.println("id: " + ff.getId());
            System.out.println("name: " + ff.getName());
            System.out.println("type: " + ff.getType());
            System.out.println("placeholder: " + ff.getPlaceholder());
            System.out.println("value: " + ff.getValue());
            System.out.println();
        }

        System.out.println(formModel.getId());
        System.out.println(formModel.getName());
        System.out.println(formModel.getKey());
        System.out.println(formModel.getDescription());*/
        Map<String, Object> variables = new HashMap<>();
        List<Task> employeesTasks = taskService.createTaskQuery().taskCandidateGroup("employees").list();
        return new JsonResult<>(1);
    }

}
