package com.huasisoft.flow.business.service;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.huasisoft.flow.business.entity.BusinessCatalog;
import com.huasisoft.flow.process.vo.PageRequest;

/**
 * @Auther: yn
 * @Description:
 * @Date 2020/8/14
 */
public interface BusinessCatalogService extends IService<BusinessCatalog> {

  BusinessCatalog  createBusinessCatalog(BusinessCatalog businessCatalog);

  List<BusinessCatalog> listAll();

  IPage<BusinessCatalog> page(PageRequest page);

    int updateStatus(String code);

    int deleteByCode(String code);

    boolean findByCataLogCode(String code);

  BusinessCatalog findByCode(String code);

  int updateCataLog(BusinessCatalog businessCatalog);


}
