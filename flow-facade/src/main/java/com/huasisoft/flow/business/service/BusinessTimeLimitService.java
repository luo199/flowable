package com.huasisoft.flow.business.service;

import com.huasisoft.flow.business.entity.BaseTimeLimit;

/**
 * 业务实现配置接口
 * @author Administrator
 *
 */
public interface BusinessTimeLimitService {
	
	/**
	 * 根据业务编码和流程节点Id获取时限
	 * @param businessCode
	 * @param flowId
	 * @return
	 */
	BaseTimeLimit getTimeLimit(String businessCode,String flowId);

	/**
	 * 保存时限配置
	 * @param baseTimeLimit
	 * @return
	 */
	BaseTimeLimit saveTimiLimit(BaseTimeLimit baseTimeLimit);

	/**
	 * 删除时限配置
	 * @param taskId
	 * @return
	 */
	int deleteByTaskId(String taskId);

}
