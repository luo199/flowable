package com.huasisoft.flow.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.huasisoft.flow.business.entity.BusinessCatalog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    List<BusinessCatalog> listByPCode(String pcode);

    int updateStatus(@Param("code") String code);

    int updateStatusWithoutMe(@Param("status") int status, @Param("code") String code);

    List<BusinessCatalog> selectListByChildCode(@Param("code") String code);
}
