package com.huasisoft.flow.form.vo;

import java.io.Serializable;

/**
 *  页面html元素描述对象，解析HTML时使用
 */
public class HtmlElementBean implements Serializable{
	
/** --------------------------------------------------- serialVersionUID 序列化ID ------------------------------------------------------- */
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -3256814241137742070L;
	
	/* ---元素名称 */
	private String name ;
	/* 元素ID */
	private String id ;
	/* ---元素类型 */
	private String type ;
	/* ---元素样式 */
	private String style ;
	/* ---元素class属性 */
	private String attrClass ;
	/* ---元素值 */
	private String value ;
	
	
/** -------------------------------------------- getters and setters 属性获取和设值方法  ------------------------------------------------- */
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	
	public String getAttrClass() {
		return attrClass;
	}
	public void setAttrClass(String attrClass) {
		this.attrClass = attrClass;
	}
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
/** -------------------------------------------- 重写hash与equal方法  ------------------------------------------------- */
	
	@Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }
	
	@Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass())
			return false;
        HtmlElementBean other = (HtmlElementBean) obj;
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        if (type == null) {
            if (other.type != null) {
                return false;
            }
        } else if (!type.equals(other.type)) {
            return false;
        }
        return true;
    }
	
/** -------------------------------------------- constructors 构造方法  ------------------------------------------------- */
	
	public HtmlElementBean() {
		super();
	}
	
	/**
	 * 带参构造函数
	 * @param name
	 * @param id
	 * @param style
	 * @param attrClass
	 * @param value
	 */
	public HtmlElementBean(String name, String id, String style, String attrClass, String value) {
		super();
		this.name = name;
		this.id = id;
		this.style = style;
		this.attrClass = attrClass;
		this.value = value;
	}
	
	

/** -------------------------------------------- 重写hash与equal方法  ------------------------------------------------- */

	@Override
	public String toString() {
		return "HtmlElement{" +
				"name='" + name + '\'' +
				", id='" + id + '\'' +
				", type='" + type + '\'' +
				", style='" + style + '\'' +
				", attrClass='" + attrClass + '\'' +
				", value='" + value + '\'' +
				'}';
	}

}
