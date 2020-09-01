package com.huasisoft.flow.test.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.huasisoft.flow.process.vo.PageRequest;
import com.huasisoft.flow.test.entity.TestAdd;
import com.huasisoft.flow.test.entity.TestPageRequest;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lb
 * @since 2020-08-21
 */
public interface TestAddService {
    TestAdd getById(String id);
    List<TestAdd> list();
    IPage<TestAdd> page( TestPageRequest request);
    void save(TestAdd testAdd);
    List<TestAdd> likeSearch(String name);
}
