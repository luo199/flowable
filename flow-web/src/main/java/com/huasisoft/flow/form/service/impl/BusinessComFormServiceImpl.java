package com.huasisoft.flow.form.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huasisoft.flow.business.entity.BaseTimeLimit;
import com.huasisoft.flow.business.entity.BusinessBase;
import com.huasisoft.flow.common.util.IDCreator;
import com.huasisoft.flow.form.entity.BusinessComForm;
import com.huasisoft.flow.form.mapper.BusinessComFormMapper;
import com.huasisoft.flow.form.service.BusinessComFormService;
import com.huasisoft.flow.form.vo.FormPageRequest;
import com.huasisoft.flow.platform.service.impl.CurrentUserHelper;
import com.huasisoft.flow.platform.vo.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @Auther: yn
 * @Description:
 * @Date 2020/9/17
 */
@Service
@Transactional(readOnly = true)
public class BusinessComFormServiceImpl extends ServiceImpl<BusinessComFormMapper, BusinessComForm> implements BusinessComFormService {

    @Autowired
    private BusinessComFormMapper mapper;

    /**
     * 分页查询列表
     * @param formPageRequest
     * @return
     */
    @Override
    public IPage<BusinessComForm> page(FormPageRequest formPageRequest) {
        IPage<BusinessComForm> userPage = new Page<>(formPageRequest.getCurrent(), formPageRequest.getSize());//参数一是当前页，参数二是每页个数
        IPage<BusinessComForm> businessBaseIPage = mapper.page(userPage, formPageRequest.getFormName());
        return businessBaseIPage;
    }

    /**
     * 主键查询
     * @param id
     * @return
     */
    @Override
    public BusinessComForm getById(String id) {
        return mapper.selectById(id);
    }

    /**
     * 保存
     * @param businessComForm
     * @return
     */
    @Override
    @Transactional
    public BusinessComForm saveForm(BusinessComForm businessComForm) {
        String id = businessComForm.getId();
        if (id != null && !"".equals(id)) {  //更新
            mapper.updateById(businessComForm);
        } else {            //保存
            //填入创建人信息
            Person person = CurrentUserHelper.currentUser();
            businessComForm.setCreateTime(new Date());
            businessComForm.setCreatePersonId(person.getId());
            businessComForm.setCreatePersonName(person.getName());
            businessComForm.setId(IDCreator.genId());
            mapper.insert(businessComForm);
        }
        return businessComForm;
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @Override
    @Transactional
    public int deleteById(String id) {
        return mapper.deleteById(id);
    }

    @Override
    public List<BusinessComForm> findAll() {
        QueryWrapper<BusinessComForm> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("CREATE_TIME");
        return mapper.selectList(queryWrapper);
    }
}
