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
 * 通用页面
 * </p>
 *
 * @author flq
 * @since 2020-08-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("FLOW_BUSINESS_COM_PAGE")
@ApiModel(value="ComPage对象", description="通用页面")
public class ComPage extends Model<ComPage> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId("ID")
    private String id;

    @ApiModelProperty(value = "页面名")
    @TableField("NAME")
    private String name;

    @ApiModelProperty(value = "页面地址")
    @TableField("PAGE_URL")
    private String pageUrl;

    @ApiModelProperty(value = "创建人ID")
    @TableField("CREATE_PERSON_ID")
    private String createPersonId;

    @ApiModelProperty(value = "创建人")
    @TableField("CREATE_PERSON_NAME")
    private String createPersonName;

    @ApiModelProperty(value = "创建时间")
    @TableField("CREATE_TIME")
    private Date createTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
