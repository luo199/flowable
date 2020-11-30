package com.huasisoft.flow.business.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.huasisoft.flow.business.entity.BaseAction;
import com.huasisoft.flow.business.mapper.BaseActionMapper;
import com.huasisoft.flow.business.service.BusinessActionService;
import com.huasisoft.flow.common.util.IDCreator;

@Service
@Transactional(readOnly=true)
public class BusinessActionServiceImpl implements BusinessActionService{
	@Autowired
	private BaseActionMapper baseActionMapper;
	
	@Transactional
	@Override
	public BaseAction saveAction(BaseAction baseAction) {
		Assert.notNull(baseAction, "不能为空");
		if(StringUtils.isBlank(baseAction.getId())) {
			baseAction.setId(IDCreator.genId());
			baseActionMapper.insert(baseAction);
		}else {
			baseActionMapper.updateById(baseAction);
		}
		return baseAction;
	}

	@Transactional
	@Override
	public int delete(String id) {
		HashMap<String, Object> deleteMap = new HashMap<>();
		deleteMap.put("ID", id);
		return baseActionMapper.deleteByMap(deleteMap);
	}
	
	@Override
	public BaseAction findOne(String id) {
		return baseActionMapper.selectById(id);
	}
	
	@Transactional
	@Override
	public int deleteByBizCodeAndActionId(String bizCode,String actionId,String id) {
		HashMap<String, Object> deleteMap = new HashMap<String, Object>();
		deleteMap.put("ACTION_ID", actionId);
		deleteMap.put("BIZ_CODE", bizCode);
		List<BaseAction> actions = baseActionMapper.selectByMap(deleteMap);
		List<String> ids = new ArrayList<>();
		for (BaseAction action: actions) {
			if(action.getId().equals(id)){
				continue;
			}
			ids.add(action.getId());
		}
		if(ids.size()>0){
			baseActionMapper.deleteBatchIds(ids);
		}
		return 0;
	}

	@Override
	public List<BaseAction> getActions(String businessCode, String flowId) {
		QueryWrapper<BaseAction> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("BIZ_CODE",businessCode ).eq("TASK_ID",flowId ).orderByAsc("ORDER_NUMBER");
		return baseActionMapper.selectList(queryWrapper);
	}
}
