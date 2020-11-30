package com.huasisoft.flow.business.service;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.huasisoft.flow.business.entity.BusinessCatalog;
import com.huasisoft.flow.business.vo.CataLogManage;
import com.huasisoft.flow.process.vo.PageRequest;

/**
 * @Auther: yn
 * @Description:
 * @Date 2020/8/14
 */
public interface BusinessCatalogService extends IService<BusinessCatalog> {

    /**
     * 创建分类
     *
     * @param businessCatalog
     * @return
     */
    BusinessCatalog createBusinessCatalog(BusinessCatalog businessCatalog);

    /**
     * 查询指定父节点下的所有子节点（包括父节点）
     *
     * @param pcodes
     * @return
     */
    List<BusinessCatalog> treeListAll(String... pcodes);

    /**
     * 查询指定父节点下的所有子节点（不包括父节点）
     * @param pcodes
     * @return
     */
    List<BusinessCatalog> childListCataLog(String... pcodes);


    /**
     * 根据子节点code查询所有父级节点
     * @param codes
     * @return
     */
    List<BusinessCatalog> findCataLogListByChildCode(String... codes);

    /**
     * 分页查询
     * @param page
     * @return
     */
    IPage<BusinessCatalog> page(PageRequest page);

    /**
     * 删除
     * @param code
     * @return
     */
    int updateStatus(String code);

    /**
     * 删除
     * @param code
     * @return
     */
    int deleteByCode(String code);

    /**
     * 判断code重复
     * @param code
     * @return
     */
    boolean findByCataLogCode(String code);

    /**
     * 判断父节点是否存在
     * @param pcode
     * @return
     */
    boolean findByCataLogPcode(String pcode);

    /**
     * 主键查询
     * @param code
     * @return
     */
    BusinessCatalog findByCode(String code);

    /**
     * 更新分类
     * @param businessCatalog
     * @return
     */
    int updateCataLog(BusinessCatalog businessCatalog);

    /**
     * 保存子节点列表
     * @param cataLogManage
     * @return
     */
    List<BusinessCatalog> saveChildCataLogList(CataLogManage cataLogManage);

    /**
     * 根据code查询父节点
     * @param code
     * @return
     */
    String findPcodeByCode(String code);


}
