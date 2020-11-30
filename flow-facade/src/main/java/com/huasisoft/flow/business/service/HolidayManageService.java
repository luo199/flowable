package com.huasisoft.flow.business.service;

import com.huasisoft.flow.business.vo.HolidayManage;

import java.util.List;

public interface HolidayManageService {
    List<HolidayManage> getHolidayManages(String businessCode,String status);

    void startHolidayProcess(String id,String executorId);

    void holidayApproveProcess(String id,String executorId);

    void holidayFinish(String id);
    void holidayReject(String id);
}
