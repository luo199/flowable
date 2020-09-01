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
 * 业务视图按钮配置
 * </p>
 *
 * @author flq
 * @since 2020-08-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("FLOW_BUSINESS_BASE_ACTION")
@ApiModel(value="BaseAction对象", description="业务视图按钮配置")
public class BaseAction extends Model<BaseAction> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId("ID")
    private String id;

    @ApiModelProperty(value = "业务CODE")
    @TableField("BIZ_CODE")
    private String bizCode;

    @ApiModelProperty(value = "关联按钮ID")
    @TableField("ACTION_ID")
    private String actionId;

    @ApiModelProperty(value = "按钮名")
    @TableField("ACTION_NAME")
    private String actionName;

    @ApiModelProperty(value = "排序号")
    @TableField("ORDER_NUMBER")
    private Integer orderNumber;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
