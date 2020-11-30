package com.huasisoft.flow.business.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 请休假基本信息表
 * </p>
 *
 * @author Luob
 * @since 2020-10-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("FLOW_BUSINESS_BASE_HOLIDAY_FORM")
@ApiModel(value="BaseHolidayForm对象", description="请休假基本信息表")
public class BaseHolidayForm extends Model<BaseHolidayForm> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "信息表ID")
    @TableId("ID")
    private String id;

    @ApiModelProperty(value = "姓名")
    @TableField("NAME")
    private String name;

    @ApiModelProperty(value = "职务")
    @TableField("POSITION")
    private String position;

    @ApiModelProperty(value = "单位")
    @TableField("DEPARTMENT")
    private String department;

    @ApiModelProperty(value = "联系电话")
    @TableField("PHONE_NUMBER")
    private String phoneNumber;

    @ApiModelProperty(value = "请假类型")
    @TableField("HOLIDAY_TYPE")
    private String holidayType;

    @ApiModelProperty(value = "补办手续")
    @TableField("RE_PROCEDURE")
    private String reProcedure;

    @ApiModelProperty(value = "工龄")
    @TableField("WORK_LENGTH")
    private Date workLength;

    @ApiModelProperty(value = "是否离深")
    @TableField("LEAVE_SHENZHEN")
    private String leaveShenzhen;

    @ApiModelProperty(value = "外出地点")
    @TableField("GO_WHERE")
    private String goWhere;

    @ApiModelProperty(value = "请假起始日期")
    @TableField("START_HOLIDAY_TIME")
    private Date startHolidayTime;

    @ApiModelProperty(value = "请假截至日期")
    @TableField("END_HOLIDAY_TIME")
    private Date endHolidayTime;

    @ApiModelProperty(value = "天数")
    @TableField("HOLIDAY_TIME")
    private Integer holidayTime;

    @ApiModelProperty(value = "应休天数")
    @TableField("SHOULD_HOLIDAY_TIME")
    private Integer shouldHolidayTime;

    @ApiModelProperty(value = "已休天数")
    @TableField("HAS_HOLIDAY_TIME")
    private Integer hasHolidayTime;

    @ApiModelProperty(value = "销假申请人")
    @TableField("OFF_HOLIDAY_NAME")
    private String offHolidayName;

    @ApiModelProperty(value = "销假申请日期")
    @TableField("OFF_HOLIDAY_TIME")
    private Date offHolidayTime;

    @ApiModelProperty(value = "实际请假天数")
    @TableField("ACTUAL_HOLIDAY_TIME")
    private String actualHolidayTime;

    @ApiModelProperty(value = "销假备案人")
    @TableField("OFF_HOLIDAY_RECORD")
    private String offHolidayRecord;

    @ApiModelProperty(value = "备注")
    @TableField("REMARK")
    private String remark;

    @ApiModelProperty(value = "表单状态")
    @TableField("FORM_STATUS")
    private String formStatus;

    @ApiModelProperty(value = "执行人Id")
    @TableField("EXECUTOR_ONE_ID")
    private String executorOneId;

    @ApiModelProperty(value = "执行人2ID")
    @TableField("EXECUTOR_TWO_ID")
    private String executorTwoId;

    @ApiModelProperty(value = "业务ID")
    @TableField("BIZ_CODE")
    private String bizCode;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
