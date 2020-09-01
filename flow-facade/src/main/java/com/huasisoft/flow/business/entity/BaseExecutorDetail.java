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
 * 业务执行详情表
 * </p>
 *
 * @author flq
 * @since 2020-08-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("FLOW_BUSINESS_BASE_EXECUTOR_DETAIL")
@ApiModel(value="BaseExecutorDetail对象", description="业务执行详情表")
public class BaseExecutorDetail extends Model<BaseExecutorDetail> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId("ID")
    private String id;

    @ApiModelProperty(value = "业务执行表ID")
    @TableField("EXEC_GUID")
    private String execGuid;

    @ApiModelProperty(value = "执行对象类型（1，个人，2角色）")
    @TableField("TYPE")
    private String type;

    @ApiModelProperty(value = "执行对象主键")
    @TableField("EXECUTOR_ID")
    private String executorId;

    @ApiModelProperty(value = "执行对象名")
    @TableField("EXECUTOR_NAME")
    private String executorName;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
