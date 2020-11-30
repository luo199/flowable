package com.huasisoft.flow.form.vo;

import com.huasisoft.flow.process.vo.PageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Auther: yn
 * @Description:
 * @Date 2020/9/17
 */
@Data
@ApiModel
@EqualsAndHashCode(callSuper=true)
public class FormPageRequest extends PageRequest {
    private static final long serialVersionUID = -4275440201838979136L;


    @ApiModelProperty("表单名")
    private String formName;
}
