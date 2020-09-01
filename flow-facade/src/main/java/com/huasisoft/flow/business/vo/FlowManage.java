package com.huasisoft.flow.business.vo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("流程节点配置配置")
public class FlowManage implements Serializable{

	private static final long serialVersionUID = 4249845733175390771L;

	@ApiModelProperty("流程节点ID")
	private String id;
	@ApiModelProperty("流程节点名")
	private String name;

}
