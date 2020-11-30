package com.huasisoft.flow.process.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huasisoft.flow.process.entity.ActDeModel;
import com.huasisoft.flow.process.mapper.ActDeModelMapper;
import com.huasisoft.flow.process.service.ActDeModelService;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yn
 * @since 2020-07-29
 */
@Service
@Transactional(readOnly = true)
public class ActDeModelServiceImpl extends ServiceImpl<ActDeModelMapper, ActDeModel> implements ActDeModelService {
    @Autowired
    private ActDeModelMapper mapper;

    /**
     * 主键查询
     * @param id
     * @return
     */
    @Override
    public ActDeModel getById(String id) {
        return mapper.selectById(id);
    }

    /**
     * 分页查询
     * @param current
     * @param size
     * @param name
     * @return
     */
    @Override
    public IPage<ActDeModel> page(Integer current, Integer size,String name) {
        IPage<ActDeModel> userPage = new Page<>(current, size);//参数一是当前页，参数二是每页个数
        return mapper.maxVersionPage(userPage,name);
    }

    /**
     * 判断key是否存在
     * @param modelKey
     * @return
     */
    @Override
    public Boolean findByModelKey(String modelKey) {
        QueryWrapper<ActDeModel> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("MODEL_KEY").eq("MODEL_KEY", modelKey);
        List<ActDeModel> modelList = list(queryWrapper);
       return modelList!=null && modelList.size()>0;
    }

    /**
     * 根据模型key获取
     * @param modelKey
     * @return
     */
    @Override
    public ActDeModel getByModelKey(String modelKey) {
    	QueryWrapper<ActDeModel> queryWrapper = new QueryWrapper<>();
    	queryWrapper.eq("MODEL_KEY", modelKey);
    	List<ActDeModel> modelList = list(queryWrapper);
    	if(modelList!=null && modelList.size()>0) {
    		return modelList.get(0);
    	}else {
    		return null;
    	}
    }
}
