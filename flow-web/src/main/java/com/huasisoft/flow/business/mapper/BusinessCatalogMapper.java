package com.huasisoft.flow.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.huasisoft.flow.business.entity.BusinessCatalog;

/**
 * <p>
 * 业务分类表 Mapper 接口
 * </p>
 *
 * @author yn
 * @since 2020-08-17
 */
public interface BusinessCatalogMapper extends BaseMapper<BusinessCatalog> {

    IPage<BusinessCatalog> page(IPage<BusinessCatalog> page);
}
