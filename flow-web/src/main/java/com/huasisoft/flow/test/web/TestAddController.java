package com.huasisoft.flow.test.web;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.huasisoft.flow.process.vo.PageRequest;
import com.huasisoft.flow.test.entity.TestAdd;
import com.huasisoft.flow.test.entity.TestPageRequest;
import com.huasisoft.flow.test.service.TestAddService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author lb
 * @since 2020-08-21
 */
@Controller
@RequestMapping("/api/test")
public class TestAddController {
    @Autowired
    private TestAddService testAddService;

    @GetMapping("/getbyid")
    private TestAdd getById(String id) {
        return testAddService.getById(id);
    }

    @PostMapping("/page")
    @ResponseBody
    private IPage<TestAdd> page(@RequestBody TestPageRequest request) {
        System.out.println(request);
        return testAddService.page(request);
    }

    @GetMapping("/list")
    private List<TestAdd> list() {
        return testAddService.list();
    }

    @PostMapping("/save")
    @ResponseBody
    private void save(@RequestBody TestAdd testAdd) {
        System.out.println(testAdd);


//        testAddService.save(testAdd);
    }


    @PostMapping("/search")
    @ResponseBody
    private List<TestAdd> likeSearch(@RequestBody String name) {





        return testAddService.likeSearch(name);
    }


}
