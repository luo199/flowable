package com.huasisoft.flow.process.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.huasisoft.flow.process.entity.ActDeModel;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yn
 * @since 2020-07-29
 */
public interface ActDeModelMapper extends BaseMapper<ActDeModel> {

    IPage<ActDeModel>  maxVersionPage(IPage<ActDeModel> page, @Param("name") String name);
}
