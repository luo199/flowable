package com.huasisoft.flow.business.vo;

import com.huasisoft.flow.process.vo.PageRequest;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Auther: yn
 * @Description:
 * @Date 2020/8/14
 */
@Data
@ApiModel(value="业务定义列表参数")
public class BusinessPageRequest extends PageRequest {
    private static final long serialVersionUID = 634022509647761828L;
    /**
     * 业务名称
     */
    @ApiModelProperty(value = "业务名称")
    private String name;

    /**
     * 业务类别
     */
    @ApiModelProperty(value = "业务类别CODE")
    private String cataCode;
    @ApiModelProperty(value = "主键业务类别名")
    private String cataName;
}
