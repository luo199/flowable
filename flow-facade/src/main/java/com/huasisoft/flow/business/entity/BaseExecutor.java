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
 * 业务执行人员配置
 * </p>
 *
 * @author flq
 * @since 2020-08-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("FLOW_BUSINESS_BASE_EXECUTOR")
@ApiModel(value="BaseExecutor对象", description="业务执行人员配置")
public class BaseExecutor extends Model<BaseExecutor> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId("ID")
    private String id;

    @ApiModelProperty(value = "业务主键")
    @TableField("BIZ_CODE")
    private String bizCode;

    @ApiModelProperty(value = "任务节点ID")
    @TableField("TASK_ID")
    private String taskId;
    
    @ApiModelProperty(value = "执行对象类型（1.机构节点，2角色,3.规则）")
    @TableField("TYPE")
    private String type;

    @ApiModelProperty(value = "执行对象主键")
    @TableField("EXECUTOR_ID")
    private String executorId;

    @ApiModelProperty(value = "执行对象名")
    @TableField("EXECUTOR_NAME")
    private String executorName;

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
