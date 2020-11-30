package com.huasisoft.flow.platform.vo;


import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("组织结构机构基类")
public class Unit implements Serializable{
	
	private static final long serialVersionUID = -4120713420002980472L;
	@ApiModelProperty("节点ID")
	private String id;
	
	@ApiModelProperty("节点名")
	private String name;
	
	@ApiModelProperty("节点类型")
	private String type;
	
	@ApiModelProperty("节点排序号")
	private Integer tabIndex;
	
	@ApiModelProperty("父节点ID")
	private String parentID;
	
	
	
	
}
