package com.huasisoft.flow.form.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.huasisoft.flow.form.entity.BusinessComForm;
import com.huasisoft.flow.form.vo.FormPageRequest;

import java.util.List;

/**
 * @Auther: yn
 * @Description: 表单定义接口
 * @Date 2020/9/17
 */
public interface BusinessComFormService extends IService<BusinessComForm> {

    /**
     * 分页查询列表
     *
     * @param formPageRequest
     * @param formPageRequest
     * @return
     */
    public IPage<BusinessComForm> page(FormPageRequest formPageRequest);

    /**
     * 主键查询
     *
     * @param id
     * @return
     */
    BusinessComForm getById(String id);


    /**
     * 保存
     * @param businessComForm
     * @return
     */
    BusinessComForm saveForm(BusinessComForm businessComForm);


    /**
     * 删除
     * @param id
     * @return
     */
    int deleteById(String id);

    List<BusinessComForm> findAll();


}
