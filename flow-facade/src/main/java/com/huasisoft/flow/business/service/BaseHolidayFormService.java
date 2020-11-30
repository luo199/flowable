package com.huasisoft.flow.business.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.huasisoft.flow.business.entity.BaseExecutor;
import com.huasisoft.flow.business.entity.BaseHolidayForm;
import com.huasisoft.flow.process.vo.ModelPageRequest;

import java.util.HashMap;
import java.util.List;

public interface BaseHolidayFormService {
    IPage<BaseHolidayForm> page(ModelPageRequest page);

    List<BaseHolidayForm>  getHolidayForms(String bizCode,String status);

    IPage<BaseHolidayForm> approvePage(ModelPageRequest page);

    BaseHolidayForm save(BaseHolidayForm baseHolidayForm);

    BaseHolidayForm findOne(String id);

    List<BaseExecutor> getNextExecutor(String bizCode, String id);

    int delete(String id);

}
