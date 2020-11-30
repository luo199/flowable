package com.huasisoft.flow.process.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Auther: yn
 * @Description: 流程列表分页请求参数
 * @Date 2020/8/4
 */
@Data
@ApiModel
@EqualsAndHashCode(callSuper=true)
public class ModelPageRequest extends PageRequest {


    private static final long serialVersionUID = 1010904747029624635L;
    
    @ApiModelProperty("名字")
    private String name;
    @ApiModelProperty("字典Code")
    private String CatalogCode;
}
