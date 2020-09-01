package com.huasisoft.flow.test.service;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.huasisoft.flow.test.entity.TestUser;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author flq
 * @since 2020-07-23
 */
public interface TestUserService{

	public  TestUser getById(String id);
	public  List<TestUser> list();
	public  IPage<TestUser> page();
}
