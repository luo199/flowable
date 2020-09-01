package com.huasisoft.flow.platform.vo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("用户角色节点")
public class RoleNode implements Serializable{
	
	private static final long serialVersionUID = -8803399402847199595L;
	
	@ApiModelProperty("角色ID")
	private String id;
	
	@ApiModelProperty("角色名")
	private String name;
	
	@ApiModelProperty("节点类型")
	private String type;
	
	@ApiModelProperty("角色排序号")
	private Integer tabIndex;
	
	@ApiModelProperty("父节点ID")
	private Integer parentID;
	
}
