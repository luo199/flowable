package com.huasisoft.flow;


import com.huasisoft.flow.business.entity.BusinessBase;
import com.huasisoft.flow.business.service.BusinessBaseService;
import com.huasisoft.flow.test.entity.TestAdd;
import com.huasisoft.flow.test.entity.TestUser;
import com.huasisoft.flow.test.service.TestAddService;
import com.huasisoft.flow.test.service.impl.TestUserServiceImpl;
import org.flowable.dmn.engine.DmnEngine;
import org.flowable.engine.FormService;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.form.api.FormInfo;
import org.flowable.form.api.FormInstance;
import org.flowable.form.engine.FormEngine;
import org.flowable.form.engine.test.FlowableFormRule;
import org.flowable.form.engine.test.FormDeploymentAnnotation;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class TestUserfunc extends AbstractJUnit4SpringContextTests {
    @Autowired
    private TestUserServiceImpl service;
    @Autowired
    private TestAddService tservice;


    @Test
    public void asdf() {
        List<TestUser> users = service.list();
        for (TestUser us : users
        ) {
            System.out.println(us.toString());
        }
    }

    TestAdd testAdd = new TestAdd("762", "李乐乐", 1, new Date(), "jiujiang");

    @Test
    @Rollback(false)
    public void saves() {
        tservice.save(testAdd);
        List<TestAdd> lists = tservice.list();
        for (TestAdd list : lists) {
            System.out.println(list.toString());
        }
    }


    @Test
    public void ssssf() {

        String id = String.valueOf(762);
        TestAdd sh = tservice.getById(id);
        System.out.println(sh);

    }




}
