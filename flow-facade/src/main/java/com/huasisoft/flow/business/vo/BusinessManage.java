package com.huasisoft.flow.business.vo;

import java.io.Serializable;
import java.util.List;

import com.huasisoft.flow.business.entity.BusinessBase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 流程配置信息
 * @author Administrator
 *
 */
@Data
@ApiModel(value="业务管理类")
public class BusinessManage implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3639812307939688457L;

	
	@ApiModelProperty(value = "业务基本信息")
	private BusinessBase base;
	
	@ApiModelProperty(value = "任务节点配置")
	private List<TaskManage> taskManages;
	
	@ApiModelProperty(value = "流程通用配置")
	private ProcManage procManage;
	
	
	
}

