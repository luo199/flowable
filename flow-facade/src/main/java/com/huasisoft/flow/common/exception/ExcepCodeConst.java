package com.huasisoft.flow.common.exception;

/**
 * 统一的异常状态码
 * 系统级别：用1-100表示
 * 		     1:代码运行出错 
 * 模块业务异常: 从101 开始，每个模块分配一个段，每段长度为200 
 * 			例如：smartform模块   101-300 (各模块的特殊错误异常码，可在模块包内另外定义一个类)
 *              transformation 301-500 
 *              officialdoc 501-700
 *              attachment  701-900
 *              wordtemplate 901-1100
 *              comment  1101-1300
 *     			extcloud模块   1301-1500 (各模块的特殊错误异常码，可在模块包内另外定义一个类)
 *
 */
public enum ExcepCodeConst {
	
	/** 后台运行出错 */
	EXCHANGE_SYS_EXCEP("\u540e\u53f0\u8fd0\u884c\u51fa\u9519", 1),
	/** 数据异常 */
	OPERATE_FAIL("\u6570\u636e\u5f02\u5e38", 11),
	/** 乐观锁异常 */
	OPTIMISTIC_LOCKING_FAILURE_EXCEPTION("\u4fdd\u5b58\u5931\u8d25", 21) ;
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
    private ExcepCodeConst(String message, int state) {  
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
        for (ExcepCodeConst d : ExcepCodeConst.values()) {  
            if (d.getState() == state) {  
                return d.message;  
            }  
        }  
        return null;  
    } 
}
