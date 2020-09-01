package com.huasisoft.flow.test.entity;

import com.huasisoft.flow.process.vo.PageRequest;
import lombok.Data;

@Data
public class TestPageRequest  extends PageRequest {

    /**
     * 流程名称
     */
    private String name;
}

