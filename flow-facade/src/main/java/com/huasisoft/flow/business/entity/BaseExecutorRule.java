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
 * 执行规则表
 * </p>
 *
 * @author flq
 * @since 2020-08-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("FLOW_BUSINESS_BASE_EXECUTOR_RULE")
@ApiModel(value="BaseExecutorRule对象", description="执行规则表")
public class BaseExecutorRule extends Model<BaseExecutorRule> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId("ID")
    private String id;

    @ApiModelProperty(value = "规则名")
    @TableField("NAME")
    private String name;

    @ApiModelProperty(value = "规则实现类名（不使用spring托管时使用）")
    @TableField("CLASS_NAME")
    private String className;

    @ApiModelProperty(value = "托管对象名（使用spring托管时使用）")
    @TableField("BEAN_NAME")
    private String beanName;

    @ApiModelProperty(value = "创建人")
    @TableField("CREATE_PERSON_NAME")
    private String createPersonName;

    @ApiModelProperty(value = "创建人ID")
    @TableField("CREATE_PERSON_ID")
    private String createPersonId;

    @ApiModelProperty(value = "创建时间")
    @TableField("CREATE_TIME")
    private Date createTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
