package com.huasisoft.flow.business.service;

import java.util.List;

import com.huasisoft.flow.business.entity.BaseAction;
import com.huasisoft.flow.business.entity.BaseExecutor;
import com.huasisoft.flow.business.entity.BaseTimeLimit;
import com.huasisoft.flow.business.entity.BaseView;
import com.huasisoft.flow.business.vo.BusinessManage;
/**
 * 业务管理接口，根据流程图组装数据
 * @author Administrator
 *
 */
public interface BusinessManageService {
	
	/**
	 * 获取执行人信息
	 * @param businessCode
	 * @return
	 */
	BusinessManage getExecutors(String businessCode);
	
	/**
	 * 获取视图信息
	 * @param businessCode
	 * @return
	 */
	BusinessManage getViews(String businessCode);
	
	/**
	 * 获取按钮信息
	 * @param businessCode
	 * @return
	 */
	BusinessManage getActions(String businessCode);
	
	/**
	 * 获取时限信息
	 * @param businessCode
	 * @return
	 */
	BusinessManage getTimiLimit(String businessCode);
	

}
