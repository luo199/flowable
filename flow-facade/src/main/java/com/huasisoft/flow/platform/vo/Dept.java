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
@ApiModel("组织结构--部门")
public class Dept extends Unit{
	
	private static final long serialVersionUID = -6722280389673370492L;

	@Override
	public String getType() {
		return UnitType.dept.getName();
	}

}
