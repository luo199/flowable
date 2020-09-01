package com.huasisoft.flow.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.huasisoft.flow.business.entity.BusinessBase;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 业务基本数据表 Mapper 接口
 * </p>
 *
 * @author yn
 * @since 2020-08-17
 */
public interface BusinessBaseMapper extends BaseMapper<BusinessBase> {

    IPage<BusinessBase> page(IPage<BusinessBase> page, @Param("cataCode") String cataCode , @Param("name") String name);
}
