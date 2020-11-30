package com.huasisoft.flow.business.service;

import com.huasisoft.flow.business.entity.BaseExecutorDetail;

import java.util.List;

public interface BaseExecutorDetailService {

    List<BaseExecutorDetail> getExecutorDetails(String execGuid);
    BaseExecutorDetail saveExecutorDetail(BaseExecutorDetail baseExecutorDetail);
}
