package com.huasisoft.flow.business.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * 业务分类表
 * </p>
 *
 * @author yn
 * @since 2020-08-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("FLOW_BUSINESS_CATALOG")
@ApiModel(value="BusinessCatalog对象", description="业务分类表")
public class BusinessCatalog extends Model<BusinessCatalog> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "类别key")
    @TableId("CODE")
    private String code;

    @ApiModelProperty(value = "类别名")
    @TableField("NAME")
    private String name;

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


    @Override
    protected Serializable pkVal() {
        return this.code;
    }

}
