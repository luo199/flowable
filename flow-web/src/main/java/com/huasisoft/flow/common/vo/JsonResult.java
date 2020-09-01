package com.huasisoft.flow.common.vo;


import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 统一返回类
 * 封装统一的json格式
 *
 */
@ApiModel("返回数据")
public class JsonResult<T> implements Serializable{
	
/** --------------------------------------------------- serialVersionUID 序列化ID ------------------------------------------------------- */

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -8137690211162668102L;

/** ----------------------------------------------------- static final properties 静态变量 ------------------------------------------------------------- */
	
	private static final Integer success = 1 ;
	private static final Integer fail = 0 ;
	
/** ----------------------------------------------------- properties 属性字段 ------------------------------------------------------------- */	
	
	@ApiModelProperty(value="返回状态",notes="1、成功 0、失败")
	private Integer exchangeStatus = success ;
	@ApiModelProperty(value="错误码")
	private Integer errorStatus ;
	@ApiModelProperty(value="错误信息",notes="错误时返回的信息")
	private String message = "success";
	@ApiModelProperty(value="返回的数据")
	private T data ;
	
/** -------------------------------------------- getters and setters 属性获取和设值方法  ------------------------------------------------- */
	
	
	public String getMessage() {
		return message;
	}
	
	public static Integer getSuccess() {
		return success;
	}

	public static Integer getFail() {
		return fail;
	}

	public Integer getExchangeStatus() {
		return exchangeStatus;
	}

	public void setExchangeStatus(Integer exchangeStatus) {
		this.exchangeStatus = exchangeStatus;
	}

	public void setErrorStatus(Integer errorStatus) {
		this.errorStatus = errorStatus;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setData(T data) {
		this.data = data;
	}

	public T getData() {
		return data;
	}
	public Integer getErrorStatus() {
		return errorStatus;
	}

/** -------------------------------------------- constructors 构造方法  ------------------------------------------------- */
	//成功时的构造方法
	public JsonResult(T data) {
		super();
		this.exchangeStatus = success;
		this.data = data;
	}
	
	//失败时的构造方法
	public JsonResult(String errorMessage,Integer errorStatus) {
		super();
		this.message = errorMessage;
		this.exchangeStatus = fail;
		this.errorStatus = errorStatus;
	}
	/*public JsonResult(BusExcepCodeConst excepCode) {
		super();
		this.message = excepCode.getMessage();
		this.code = fail;
		this.errorStatus = excepCode.getState();
	}*/
	
}