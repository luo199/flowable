package com.huasisoft.flow.platform.vo;

import io.swagger.annotations.ApiModel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
@ApiModel("组织结构--用户组")
public class Group extends Unit{
	

	private static final long serialVersionUID = 1616545864020496622L;

	@Override
	public String getType() {
		return UnitType.group.getName();
	}

}
