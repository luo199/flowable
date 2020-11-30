package com.huasisoft.flow.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.huasisoft.flow.business.entity.BaseExecutorDetail;
import com.huasisoft.flow.business.mapper.BaseExecutorDetailDmMapper;
import com.huasisoft.flow.business.service.BaseExecutorDetailService;
import com.huasisoft.flow.common.util.IDCreator;
import com.huasisoft.flow.platform.service.impl.CurrentUserHelper;
import com.huasisoft.flow.platform.vo.Person;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;


@Service
public class BaseExecutorDetailServiceImpl implements BaseExecutorDetailService {

    @Autowired
    BaseExecutorDetailDmMapper baseExecutorDetailDmMapper;

    @Override
    public List<BaseExecutorDetail> getExecutorDetails(String execGuid) {
        QueryWrapper<BaseExecutorDetail> queryWrapper = new QueryWrapper<BaseExecutorDetail>().eq("EXEC_GUID", execGuid);
        return baseExecutorDetailDmMapper.selectList(queryWrapper);
    }

    @Override
    public BaseExecutorDetail saveExecutorDetail(BaseExecutorDetail baseExecutorDetail) {
        Assert.notNull(baseExecutorDetail, "不能为空");
        if (StringUtils.isNotBlank(baseExecutorDetail.getId())) {
            baseExecutorDetail.setId(IDCreator.genId());
            Person person = CurrentUserHelper.currentUser();
            baseExecutorDetailDmMapper.insert(baseExecutorDetail);
        }else {
            baseExecutorDetailDmMapper.updateById(baseExecutorDetail);
        }

        return baseExecutorDetail;
    }
}
