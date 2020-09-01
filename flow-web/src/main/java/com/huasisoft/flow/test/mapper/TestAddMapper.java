package com.huasisoft.flow.test.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.huasisoft.flow.test.entity.TestAdd;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lb
 * @since 2020-08-21
 */
public interface TestAddMapper extends BaseMapper<TestAdd> {
    IPage<TestAdd> searchByName(IPage<TestAdd> page, @Param("name")String name);

}
