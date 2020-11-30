package com.huasisoft.flow.form.vo.exception;

public class HtmlEleRepeatException extends Exception{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -5279719857524025839L;
	
	/* 错误码 */
	private int errerCode ;
	
	/* 重复的元素名称 */
	private String repeatEleName ;
	
	public int getErrerCode() {
		return errerCode;
	}
	public void setErrerCode(int errerCode) {
		this.errerCode = errerCode;
	}
	
	public String getRepeatEleName() {
		return repeatEleName;
	}
	public void setRepeatEleName(String repeatEleName) {
		this.repeatEleName = repeatEleName;
	}
	
	public HtmlEleRepeatException(int errerCode,String repeatEleName,String exceptionMessage){
		super(exceptionMessage);
		this.errerCode = errerCode;
		this.repeatEleName = repeatEleName;
	}
	
	public HtmlEleRepeatException(int errerCode,String repeatEleName,String exceptionMessage ,Throwable arg1){
		super(exceptionMessage , arg1);
		this.errerCode = errerCode;
		this.repeatEleName = repeatEleName;
	}
	
	public HtmlEleRepeatException(int errerCode,String repeatEleName,Throwable arg1){
		super(arg1);
		this.errerCode = errerCode;
		this.repeatEleName = repeatEleName;
	}
	
}
