package com.huasisoft.flow.business.service;

import java.util.List;

import com.huasisoft.flow.business.entity.BaseExecutor;

/**
 * 业务执行人配置接口
 * @author Administrator
 *
 */
public interface BusinessExecutorService {
	
	/**
	 * 根据业务code和流程节点Id获取配置信息
	 * @param businessCode
	 * @param flowId
	 * @return
	 */
	public List<BaseExecutor> getExecutors(String businessCode, String flowId);

	public BaseExecutor getExecutorById(String id);
	
	public int deleteExecutorById(String id);
	
	public List<BaseExecutor> save(List<BaseExecutor> baseExecutors);
	
	
}
