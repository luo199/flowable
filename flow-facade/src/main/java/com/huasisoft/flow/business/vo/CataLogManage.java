package com.huasisoft.flow.business.vo;

import com.huasisoft.flow.business.entity.BusinessCatalog;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: yn
 * @Description:
 * @Date 2020/9/10
 */
@Data
@ApiModel("分类节点参数")
public class CataLogManage implements Serializable {

    private static final long serialVersionUID = -1633954603696176763L;

    @ApiModelProperty("父节点编码")
    private String code;
    @ApiModelProperty("子节点集合")
    private List<BusinessCatalog> catalogList;

}
