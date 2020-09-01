package com.huasisoft.flow.test.service.impl;

import com.alibaba.dubbo.common.bytecode.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huasisoft.flow.test.entity.TestAdd;
import com.huasisoft.flow.test.entity.TestPageRequest;
import com.huasisoft.flow.test.mapper.TestAddMapper;
import com.huasisoft.flow.test.service.TestAddService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author lb
 * @since 2020-08-21
 */
@Service
public class TestAddServiceImpl implements TestAddService {

    @Autowired
    private TestAddMapper mapper;
    private Map<Integer, List<TestAdd>> map;

    @Override
    public TestAdd getById(String id) {
        return mapper.selectById(id);
    }

    @Override
    public IPage<TestAdd> page( TestPageRequest request) {
        IPage<TestAdd> page = new Page<>(request.getCurrent(),request.getSize());
        return mapper.searchByName(page, request.getName());
    }


    @Override
    public List<TestAdd> list() {
        return mapper.selectList(Wrappers.emptyWrapper());
    }

    public Map<Integer, List<TestAdd>> count() {
        Integer s = mapper.selectCount(Wrappers.emptyWrapper());
        List<TestAdd> li = mapper.selectList(Wrappers.emptyWrapper());
        map.keySet().add(s);
        map.values().add(li);
        return map;
    }

    @Override
    @Transactional
    public void save(TestAdd testAdd) {
        mapper.insert(testAdd);
    }

    @Override
    public List<TestAdd> likeSearch(String name) {
        QueryWrapper<TestAdd> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().like(TestAdd::getName,name);
        List<TestAdd> list = (List<TestAdd>) queryWrapper;
        return list;

    }
}
