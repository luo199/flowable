package com.huasisoft.flow.business.service.impl;

import com.alibaba.druid.sql.ast.statement.SQLIfStatement;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huasisoft.flow.business.entity.ComAction;
import com.huasisoft.flow.business.mapper.ComActionMapper;
import com.huasisoft.flow.business.service.ComActionService;
import com.huasisoft.flow.common.util.IDCreator;
import com.huasisoft.flow.platform.service.impl.CurrentUserHelper;
import com.huasisoft.flow.platform.vo.Person;
import com.huasisoft.flow.process.vo.ModelPageRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;


@Service
public class ComActionServiceImpl implements ComActionService {

    @Autowired
    private ComActionMapper mapper;


    @Override
    public IPage<ComAction> page(ModelPageRequest page) {
        IPage<ComAction> iPage = new Page<>(page.getCurrent(), page.getSize());
        IPage<ComAction> result = mapper.selectPage(iPage,cataLogQueryWrapper(page.getName(),page.getCatalogCode()));
        return result;
    }
    private QueryWrapper<ComAction> cataLogQueryWrapper(String name,String catalogCode) {
        QueryWrapper<ComAction> queryWrapper = null;
        if (StringUtils.isBlank(catalogCode) && StringUtils.isBlank(name)) {
            queryWrapper = Wrappers.emptyWrapper();
        } else if (StringUtils.isNotBlank(catalogCode)){
            queryWrapper = new QueryWrapper<ComAction>().eq("CATALOG_CODE", catalogCode).like("NAME",name);
        }else {
            queryWrapper = new QueryWrapper<ComAction>().like("NAME",name);
        }
        return queryWrapper;
    }
    @Override
    public ComAction save(ComAction comAction) {
        Assert.notNull(comAction, "不能为空");
        if(StringUtils.isBlank(comAction.getId())) {
            comAction.setId(IDCreator.genId());
            Person person = CurrentUserHelper.currentUser();
            comAction.setCreatePersonName(person.getName());
            comAction.setCreatePersonId(person.getId());
            comAction.setCreateTime(new Date());
            mapper.insert(comAction);
        }else {
            mapper.updateById(comAction);
        }
        return comAction;
    }

    @Override
    public ComAction findOne(String id) {
        return mapper.selectById(id);
    }



    @Override
    public int delete(String id) {
        return mapper.deleteById(id);
    }

    @Override
    public List<ComAction> listAll() {
        return mapper.selectList(Wrappers.emptyWrapper());
    }
}