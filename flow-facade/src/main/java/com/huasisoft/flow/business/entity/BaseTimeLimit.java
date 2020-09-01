package com.huasisoft.flow.business.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 业务时限表
 * </p>
 *
 * @author flq
 * @since 2020-08-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("FLOW_BUSINESS_BASE_TIME_LIMIT")
@ApiModel(value="BaseTimeLimit对象", description="业务时限表")
public class BaseTimeLimit extends Model<BaseTimeLimit> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId("ID")
    private String id;

    @ApiModelProperty(value = "任务ID")
    @TableField("TASK_ID")
    private String taskId;

    @ApiModelProperty(value = "时限")
    @TableField("TIME_LIMIT")
    private Integer timeLimit;

    @ApiModelProperty(value = "时限类型：1工作日，2自然日")
    @TableField("LIMIT_TYPE")
    private Integer limitType;

    @ApiModelProperty(value = "业务编码")
    @TableField("BIZ_CODE")
    private String bizCode;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
