package com.huasisoft.flow.process.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.huasisoft.flow.process.entity.ActDeModel;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author yn
 * @since 2020-07-29
 */
public interface ActDeModelService extends IService<ActDeModel> {

    /**
     * 主键查询
     * @param id
     * @return
     */
    public ActDeModel getById(String id);


    /**
     * 分页查询列表
     * @param current
     * @param size
     * @param name
     * @return
     */
    public IPage<ActDeModel> page(Integer current, Integer size, String name);

    /**
     * 判断key是否存在
     * @param modelKey
     * @return
     */
    public Boolean findByModelKey(String modelKey);

    /**
     *  根据模型key查询
     * @param modelKey
     * @return
     */
    public ActDeModel getByModelKey(String modelKey);
}
