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
 * 业务视图配置表
 * </p>
 *
 * @author flq
 * @since 2020-08-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("FLOW_BUSINESS_BASE_VIEW")
@ApiModel(value="BaseView对象", description="业务视图配置表")
public class BaseView extends Model<BaseView> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId("ID")
    private String id;

    @ApiModelProperty(value = "业务CODE")
    @TableField("BIZ_CODE")
    private String bizCode;

    @ApiModelProperty(value = "视图类型（1.表单，2页面）")
    @TableField("VIEW_TYPE")
    private Integer viewType;

    @ApiModelProperty(value = "排序号")
    @TableField("ORDER_NUMBER")
    private Integer orderNumber;

    @ApiModelProperty(value = "任务节点ID")
    @TableField("TASK_ID")
    private String taskId;

    @ApiModelProperty(value = "视图名")
    @TableField("VIEW_NAME")
    private String viewName;

    @ApiModelProperty(value = "视图对象主键")
    @TableField("VIEW_OBJ_ID")
    private String viewObjId;

    @ApiModelProperty(value = "视图分组")
    @TableField("VIEW_GROUP")
    private Integer viewGroup;



    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
