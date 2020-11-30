package com.huasisoft.flow.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huasisoft.flow.business.entity.BusinessBase;
import com.huasisoft.flow.business.entity.BusinessCatalog;
import com.huasisoft.flow.business.mapper.BusinessCatalogMapper;
import com.huasisoft.flow.business.service.BusinessCatalogService;
import com.huasisoft.flow.business.vo.CataLogManage;
import com.huasisoft.flow.platform.service.impl.CurrentUserHelper;
import com.huasisoft.flow.platform.vo.Person;
import com.huasisoft.flow.process.vo.PageRequest;
import com.sun.org.apache.xpath.internal.compiler.OpCodes;
import org.flowable.idm.api.User;
import org.flowable.ui.common.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @Auther: yn
 * @Description:
 * @Date 2020/8/14
 */
@Service
@Transactional(readOnly = true )
public class BusinessCatalogServiceImpl extends ServiceImpl<BusinessCatalogMapper, BusinessCatalog> implements BusinessCatalogService {

    @Autowired
    private BusinessCatalogMapper mapper;

    /**
     * 新增业务类别
     *
     * @param businessCatalog
     * @return
     */
    @Override
    @Transactional
    public BusinessCatalog createBusinessCatalog(BusinessCatalog businessCatalog) {
        //填入创建人信息
        Person person = CurrentUserHelper.currentUser();
        businessCatalog.setCreatrTime(new Date());
        businessCatalog.setCreatorId(person.getId());
        businessCatalog.setCreatorName(person.getName());
        //初始状态默认为 0
        businessCatalog.setStatus("0");
        boolean result = false;
        try {
            result = save(businessCatalog);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (result) {
            return businessCatalog;
        } else {
            return null;
        }
    }

    /**
     * 查询指定父节点及其所有子节点集合
     * @param pcodes
     * @return
     */
    @Override
    public List<BusinessCatalog> treeListAll(String... pcodes) {
        if(pcodes==null||pcodes.length==0){
            pcodes=new String[]{"0"};
        }
        List<BusinessCatalog> set = new ArrayList<>();
        for (String pcode : pcodes) {
            if (!"0".equals(pcode)){
                set.add(findByCode(pcode));
            }
            List<BusinessCatalog> temp = mapper.listByPCode(pcode);
            if(temp!=null&&temp.size()>0){
                set.addAll(temp);
            }
        }
        return set;
    }

    /**
     * 查询指定父节点下的一级节点（不包含父节点）
     * @param pcodes
     * @return
     */
    @Override
    public List<BusinessCatalog> childListCataLog(String... pcodes) {
        if(pcodes==null||pcodes.length==0){
            pcodes=new String[]{"0"};
        }
        QueryWrapper<BusinessCatalog> queryWrapper = new QueryWrapper<>();

        List<BusinessCatalog> set = new ArrayList<>();
        for (String pcode : pcodes) {
            queryWrapper.eq("PCODE", pcode).eq("STATUS", 0).orderByAsc("ORDER_NUMBER");
            List<BusinessCatalog> temp = mapper.selectList(queryWrapper);
            if(temp!=null&&temp.size()>0){
                set.addAll(temp);
            }
        }
        return set;
    }

    /**
     * 根据子节点code查询所有父级节点
     * @param codes
     * @return
     */
    @Override
    public List<BusinessCatalog> findCataLogListByChildCode(String... codes) {
        List<BusinessCatalog> set = new ArrayList<>();
        if (codes!=null&& codes.length>0){
            for (String code : codes) {
                List<BusinessCatalog> businessCatalogs = mapper.selectListByChildCode(code);
                if (businessCatalogs.size()>0&&businessCatalogs!=null){
                    set.addAll(businessCatalogs);
                }
            }
        }
        return set;
    }

    /**
     * 分页查询所有节点集合
     * @param page
     * @return
     */
    @Override
    public IPage<BusinessCatalog> page(PageRequest page) {
        IPage<BusinessCatalog> userPage = new Page<>(page.getCurrent(), page.getSize());//参数一是当前页，参数二是每页个数
        return mapper.page(userPage);
    }

    /**
     * 删除指定分类
     * @param code
     * @return
     */
    @Override
    @Transactional
    public int updateStatus(String code) {
        return mapper.updateStatus(code);
    }

    /**
     * 删除指定分类
     * @param code
     * @return
     */
    @Override
    @Transactional
    public int deleteByCode(String code) {
        LambdaQueryWrapper<BusinessCatalog> lambda = Wrappers.<BusinessCatalog>lambdaQuery();
        lambda.eq(BusinessCatalog::getCode, code);
        return mapper.delete(lambda);
    }

    /**
     * 判断新增code是否存在
     * @param code
     * @return
     */
    @Override
    public boolean findByCataLogCode(String code) {
        QueryWrapper<BusinessCatalog> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("CODE").eq("CODE", code);
        List<BusinessCatalog> businessList = list(queryWrapper);
        return businessList != null && businessList.size() > 0;
    }

    /**
     * 判断父节点编码是否存在
     * @param pcode
     * @return
     */
    @Override
    public boolean findByCataLogPcode(String pcode) {
        if ("0".equals(pcode)) {
            //顶级
            return true;
        } else {
            QueryWrapper<BusinessCatalog> queryWrapper = new QueryWrapper<>();
            queryWrapper.select("CODE").eq("CODE", pcode);
            List<BusinessCatalog> businessList = list(queryWrapper);
            return businessList != null && businessList.size() > 0;
        }
    }

    /**
     * 根据code查询单个分类
     * @param code
     * @return
     */
    @Override
    public BusinessCatalog findByCode(String code) {
        QueryWrapper<BusinessCatalog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("CODE", code).eq("STATUS",0 );
        return getOne(queryWrapper);
    }

    /**
     * 更新单个分类
     * @param businessCatalog
     * @return
     */
    @Override
    @Transactional
    public int updateCataLog(BusinessCatalog businessCatalog) {
        UpdateWrapper<BusinessCatalog> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("NAME", businessCatalog.getName())
                .set("PCODE", businessCatalog.getPcode())
                .set("ORDER_NUMBER", businessCatalog.getOrderNumber())
                .set("STATUS", 0)
                .eq("CODE", businessCatalog.getCode());
        return mapper.update(null, updateWrapper);
    }

    /**
     * 保存指定父节点下的子节点集合
     * @param cataLogManage
     * @return
     */
    @Override
    @Transactional
    public List<BusinessCatalog> saveChildCataLogList(CataLogManage cataLogManage) {
        //        //父节点编码
        String code = cataLogManage.getCode();
        //子节点集合
        List<BusinessCatalog> catalogList = cataLogManage.getCatalogList();
        if (catalogList.size() > 0 && catalogList != null) {
            mapper.updateStatusWithoutMe(1, code);
            for (BusinessCatalog businessCatalog : catalogList) {
                if (businessCatalog.getCode()==null||"".equals(businessCatalog.getCode())){
                    continue;
                }
                businessCatalog.setPcode(code);
                boolean flag = findByCataLogCode(businessCatalog.getCode());
                if (flag){
                    updateCataLog(businessCatalog);
                    mapper.updateStatusWithoutMe(0, businessCatalog.getCode());
                }else {
                    createBusinessCatalog(businessCatalog);
                }
            }
        }
        return catalogList;
    }

    /**
     * 根据code查询父节点编码
     * @param code
     * @return
     */
    @Override
    public String findPcodeByCode(String code) {
        BusinessCatalog businessCatalog = mapper.selectById(code);
        if (businessCatalog!=null){
            return businessCatalog.getPcode();
        }else {
            return null;
        }
    }
}
