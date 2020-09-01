package com.huasisoft.flow.platform.service;

import java.util.List;

import com.huasisoft.flow.platform.vo.Resource;

/**
 * 资源接口
 * @author Administrator
 *
 */
public interface ResourceService {
	/**
	 * 获取左侧资源菜单
	 * @param currentUserId
	 * @return
	 */
	List<Resource> leftMenu(String currentUserId);
}
