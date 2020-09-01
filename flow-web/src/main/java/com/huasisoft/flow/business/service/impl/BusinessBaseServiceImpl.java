package com.huasisoft.flow.business.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.flowable.idm.api.User;
import org.flowable.ui.common.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huasisoft.flow.business.entity.BusinessBase;
import com.huasisoft.flow.business.entity.BusinessCatalog;
import com.huasisoft.flow.business.mapper.BusinessBaseMapper;
import com.huasisoft.flow.business.mapper.BusinessCatalogMapper;
import com.huasisoft.flow.business.service.BusinessBaseService;
import com.huasisoft.flow.business.vo.BusinessPageRequest;
import com.huasisoft.flow.process.entity.ActReProcdef;
import com.huasisoft.flow.process.service.ActReProcdefService;

/**
 * @Auther: yn
 * @Description:
 * @Date 2020/8/14
 */
@Service
@Transactional(readOnly=true)
public class BusinessBaseServiceImpl extends ServiceImpl<BusinessBaseMapper, BusinessBase> implements BusinessBaseService {

    @Autowired
    private BusinessBaseMapper mapper;

    @Autowired
    private BusinessCatalogMapper catalogMapper;


    @Autowired
    private ActReProcdefService actReProcdefService;

    @Override
    public IPage<BusinessBase> page(BusinessPageRequest page) {
        IPage<BusinessBase> userPage = new Page<>(page.getCurrent(), page.getSize());//参数一是当前页，参数二是每页个数
        IPage<BusinessBase> businessBaseIPage = mapper.page(userPage, page.getCataCode(), page.getName());
        for (BusinessBase base : businessBaseIPage.getRecords()) {
            setcataNameAndProcessName(base);
        }
        return businessBaseIPage;
    }


    @Override
    @Transactional
    public BusinessBase createBusiness(BusinessBase businessBase) {

        //填入创建人信息
        User user = SecurityUtils.getCurrentUserObject();
        businessBase.setCreatrTime(new Date());
        businessBase.setCreatorId(user.getId());
        businessBase.setCreatorName(user.getDisplayName());
        //初始状态默认为 0
        businessBase.setStatus("0");
        boolean result = false;
        try {
            result = save(businessBase);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (result) {
            return businessBase;
        } else {
            return null;
        }
    }

    @Override
    public Boolean findByBusinessCode(String code) {
        QueryWrapper<BusinessBase> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("CODE").eq("CODE", code);
        List<BusinessBase> businessList = list(queryWrapper);
        return businessList != null && businessList.size() > 0;
    }

    @Override
    @Transactional
    public int updateStatus(String code) {
        UpdateWrapper<BusinessBase> businessBaseUpdateWrapper = new UpdateWrapper<>();
        businessBaseUpdateWrapper.set("STATUS", "1").eq("CODE", code);
        return mapper.update(null, businessBaseUpdateWrapper);
    }

    @Override
    @Transactional
    public int deleteByCode(String code) {
        LambdaQueryWrapper<BusinessBase> lambda = Wrappers.<BusinessBase>lambdaQuery();
        lambda.eq(BusinessBase::getCode, code);
        return mapper.delete(lambda);
    }

    @Override
    public BusinessBase findByCode(String code) {
        QueryWrapper<BusinessBase> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("CODE", code);
        BusinessBase base = getOne(queryWrapper);
        return setcataNameAndProcessName(base);
    }

    @Override
    @Transactional
    public int updateBusiness(BusinessBase businessBase) {
        UpdateWrapper<BusinessBase> businessBaseUpdateWrapper = new UpdateWrapper<>();
        businessBaseUpdateWrapper.set("NAME", businessBase.getName())
                                 .set("REMARK", businessBase.getRemark())
                                 .set("CATA_CODE", businessBase.getCataCode())
                                 .set("PROCESS_ID",businessBase.getProcessId() )
                                 .eq("CODE", businessBase.getCode());
        return mapper.update(null, businessBaseUpdateWrapper);
    }

    @Override
    public Boolean checkBusinessByCatacode(String cataCode) {
        QueryWrapper<BusinessBase> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("CODE").eq("CATA_CODE", cataCode);
        List<BusinessBase> businessList = list(queryWrapper);
        return businessList != null && businessList.size() > 0;
    }
    
    public BusinessBase setcataNameAndProcessName(BusinessBase base){
    	if(StringUtils.isNotBlank(base.getCataCode())) {
	        QueryWrapper<BusinessCatalog> catalogWrapper = new QueryWrapper<BusinessCatalog>();
	        catalogWrapper.eq("CODE", base.getCataCode());
	        BusinessCatalog businessCatalog = catalogMapper.selectList(catalogWrapper).get(0);
	        base.setCataName(businessCatalog.getName());
    	}
        if(StringUtils.isNotBlank(base.getProcessId())) {
        	ActReProcdef actReProcdef = actReProcdefService.findById(base.getProcessId());
            base.setProcessName(actReProcdef.getName());
        }
        return base;
    }
}
