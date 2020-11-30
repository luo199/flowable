package com.huasisoft.flow.form.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.huasisoft.flow.form.entity.BusinessComForm;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huasisoft.flow.process.entity.ActReProcdef;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 表单定义表 Mapper 接口
 * </p>
 *
 * @author yn
 * @since 2020-09-17
 */
public interface BusinessComFormMapper extends BaseMapper<BusinessComForm> {

    IPage<BusinessComForm> page(IPage<BusinessComForm> page, @Param("name") String name);

}
