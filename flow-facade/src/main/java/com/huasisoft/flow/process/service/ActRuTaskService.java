package com.huasisoft.flow.process.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.huasisoft.flow.process.entity.ActRuTask;
import com.huasisoft.flow.process.vo.ManagePageRequest;

import java.util.List;

public interface ActRuTaskService {

    /**
     * 分页查询列表
     * @return
     */
    IPage<ActRuTask> page(ManagePageRequest page);

    /**
     * 列出所有在行的同流程任务
     * @return
     */
    List<ActRuTask> listAllTasks(String procDefId);
}
