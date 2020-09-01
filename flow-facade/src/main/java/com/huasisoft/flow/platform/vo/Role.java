package com.huasisoft.flow.platform.vo;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
@ApiModel("用户角色")
public class Role  extends RoleNode{
	
	private static final long serialVersionUID = -3683398251403390541L;

	
	@ApiModelProperty("角色成员")
	private List<Role> units;
	
	@Override
	public String getType() {
		return RoleNodeType.role.getName();
	}
	
}
