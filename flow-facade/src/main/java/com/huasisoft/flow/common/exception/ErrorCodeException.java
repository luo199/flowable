package com.huasisoft.flow.common.exception;

public class ErrorCodeException extends RuntimeException{


    private static final long serialVersionUID = 3391302598056801742L;

    /* 错误码 */
    private int errerCode ;
    
    /* 异常信息 */
    private String errerMessage ;

	public int getErrerCode() {
		return errerCode;
	}
	public void setErrerCode(int errerCode) {
		this.errerCode = errerCode;
	}

	public String getErrerMessage() {
		return errerMessage;
	}
	public void setErrerMessage(String errerMessage) {
		this.errerMessage = errerMessage;
	}
	
	public ErrorCodeException(int errerCode) {
		super();
		this.errerCode = errerCode;
	}
	
	public ErrorCodeException(int errerCode, String errerMessage) {
		super();
		this.errerCode = errerCode;
		this.errerMessage = errerMessage;
	}

   

    

}
