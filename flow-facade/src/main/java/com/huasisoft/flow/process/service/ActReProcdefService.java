package com.huasisoft.flow.process.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.huasisoft.flow.process.entity.ActReProcdef;
import com.huasisoft.flow.process.vo.ManagePageRequest;

import java.util.List;

public interface ActReProcdefService {
	
	
	IPage<ActReProcdef> procPage(ManagePageRequest page);

	List<ActReProcdef> listActivateProcess();
	
	public ActReProcdef findById(String id);
	
	public List<ActReProcdef> findByKey(String key);
}
