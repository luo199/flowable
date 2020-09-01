package com.huasisoft.flow.process.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author yn
 * @since 2020-07-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ACT_DE_MODEL")
@ApiModel(value="ActDeModel对象", description="")
public class ActDeModel extends Model<ActDeModel> {

    private static final long serialVersionUID = 1L;

    @TableId("ID")
    private String id;

    @TableField("NAME")
    private String name;

    @TableField("MODEL_KEY")
    private String modelKey;

    @TableField("DESCRIPTION")
    private String description;

    @TableField("MODEL_COMMENT")
    private String modelComment;

    @TableField("CREATED")
    private Date created;

    @TableField("CREATED_BY")
    private String createdBy;

    @TableField("LAST_UPDATED")
    private Date lastUpdated;

    @TableField("LAST_UPDATED_BY")
    private String lastUpdatedBy;

    @TableField("VERSION")
    private Integer version;

  /*  @TableField("MODEL_EDITOR_JSON")
    private String modelEditorJson;

    @TableField("THUMBNAIL")
    private Byte[] thumbnail;*/

    @TableField("MODEL_TYPE")
    private Integer modelType;

    @TableField("TENANT_ID")
    private String tenantId;

    /**
     * 部署的最高版本
     */
    @TableField(value="MAX_VERSION",exist=false)
    private Integer maxVersion;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
