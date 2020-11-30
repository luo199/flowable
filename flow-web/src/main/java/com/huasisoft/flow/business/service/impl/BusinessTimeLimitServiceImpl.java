package com.huasisoft.flow.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.huasisoft.flow.business.entity.BaseTimeLimit;
import com.huasisoft.flow.business.mapper.BaseTimeLimitDmMapper;
import com.huasisoft.flow.business.service.BusinessTimeLimitService;
import com.huasisoft.flow.common.util.IDCreator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.HashMap;

/**
 * @Auther: yn
 * @Description:
 * @Date 2020/9/9 000911:07
 */
@Service
@Transactional(readOnly = true)
public class BusinessTimeLimitServiceImpl implements BusinessTimeLimitService {

    @Autowired
    private BaseTimeLimitDmMapper baseTimeLimitDmMapper;

    /**
     * 根据业务编码和流程节点Id获取时限
     *
     * @param businessId
     * @param flowId
     * @return
     */
    @Override
    public BaseTimeLimit getTimeLimit(String businessId, String flowId) {
        QueryWrapper<BaseTimeLimit> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("BIZ_CODE", businessId).eq("TASK_ID", flowId);
        return baseTimeLimitDmMapper.selectOne(queryWrapper);
    }

    /**
     * 保存时限配置
     * @param baseTimeLimit
     * @return
     */
    @Override
    @Transactional
    public BaseTimeLimit saveTimiLimit(BaseTimeLimit baseTimeLimit) {
        Assert.notNull(baseTimeLimit, "不能为空");
        if (StringUtils.isBlank(baseTimeLimit.getId())) {
            baseTimeLimit.setId(IDCreator.genId());
            baseTimeLimitDmMapper.insert(baseTimeLimit);
        } else {
            baseTimeLimitDmMapper.updateById(baseTimeLimit);
        }
        return baseTimeLimit;
    }

    /**
     * 删除时限配置
     * @param taskId
     * @return
     */
    @Override
    @Transactional
    public int deleteByTaskId(String taskId) {
        HashMap<String, Object> deleteMap = new HashMap<>();
        deleteMap.put("TASK_ID", taskId);
        return baseTimeLimitDmMapper.deleteByMap(deleteMap);
    }
}
