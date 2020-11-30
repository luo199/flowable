package com.huasisoft.flow.form.vo.exception;

/**
 * 智能表单模块 异常状态码
 * 	错误码范围   101-300  
 *
 */
public enum SmartFormExcepCodeConst {
	
	/** 元素重复 */
	HTML_ELE_REPEAT_EXCEP("\u5143\u7d20\u91cd\u590d", 101) ;
/** ----------------------------------------------------- properties 属性字段 ------------------------------------------------------------- */
	
    // 成员变量  
    private String message;  
    private int state;  
    
/** -------------------------------------------- getters and setters 属性获取和设值方法  ------------------------------------------------- */
    
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}

	
/** -------------------------------------------- constructors 构造方法  ------------------------------------------------- */
	
	 // 构造方法  
    private SmartFormExcepCodeConst(String message, int state) {  
        this.message = message;  
        this.state = state;  
    }
    
/** -------------------------------------------- methods 成员方法  ------------------------------------------------- */
  
    /**
     * 根据状态码获取说明信息
     * @param state
     * @return
     */
    public static String getMessage(int state) {  
        for (SmartFormExcepCodeConst d : SmartFormExcepCodeConst.values()) {  
            if (d.getState() == state) {  
                return d.message;  
            }  
        }  
        return null;  
    } 
}
