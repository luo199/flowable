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
@ApiModel("组织结构--机构")
public class Org extends Unit{
	
	private static final long serialVersionUID = -2630991053823448571L;

	@Override
	public String getType() {
		return UnitType.org.getName();
	}

}
