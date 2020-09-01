package com.huasisoft.flow.business.vo;

import java.util.List;

import com.huasisoft.flow.business.entity.BaseAction;
import com.huasisoft.flow.business.entity.BaseExecutor;
import com.huasisoft.flow.business.entity.BaseTimeLimit;
import com.huasisoft.flow.business.entity.BaseView;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("任务节点配置")
public class TaskManage extends FlowManage{

	private static final long serialVersionUID = 5074730319764892399L;
	
	/**
	 * 视图配置
	 */
	@ApiModelProperty(value = "视图配置")
	private List<BaseView> views;
	/**
	 * 时限配置
	 */
	@ApiModelProperty(value = "时限配置")
	private BaseTimeLimit timeLimit;
	/**
	 * 执行人员配置
	 */
	@ApiModelProperty(value = "执行人员配置")
	private List<BaseExecutor> executors;
	/**
	 * 其他按钮配置
	 */
	@ApiModelProperty(value = "其他按钮配置")
	private List<BaseAction> actions;
	
	/**
	 * 流程按钮配置
	 */
	@ApiModelProperty(value = "流程按钮配置")
	private List<SequeceManage> seqieceManage;
	
	public String getFlowType() {
		return "TASK";
	}

}
