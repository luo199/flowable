package com.huasisoft.flow.business.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.huasisoft.flow.business.entity.BusinessBase;
import com.huasisoft.flow.business.vo.BusinessPageRequest;

/**
 * @Auther: yn
 * @Description:
 * @Date 2020/8/14
 */
public interface BusinessBaseService extends IService<BusinessBase> {

    IPage<BusinessBase> page(BusinessPageRequest page);

    BusinessBase createBusiness(BusinessBase businessBase);

    Boolean findByBusinessCode(String code);

    int updateStatus(String code);

    int deleteByCode(String code);

    BusinessBase findByCode(String code);

    int updateBusiness(BusinessBase businessBase);

    Boolean checkBusinessByCatacode(String cataCode);

}
