package com.huasisoft.flow.business.vo;

import java.util.List;

import com.huasisoft.flow.business.entity.BaseExecutor;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("流程连线配置")
public class SequeceManage extends FlowManage{
	
	private static final long serialVersionUID = -5971868562280038470L;

	/**
	 * 执行人员配置
	 */
	@ApiModelProperty(value = "执行人员配置")
	private List<BaseExecutor> executors;
	
	@ApiModelProperty(value = "重命名")
	private String rename;
	
	public String getFlowType() {
		return "SEQUECE";
	}
}
