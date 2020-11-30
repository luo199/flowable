package com.huasisoft.flow.business.service;

import java.util.List;

import com.huasisoft.flow.business.entity.BaseView;

/**
 * 业务视图配置接口
 * @author Administrator
 *
 */
public interface BusinessViewService {
	
	public List<BaseView> getViews(String businessCode, String flowId);

	BaseView save(BaseView baseView);

	int delete(String id);

	BaseView findOne(String id);

	int deleteByTaskId(String taskId);








	
}
