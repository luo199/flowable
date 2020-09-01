package com.huasisoft.flow.process.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author flq
 * @since 2020-07-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ACT_RE_PROCDEF")
@ApiModel(value="ActReProcdef对象", description="")
public class ActReProcdef extends Model<ActReProcdef> {

    private static final long serialVersionUID = 1L;

    @TableId("ID_")
    private String id;

    @TableField("REV_")
    private BigDecimal rev;

    @TableField("CATEGORY_")
    private String category;

    @TableField("NAME_")
    private String name;

    @TableField("KEY_")
    private String key;

    @TableField("VERSION_")
    private Integer version;

    @TableField("DEPLOYMENT_ID_")
    private String deploymentId;

    @TableField("RESOURCE_NAME_")
    private String resourceName;

    @TableField("DGRM_RESOURCE_NAME_")
    private String dgrmResourceName;

    @TableField("DESCRIPTION_")
    private String description;

    @TableField("HAS_START_FORM_KEY_")
    private Integer hasStartFormKey;

    @TableField("HAS_GRAPHICAL_NOTATION_")
    private Integer hasGraphicalNotation;

    @TableField("SUSPENSION_STATE_")
    private BigDecimal suspensionState;

    @TableField("TENANT_ID_")
    private String tenantId;

    @TableField("DERIVED_FROM_")
    private String derivedFrom;

    @TableField("DERIVED_FROM_ROOT_")
    private String derivedFromRoot;

    @TableField("DERIVED_VERSION_")
    private Integer derivedVersion;

    @TableField("ENGINE_VERSION_")
    private String engineVersion;
    
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @TableField(value = "DEPLOY_TIME",exist=false)
    private Date deployTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
