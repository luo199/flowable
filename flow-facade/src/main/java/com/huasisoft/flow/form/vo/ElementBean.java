package com.huasisoft.flow.form.vo;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ElementBean implements Cloneable {
    private String elementId;
    private String key;
    private String type;
    private Object value;
    private Integer permOpe;

    /** 是-1 否-2 */
    private Integer isEmpty;

    /** 为空的错误提醒 */
    private String emptyMsg;

    /** 是-1 否-2 */
    private Integer isLengthMin;

    /** 最小长度 */
    private Integer lengthMin;

    /** 小于最小长度的提醒 */
    private String lengthMinMsg;

    /** 是-1 否-2 */
    private Integer isLengthMax;

    /** 最大长度 */
    private Integer lengthMax;

    /** 大于最大长度的提醒 */
    private String lengthMaxMsg;

    /** 是否最小数字 */
    private Integer isNumericMin;

    /** 最小数字 */
    private Integer numericMin;

    /** 小于最小数字的提醒 */
    private String numericMinMsg;

    /** 是否最大数字 */
    private Integer isNumericMax;

    /** 最大数字 */
    private Integer numericMax;

    /** 大于最大数字的提醒 */
    private String numericMaxMsg;

    /** 是否正则表达式 */
    private Integer isRegularExpression;

    /** 正则表达式 */
    private String regularExpression;

    /** 不符合正则的提示信息 */
    private String errorMessage;


    public ElementBean() {
    }

    public String getElementId() {
        return elementId;
    }

    public void setElementId(String elementId) {
        this.elementId = elementId;
    }

    public Integer getIsEmpty() {
        return isEmpty;
    }

    public void setIsEmpty(Integer isEmpty) {
        this.isEmpty = isEmpty;
    }

    public String getEmptyMsg() {
        return emptyMsg;
    }

    public void setEmptyMsg(String emptyMsg) {
        this.emptyMsg = emptyMsg;
    }

    public Integer getIsLengthMin() {
        return isLengthMin;
    }

    public void setIsLengthMin(Integer isLengthMin) {
        this.isLengthMin = isLengthMin;
    }

    public Integer getLengthMin() {
        return lengthMin;
    }

    public void setLengthMin(Integer lengthMin) {
        this.lengthMin = lengthMin;
    }

    public String getLengthMinMsg() {
        return lengthMinMsg;
    }

    public void setLengthMinMsg(String lengthMinMsg) {
        this.lengthMinMsg = lengthMinMsg;
    }

    public Integer getIsLengthMax() {
        return isLengthMax;
    }

    public void setIsLengthMax(Integer isLengthMax) {
        this.isLengthMax = isLengthMax;
    }

    public Integer getLengthMax() {
        return lengthMax;
    }

    public void setLengthMax(Integer lengthMax) {
        this.lengthMax = lengthMax;
    }

    public String getLengthMaxMsg() {
        return lengthMaxMsg;
    }

    public void setLengthMaxMsg(String lengthMaxMsg) {
        this.lengthMaxMsg = lengthMaxMsg;
    }

    public Integer getIsNumericMin() {
        return isNumericMin;
    }

    public void setIsNumericMin(Integer isNumericMin) {
        this.isNumericMin = isNumericMin;
    }

    public Integer getNumericMin() {
        return numericMin;
    }

    public void setNumericMin(Integer numericMin) {
        this.numericMin = numericMin;
    }

    public String getNumericMinMsg() {
        return numericMinMsg;
    }

    public void setNumericMinMsg(String numericMinMsg) {
        this.numericMinMsg = numericMinMsg;
    }

    public Integer getIsNumericMax() {
        return isNumericMax;
    }

    public void setIsNumericMax(Integer isNumericMax) {
        this.isNumericMax = isNumericMax;
    }

    public Integer getNumericMax() {
        return numericMax;
    }

    public void setNumericMax(Integer numericMax) {
        this.numericMax = numericMax;
    }

    public String getNumericMaxMsg() {
        return numericMaxMsg;
    }

    public void setNumericMaxMsg(String numericMaxMsg) {
        this.numericMaxMsg = numericMaxMsg;
    }

    public Integer getIsRegularExpression() {
        return isRegularExpression;
    }

    public void setIsRegularExpression(Integer isRegularExpression) {
        this.isRegularExpression = isRegularExpression;
    }

    public String getRegularExpression() {
        return regularExpression;
    }

    public void setRegularExpression(String regularExpression) {
        this.regularExpression = regularExpression;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public ElementBean(String key, String type, Object value, Integer permOpe) {
        this(key,type);
        this.permOpe = permOpe;
        setValue(value);
    }
    public ElementBean(String key, String type) {
        this.key = key;
        this.type = type;
    }

    public ElementBean(String key, String type, Object value) {
        this.key = key;
        this.type = type;
        this.value = value;
    }

    public ElementBean(String key, String type, Integer permOpe) {
        this(key,type);
        this.permOpe = permOpe;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getValue() {
        return value;
    }

    public ElementBean setValue(Object value) {
        if(value instanceof Date) {
            value = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format((Date) value);
        }
        this.value = value;
        return this;
    }

    @Override
    public String toString() {
        return "ElementBean{" +
                "key='" + key + '\'' +
                ", type='" + type + '\'' +
                ", value=" + value +
                ", permOpe=" + permOpe +
                ", isEmpty=" + isEmpty +
                ", emptyMsg='" + emptyMsg + '\'' +
                ", isLengthMin=" + isLengthMin +
                ", lengthMin=" + lengthMin +
                ", lengthMinMsg='" + lengthMinMsg + '\'' +
                ", isLengthMax=" + isLengthMax +
                ", lengthMax=" + lengthMax +
                ", lengthMaxMsg='" + lengthMaxMsg + '\'' +
                ", isNumericMin=" + isNumericMin +
                ", numericMin=" + numericMin +
                ", numericMinMsg='" + numericMinMsg + '\'' +
                ", isNumericMax=" + isNumericMax +
                ", numericMax=" + numericMax +
                ", numericMaxMsg='" + numericMaxMsg + '\'' +
                ", isRegularExpression=" + isRegularExpression +
                ", regularExpression='" + regularExpression + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }

    public Integer getPermOpe() {
        return permOpe;
    }

    public void setPermOpe(Integer permOpe) {
        this.permOpe = permOpe;
    }

    public ElementBean copy() {
        return new ElementBean(key,type,value,permOpe);
    }

}

