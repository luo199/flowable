package com.huasisoft.flow.business.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.huasisoft.flow.business.entity.BusinessBase;
import com.huasisoft.flow.business.vo.BusinessPageRequest;

/**
 * @Auther: yn
 * @Description:
 * @Date 2020/8/14
 */
public interface BusinessBaseService extends IService<BusinessBase> {

    /**
     * 分页查询业务列表
     * @param page
     * @return
     */
    IPage<BusinessBase> page(BusinessPageRequest page);

    /**
     * 新增业务
     * @param businessBase
     * @return
     */
    BusinessBase createBusiness(BusinessBase businessBase);

    /**
     * 判断业务code是否存在
     * @param code
     * @return
     */
    Boolean findByBusinessCode(String code);

    /**
     * 删除业务
     * @param code
     * @return
     */
    int updateStatus(String code);

    /**
     * 删除业务
     * @param code
     * @return
     */
    int deleteByCode(String code);

    /**
     * 主键查询
     * @param code
     * @return
     */
    BusinessBase findByCode(String code);

    /**
     * 更新业务
     * @param businessBase
     * @return
     */
    int updateBusiness(BusinessBase businessBase);

    /**
     *  查询指定分类下是否有业务
     * @param cataCode
     * @return
     */
    Boolean checkBusinessByCatacode(String cataCode);

}
