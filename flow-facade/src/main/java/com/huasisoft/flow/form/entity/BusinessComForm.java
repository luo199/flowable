package com.huasisoft.flow.form.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.sql.Clob;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 表单定义表
 * </p>
 *
 * @author yn
 * @since 2020-09-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("FLOW_BUSINESS_COM_FORM")
@ApiModel(value="BusinessComForm对象", description="表单定义表")
public class BusinessComForm extends Model<BusinessComForm> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId("ID")
    private String id;

    @ApiModelProperty(value = "表单名")
    @TableField("NAME")
    private String name;

    @ApiModelProperty(value = "表单HTML内容")
    @TableField("CONTENT")
    private Byte[] content;

    @ApiModelProperty(value = "表单类型")
    @TableField("TYPE_CODE")
    private String typeCode;

    @ApiModelProperty(value = "表单类型名")
    @TableField("TYPE_NAME")
    private String typeName;

    @ApiModelProperty(value = "创建时间")
    @TableField("CREATE_TIME")
    private Date createTime;

    @ApiModelProperty(value = "创建人ID")
    @TableField("CREATE_PERSON_ID")
    private String createPersonId;

    @ApiModelProperty(value = "创建人")
    @TableField("CREATE_PERSON_NAME")
    private String createPersonName;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
