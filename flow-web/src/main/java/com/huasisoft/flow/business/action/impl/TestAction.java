package com.huasisoft.flow.business.action.impl;

import com.huasisoft.flow.business.action.ICommAction;
/**
 * 测试按钮
 * @author Administrator
 *
 */
public class TestAction implements ICommAction{

	@Override
	public boolean execute(String businessInstanceId) {
		System.out.println("执行按钮功能");
		return false;
	}

}
