package com.huasisoft.flow.process.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huasisoft.flow.business.entity.ComAction;
import com.huasisoft.flow.process.entity.ActRuTask;
import com.huasisoft.flow.process.mapper.ActRuTaskMapper;
import com.huasisoft.flow.process.service.ActReProcdefService;
import com.huasisoft.flow.process.service.ActRuTaskService;
import com.huasisoft.flow.process.vo.ManagePageRequest;
import com.huasisoft.flow.process.vo.PageRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class ActRuTaskServiceImpl implements ActRuTaskService {

    @Autowired
    private ActRuTaskMapper actRuTaskMapper;

    @Override
    public IPage<ActRuTask> page(ManagePageRequest pageRequest) {
        IPage<ActRuTask> iPage = new Page<>(pageRequest.getCurrent(),pageRequest.getSize());
        return null;
        //        return actRuTaskMapper.selectPage(iPage,cataLogQueryWrapper(iPage.getName(),iPage.getCatalogCode()));
    }
    private QueryWrapper<ActRuTask> cataLogQueryWrapper(String name, String catalogCode) {
        QueryWrapper<ActRuTask> queryWrapper = null;
        if (StringUtils.isBlank(catalogCode) && StringUtils.isBlank(name)) {
            queryWrapper = Wrappers.emptyWrapper();
        } else if (StringUtils.isNotBlank(catalogCode)){
            queryWrapper = new QueryWrapper<ActRuTask>().eq("CATALOG_CODE", catalogCode).like("NAME",name);
        }else {
            queryWrapper = new QueryWrapper<ActRuTask>().like("NAME",name);
        }
        return queryWrapper;
    }

    @Override
    public List<ActRuTask> listAllTasks(String procDefId) {
        QueryWrapper<ActRuTask> actRuTaskQueryWrapper = new QueryWrapper<>();
        actRuTaskQueryWrapper.eq("PROC_DEF_ID_",procDefId);
        return actRuTaskMapper.selectList(actRuTaskQueryWrapper);
    }
}
