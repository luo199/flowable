package com.huasisoft.flow.business.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.huasisoft.flow.common.util.IDCreator;
import com.huasisoft.flow.platform.service.impl.CurrentUserHelper;
import com.huasisoft.flow.platform.vo.Person;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.huasisoft.flow.business.entity.BaseView;
import com.huasisoft.flow.business.mapper.BaseViewDmMapper;
import com.huasisoft.flow.business.service.BusinessViewService;
import org.springframework.util.Assert;

@Transactional(readOnly=true)
@Service
public class BusinessViewServiceImpl implements BusinessViewService {
	
	@Autowired
	private BaseViewDmMapper baseViewMapper;
	
	@Override
	public List<BaseView> getViews(String businessCode, String flowId) {
		QueryWrapper<BaseView> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("BIZ_CODE",businessCode ).eq("TASK_ID",flowId ).orderByAsc("ORDER_NUMBER");
		return baseViewMapper.selectList(queryWrapper);
	}

	@Override
	@Transactional
	public BaseView save(BaseView baseView) {
		Assert.notNull(baseView, "不能为空");
		if (StringUtils.isBlank(baseView.getId())) {
			baseView.setId(IDCreator.genId());
			baseViewMapper.insert(baseView);
		} else {
			baseViewMapper.updateById(baseView);
		}
		return baseView;
	}

	@Override
	@Transactional
	public int delete(String id) {
		return baseViewMapper.deleteById(id);
	}

	@Override
	public BaseView findOne(String id) {
		return baseViewMapper.selectById(id);
	}

	@Override
	@Transactional
	public int deleteByTaskId(String taskId) {
		UpdateWrapper<BaseView> up = new UpdateWrapper<>();
		up.eq("TASK_ID", taskId);
		return baseViewMapper.delete(up);
	}

}
