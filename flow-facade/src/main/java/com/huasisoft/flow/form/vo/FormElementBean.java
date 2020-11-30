package com.huasisoft.flow.form.vo;

import java.io.Serializable;

/**
 *   html元素及库表字段对应关系描述,在配置映射时使用
 *
 */
public class FormElementBean implements Serializable{
	

/** --------------------------------------------------- serialVersionUID 序列化ID ------------------------------------------------------- */
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 3703520059237394011L;
	
/** ----------------------------------------------------- properties 属性字段 ------------------------------------------------------------- */
	
	/* ---元素ID */
	private String elementID ;
	/* ---元素名称 */
	private String elementName ;
	/* ---元素值 */
	private String elementValue ;
	/* ---元素类型 */
	private String elementType ;
	/* ---库表列名 */
	private String columnName ;
	/* ---库表值 */
	private String columnValue ;
	/* ---库类型 */
	private String columnType ;
	/* ---表名 */
	private String tableName ;
	

/** -------------------------------------------- getters and setters 属性获取和设值方法  ------------------------------------------------- */
	
	public String getElementID() {
		return elementID;
	}
	public void setElementID(String elementID) {
		this.elementID = elementID;
	}
	
	public String getElementName() {
		return elementName;
	}
	public void setElementName(String elementName) {
		this.elementName = elementName;
	}
	
	public String getElementValue() {
		return elementValue;
	}
	public void setElementValue(String elementValue) {
		this.elementValue = elementValue;
	}
	
	public String getElementType() {
		return elementType;
	}
	public void setElementType(String elementType) {
		this.elementType = elementType;
	}
	
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	
	public String getColumnValue() {
		return columnValue;
	}
	public void setColumnValue(String columnValue) {
		this.columnValue = columnValue;
	}
	
	public String getColumnType() {
		return columnType;
	}
	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}
	
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
}
