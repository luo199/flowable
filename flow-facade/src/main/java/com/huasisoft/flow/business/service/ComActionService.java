package com.huasisoft.flow.business.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.huasisoft.flow.business.entity.ComAction;
import com.huasisoft.flow.process.vo.ModelPageRequest;

import java.util.List;

/**
 * 通用按钮接口
 */
public interface ComActionService {
    /**
     * 分页列表
     * @param page
     * @return
     */
    IPage<ComAction> page(ModelPageRequest page);

    /**
     * 保存
     * @param comAction
     * @return
     */
    ComAction save(ComAction comAction);

    /**
     * 按主键查询
     * @param id
     * @return
     */
    ComAction findOne(String id);

    /**
     * 按主键删除
     * @param id
     * @return
     */
    int delete(String id);

    /**
     * 列出所有按钮
     * @return
     */
    List<ComAction> listAll();
}
