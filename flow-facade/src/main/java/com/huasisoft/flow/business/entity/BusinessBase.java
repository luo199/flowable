package com.huasisoft.flow.business.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 业务基本数据表
 * </p>
 *
 * @author yn
 * @since 2020-08-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("FLOW_BUSINESS_BASE")
@ApiModel(value="BusinessBase对象", description="业务基本数据表")
public class BusinessBase extends Model<BusinessBase> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableField("CODE")
    private String code;

    @ApiModelProperty(value = "业务名")
    @TableField("NAME")
    private String name;

    @ApiModelProperty(value = "业务描述")
    @TableField("REMARK")
    private String remark;

    @ApiModelProperty(value = "业务类别CODE")
    @TableField("CATA_CODE")
    private String cataCode;

    @ApiModelProperty(value = "创建人id")
    @TableField("CREATOR_ID")
    private String creatorId;

    @ApiModelProperty(value = "创建人姓名")
    @TableField("CREATOR_NAME")
    private String creatorName;

    @ApiModelProperty(value = "创建时间")
    @TableField("CREATR_TIME")
    private Date creatrTime;

    @ApiModelProperty(value = "状态")
    @TableField("STATUS")
    private String status;

    @ApiModelProperty(value = "流程id")
    @TableField("PROCESS_ID")
    private String processId;

    @TableField(value="CATA_NAME",exist=false)
    private String cataName;

    @TableField(value="PROCESS_NAME",exist=false)
    private String processName;

    @Override
    protected Serializable pkVal() {
        return null;
    }

}
