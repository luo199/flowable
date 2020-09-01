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
 * 通用按钮配置
 * </p>
 *
 * @author flq
 * @since 2020-08-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("FLOW_BUSINESS_COM_ACTION")
@ApiModel(value="ComAction对象", description="通用按钮配置")
public class ComAction extends Model<ComAction> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId("ID")
    private String id;

    @ApiModelProperty(value = "按钮名")
    @TableField("NAME")
    private String name;

    @ApiModelProperty(value = "备注")
    @TableField("REMARK")
    private String remark;

    @ApiModelProperty(value = "按钮类全名")
    @TableField("CLASS_NAME")
    private String className;

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
