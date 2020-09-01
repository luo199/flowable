package com.huasisoft.flow.process.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huasisoft.flow.process.entity.ActReProcdef;
import com.huasisoft.flow.process.mapper.ActReProcdefMapper;
import com.huasisoft.flow.process.service.ActReProcdefService;
import com.huasisoft.flow.process.vo.ManagePageRequest;

import java.util.List;

@Service
@Transactional
public class ActReProcdefServiceImpl implements ActReProcdefService {

	@Autowired
	private ActReProcdefMapper mapper;
	
	@Override
	public IPage<ActReProcdef> procPage(ManagePageRequest pageRequst) {
		IPage<ActReProcdef> page = new Page<>(pageRequst.getCurrent(), pageRequst.getSize());//参数一是当前页，参数二是每页个数
		return mapper.maxVersionPage(page,pageRequst.getName());
	}

	@Override
	public List<ActReProcdef> listActivateProcess() {
		return mapper.listActivateProcess();
	}
	
	@Override
	public ActReProcdef findById(String id) {
		return mapper.selectById(id);
	}
	
	@Override
	public List<ActReProcdef> findByKey(String key) {
		QueryWrapper<ActReProcdef> actReProcdefWrapper = new QueryWrapper<ActReProcdef>();
		actReProcdefWrapper.eq("KEY_", key).orderByAsc("VERSION_");
		return mapper.selectList(actReProcdefWrapper);
	}
	
	

}
