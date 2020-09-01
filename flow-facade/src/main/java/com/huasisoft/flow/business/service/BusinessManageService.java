package com.huasisoft.flow.business.service;

import java.util.List;

import com.huasisoft.flow.business.entity.BaseAction;
import com.huasisoft.flow.business.entity.BaseTimeLimit;
import com.huasisoft.flow.business.entity.BaseView;
import com.huasisoft.flow.business.vo.FlowManage;

public interface BusinessManageService {

	List<FlowManage> getViews(String businessCode);
	
	List<BaseView> getViews(String businessCode,String flowId);
	
	List<FlowManage> getActions(String businessCode);
	
	List<BaseAction> getActions(String businessCode,String flowId);
	
	List<FlowManage> getTimiLimit(String businessCode);
	
	BaseTimeLimit getTimeLimit(String businessCode,String flowId);
	
}
