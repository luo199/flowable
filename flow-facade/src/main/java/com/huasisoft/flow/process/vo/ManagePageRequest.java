package com.huasisoft.flow.process.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
@ApiModel(value="流程管理分页请求参数", description="")
public class ManagePageRequest extends PageRequest {

	private static final long serialVersionUID = 6172060093775095048L;
	
	@ApiModelProperty("流程名")
	private String name;

}
 