package com.huasisoft.flow.business.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.huasisoft.flow.business.entity.BaseExecutor;
import com.huasisoft.flow.business.mapper.BaseExecutorDmMapper;
import com.huasisoft.flow.business.service.BusinessExecutorService;
import com.huasisoft.flow.common.util.IDCreator;
import com.huasisoft.flow.platform.service.impl.CurrentUserHelper;
import com.huasisoft.flow.platform.vo.Person;

//@Transactional(readOnly=true)
@Service
public class BusinessExecutorServiceImpl implements BusinessExecutorService{
	
	@Autowired
	private BaseExecutorDmMapper baseExecutorDmMapper;
	
	@Override
	public List<BaseExecutor> getExecutors(String businessCode, String flowId) {
		QueryWrapper<BaseExecutor> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("BIZ_CODE",businessCode ).eq("TASK_ID",flowId );
		return baseExecutorDmMapper.selectList(queryWrapper);
	}

	@Override
	public int deleteExecutorById(String id) {
		return baseExecutorDmMapper.deleteById(id);
	}

	@Override
	public List<BaseExecutor> save(List<BaseExecutor> baseExecutors) {
		Person person = CurrentUserHelper.currentUser();
		List<BaseExecutor> result = new ArrayList<BaseExecutor>(baseExecutors.size());
		for (BaseExecutor baseExecutor : baseExecutors) {
			if(StringUtils.isBlank(baseExecutor.getId())) {
				baseExecutor.setCreateTime(new Date());
				baseExecutor.setCreatePersonId(person.getId());
				baseExecutor.setCreatePersonName(person.getName());
				baseExecutor.setId(IDCreator.genId());
				if(baseExecutor.getType().equals("Person")){
					baseExecutor.setType("1");
				}else {
					continue;
				}
				baseExecutorDmMapper.insert(baseExecutor);
			}else {
				baseExecutorDmMapper.updateById(baseExecutor);
			}
			result.add(baseExecutor);
		}
		return result;
	}

	@Override
	public BaseExecutor getExecutorById(String id) {
		return baseExecutorDmMapper.selectById(id);
	}
}
