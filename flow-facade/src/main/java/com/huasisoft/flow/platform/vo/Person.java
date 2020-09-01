package com.huasisoft.flow.platform.vo;

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
@ApiModel("组织结构--人员")
public class Person extends Unit{
	
	private static final long serialVersionUID = -5574491414574931696L;
	
	@ApiModelProperty("登录名")
	private String loginName;
	
	@ApiModelProperty("联系电话")
	private String mobile;
	
	@Override
	public String getType() {
		return UnitType.person.getName();
	}

}
