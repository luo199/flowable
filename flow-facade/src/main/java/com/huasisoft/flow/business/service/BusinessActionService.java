package com.huasisoft.flow.business.service;

import java.util.List;

import com.huasisoft.flow.business.entity.BaseAction;
/**
 * 业务按钮配置接口
 * @author Administrator
 *
 */
public interface BusinessActionService {
	/**
	 * 保存业务配置按钮
	 * @param baseAction
	 * @return
	 */
	BaseAction saveAction(BaseAction baseAction) ;

	/**
	 * 根据主键id删除业务配置按钮
	 * @param id
	 * @return
	 */
	int delete(String id) ;

	/**
	 * 根据主键id查找业务配置按钮
	 * @param id
	 * @return
	 */
	BaseAction findOne(String id) ;

	/**
	 * 根据业务代码和按钮id删除任务中的按钮
	 * @param bizCode
	 * @param actionId
	 * @param  id
	 * @return
	 */
	int deleteByBizCodeAndActionId(String bizCode,String actionId,String id);

	/**
	 * 根据业务code和流程节点ID获取数据
	 * @param businessCode 业务CODE
	 * @param flowId 流程节点ID
	 * @return
	 */
	List<BaseAction> getActions(String businessCode,String flowId);
}
