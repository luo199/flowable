package com.huasisoft.flow.business.action;

/**
 * 通用按钮接口
 * @author Administrator
 *
 */
public interface ICommAction {
	
	/**
	 * 业务实例ID
	 * @param businessInstanceId
	 * @return
	 */
	boolean execute(String businessInstanceId);
}
