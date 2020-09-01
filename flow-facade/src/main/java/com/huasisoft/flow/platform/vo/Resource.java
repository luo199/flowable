package com.huasisoft.flow.platform.vo;


import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("资源")
public class Resource implements Serializable{
	
	private static final long serialVersionUID = -4120713420002980472L;
	@ApiModelProperty("资源ID")
	private String id;
	
	@ApiModelProperty("资源名")
	private String name;
	
	@ApiModelProperty("资源别名")
	private String aliasName;
	
	@ApiModelProperty("资源图标")
	private String resourceIcon;
	
	@ApiModelProperty("资源描述")
	private String description;
	
	@ApiModelProperty("资源url")
	private String resourceUrl;
	
	@ApiModelProperty("资源排序号")
	private Integer tabIndex;
	
	@ApiModelProperty("子资源")
	private List<Resource> childs;
	
	
}
