package com.huasisoft.flow.business.vo;

import com.huasisoft.flow.business.entity.BaseExecutor;
import com.huasisoft.flow.business.entity.BaseExecutorDetail;
import com.huasisoft.flow.business.entity.BaseHolidayForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


@Data
@ApiModel(value = "流程管理类")
public class HolidayManage {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "请假表基本信息")
    private BaseHolidayForm baseHolidayForm;

    @ApiModelProperty(value = "执行人基本信息")
    private List<BaseExecutor> baseExecutors;

    @ApiModelProperty(value = "执行具体信息")
    private List<BaseExecutorDetail> baseExecutorDetails;

}
