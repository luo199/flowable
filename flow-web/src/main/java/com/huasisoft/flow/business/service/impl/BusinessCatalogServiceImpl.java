package com.huasisoft.flow.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huasisoft.flow.business.entity.BusinessBase;
import com.huasisoft.flow.business.entity.BusinessCatalog;
import com.huasisoft.flow.business.mapper.BusinessCatalogMapper;
import com.huasisoft.flow.business.service.BusinessCatalogService;
import com.huasisoft.flow.process.vo.PageRequest;
import org.flowable.idm.api.User;
import org.flowable.ui.common.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Auther: yn
 * @Description:
 * @Date 2020/8/14
 */
@Service
public class BusinessCatalogServiceImpl extends ServiceImpl<BusinessCatalogMapper, BusinessCatalog> implements BusinessCatalogService {

    @Autowired
    private BusinessCatalogMapper mapper;

    /**
     * 新增业务类别
     * @param businessCatalog
     * @return
     */
    @Override
    public BusinessCatalog createBusinessCatalog(BusinessCatalog businessCatalog) {
        //填入创建人信息
        User user = SecurityUtils.getCurrentUserObject();
        businessCatalog.setCreatrTime(new Date());
        businessCatalog.setCreatorId(user.getId());
        businessCatalog.setCreatorName(user.getDisplayName());
        //初始状态默认为 0
        businessCatalog.setStatus("0");
        boolean result = false;
        try {
            result = save(businessCatalog);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (result){
            return businessCatalog;
        }else {
            return null;
        }
    }

    @Override
    public List<BusinessCatalog> listAll() {
        QueryWrapper<BusinessCatalog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("STATUS","0" );
        return list(queryWrapper);
    }

    @Override
    public IPage<BusinessCatalog> page(PageRequest page) {
        IPage<BusinessCatalog> userPage = new Page<>(page.getCurrent(), page.getSize());//参数一是当前页，参数二是每页个数
        return mapper.page(userPage);
    }

    @Override
    public int updateStatus(String code) {
        UpdateWrapper<BusinessCatalog> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("STATUS", "1" ).eq("CODE",code );
        return mapper.update(null, updateWrapper);
    }

    @Override
    public int deleteByCode(String code) {
        LambdaQueryWrapper<BusinessCatalog> lambda = Wrappers.<BusinessCatalog>lambdaQuery();
        lambda.eq(BusinessCatalog::getCode, code);
        return  mapper.delete(lambda);
    }

    @Override
    public boolean findByCataLogCode(String code) {
        QueryWrapper<BusinessCatalog> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("CODE").eq("CODE", code);
        List<BusinessCatalog> businessList = list(queryWrapper);
        return businessList != null && businessList.size() > 0;
    }

    @Override
    public BusinessCatalog findByCode(String code) {
        QueryWrapper<BusinessCatalog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("CODE", code);
        return getOne(queryWrapper);
    }

    @Override
    public int updateCataLog(BusinessCatalog businessCatalog) {
        UpdateWrapper<BusinessCatalog> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("NAME", businessCatalog.getName()).eq("CODE", businessCatalog.getCode());
        return mapper.update(null, updateWrapper);
    }
}
