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
@ApiModel("组织结构--岗位")
public class Position extends Unit{
	

	private static final long serialVersionUID = 1616545864020496622L;

	@Override
	public String getType() {
		return UnitType.group.getName();
	}

}
