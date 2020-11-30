package com.huasisoft.flow.form.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FormBean implements Serializable {

    private static final long serialVersionUID = -5522817740477648529L;

    private String businessId ;

    private String instanceId ;

    private Map<String,List<ElementBean>> formMap = new HashMap<>();

    public FormBean() {
    }

    public FormBean(String businessId,String instanceId,Map<String,List<ElementBean>> formMap) {
        this.businessId = businessId;
        this.instanceId = instanceId;
        setFormMap(formMap);
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public Map<String, List<ElementBean>> getFormMap() {
        return formMap;
    }

    public void setFormMap(Map<String, List<ElementBean>> formMap) {
        this.formMap = formMap;
    }
}
