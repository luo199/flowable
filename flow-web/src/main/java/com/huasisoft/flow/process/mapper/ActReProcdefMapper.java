package com.huasisoft.flow.process.mapper;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.huasisoft.flow.process.entity.ActReProcdef;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author flq
 * @since 2020-07-31
 */
public interface ActReProcdefMapper extends BaseMapper<ActReProcdef> {

	
	IPage<ActReProcdef> maxVersionPage(IPage<ActReProcdef> page,@Param("name") String name);

	List<ActReProcdef> listActivateProcess();

}
