package com.huasisoft.flow.process.vo;

import lombok.Data;

/**
 * @Auther: yn
 * @Description: 流程列表分页请求参数
 * @Date 2020/8/4
 */
@Data
public class ModelPageRequest extends PageRequest {


    private static final long serialVersionUID = 1010904747029624635L;
    /**
     * 流程名称
     */
    private String name;
}
