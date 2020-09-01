package com.huasisoft.flow.process.vo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="通用分页请求参数", description="")
public class PageRequest implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = -7864854465425568279L;
	
	@ApiModelProperty("每页行数")
	private Integer size=10;
	
	@ApiModelProperty("当前页数")
	private Integer current;

	public Integer getSize() {
		return size==null?10:size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Integer getCurrent() {
		return current==null?1:current;
	}

	public void setCurrent(Integer current) {
		this.current = current;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
