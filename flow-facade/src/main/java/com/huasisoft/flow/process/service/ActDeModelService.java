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

    public ActDeModel getById(String id);


    public IPage<ActDeModel> page(Integer current, Integer size, String name);

    public Boolean findByModelKey(String modelKey);
    
    public ActDeModel getByModelKey(String modelKey);
}
