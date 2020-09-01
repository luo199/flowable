package com.huasisoft.flow.common.controller;

import com.huasisoft.flow.common.vo.JsonResult;
import org.flowable.ui.common.model.UserRepresentation;
import org.flowable.ui.common.security.DefaultPrivileges;
import org.jasig.cas.client.validation.Assertion;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import static com.huasisoft.h1.constant.ACOperationConst.OPERATION_SYSTEM_BROWSE;


@RestController
@RequestMapping("/app")
public class FlowableController {

/*
     * 获取默认的管理员信息
     * @return
     */


    @RequestMapping(value = "/rest/account", method = RequestMethod.GET, produces =
            "application/json")
    public UserRepresentation getAccount(HttpSession session) {
        UserRepresentation userRepresentation = new UserRepresentation();

        Assertion assertion = session != null ? (Assertion) session.getAttribute("_const_cas_assertion_") : null;


       /* var userName = '${Session._const_cas_assertion_.principal.attributes.name}';
        var userId = '${_const_cas_assertion_.principal.attributes.ID}';
        var bureauId = '${_const_cas_assertion_.principal.attributes.bureauID}';
        var bureauName = '${_const_cas_assertion_.principal.attributes.bureauName}';
        var deptId = '${_const_cas_assertion_.principal.attributes.deptID}';
        var userSex = "男";*/

        if(assertion!=null) {
            Object currentUserID = assertion.getPrincipal().getAttributes().get("ID");
            userRepresentation.setId(String.valueOf(currentUserID));
            Object name = assertion.getPrincipal().getAttributes().get("name");
            userRepresentation.setEmail("");
            userRepresentation.setFullName(String.valueOf(name));
        }else{
            userRepresentation.setId("admin");
            userRepresentation.setEmail("admin@flowable.org");
            userRepresentation.setFullName("Administrator");
//        userRepresentation.setLastName("Administrator");
        }


        userRepresentation.setFirstName("");
        List<String> privileges = new ArrayList<>();
        privileges.add(DefaultPrivileges.ACCESS_MODELER);
        privileges.add(DefaultPrivileges.ACCESS_IDM);
        privileges.add(DefaultPrivileges.ACCESS_ADMIN);
        privileges.add(DefaultPrivileges.ACCESS_TASK);
        privileges.add(DefaultPrivileges.ACCESS_REST_API);
        userRepresentation.setPrivileges(privileges);
        return userRepresentation;
    }
}
