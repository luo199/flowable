package com.huasisoft.flow.test.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huasisoft.flow.test.entity.TestUser;
import com.huasisoft.flow.test.mapper.TestUserMapper;
import com.huasisoft.flow.test.service.TestUserService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author flq
 * @since 2020-07-23
 */
@Service
@Transactional(readOnly=true)
public class TestUserServiceImpl implements TestUserService {

	
	@Autowired
	private TestUserMapper mapper;
	private Map<Integer,List<TestUser>> map;

	@Override
	public TestUser getById(String id) {
		return mapper.selectById(id);
	}

	@Override
	public List<TestUser> list() {
		return mapper.selectList(Wrappers.emptyWrapper());
	}

	@Override
	public IPage<TestUser> page() {
		IPage<TestUser> userPage = new Page<>(1, 2);//参数一是当前页，参数二是每页个数
		return mapper.selectPage(userPage,Wrappers.emptyWrapper());
	}

	public Map<Integer,List<TestUser>> count(){
		Integer s = mapper.selectCount(Wrappers.emptyWrapper());
		List<TestUser> list = mapper.selectList(Wrappers.lambdaQuery());
		 map.keySet().add(s);
		 map.values().add(list);
		return map;
	}



}
