package com.huasisoft.flow.business.service;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.huasisoft.flow.business.entity.ComPage;
import com.huasisoft.flow.process.vo.ModelPageRequest;

/**
 * 通用页面接口
 * @author flq
 *
 */
public interface ComPageService {

	/**
	 * 按主键查询
	 * @param id
	 * @return
	 */
	ComPage findOne(String id);
	/**
	 * 分页列表
	 * @param page
	 * @return
	 */
	IPage<ComPage> page(ModelPageRequest page);
	/**
	 * 通用页面选择列表
	 * @param name
	 * @return
	 */
	List<ComPage> selectList(String name);
	/**
	 * 保存
	 * @param comPage
	 * @return
	 */
	ComPage save(ComPage comPage);
	/**
	 * 按主键删除
	 * @return
	 */
	int delete(String id);
}
