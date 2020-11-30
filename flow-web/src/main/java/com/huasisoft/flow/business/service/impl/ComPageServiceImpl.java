package com.huasisoft.flow.business.service.impl;

import java.util.Date;
import java.util.List;

import com.huasisoft.flow.business.entity.ComAction;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huasisoft.flow.business.entity.ComPage;
import com.huasisoft.flow.business.mapper.ComPageMapper;
import com.huasisoft.flow.business.service.ComPageService;
import com.huasisoft.flow.common.util.IDCreator;
import com.huasisoft.flow.platform.service.impl.CurrentUserHelper;
import com.huasisoft.flow.platform.vo.Person;
import com.huasisoft.flow.process.vo.ModelPageRequest;

@Service
public class ComPageServiceImpl implements ComPageService {
	
	@Autowired
	private ComPageMapper mapper;

	@Override
	public ComPage findOne(String id) {
		return mapper.selectById(id);
	}

	@Override
	public IPage<ComPage> page(ModelPageRequest page) {
		 IPage<ComPage> iPage = new Page<>(page.getCurrent(), page.getSize());//参数一是当前页，参数二是每页个数
		 IPage<ComPage> result = mapper.selectPage(iPage, cataLogQueryWrapper(page.getName(),page.getCatalogCode()));
		 return result;
	}
	private QueryWrapper<ComPage> cataLogQueryWrapper(String name, String catalogCode) {
		QueryWrapper<ComPage> queryWrapper = null;
		if (StringUtils.isBlank(catalogCode) && StringUtils.isBlank(name)) {
			queryWrapper = Wrappers.emptyWrapper();
		} else if (StringUtils.isNotBlank(catalogCode)){
			queryWrapper = new QueryWrapper<ComPage>().eq("CATALOG_CODE", catalogCode).like("NAME",name);
		}else {
			queryWrapper = new QueryWrapper<ComPage>().like("NAME",name);
		}
		return queryWrapper;
	}

	@Override
	public List<ComPage> selectList(String name) {
		return mapper.selectList(nameQueryWrapper(name));
	}

	private QueryWrapper<ComPage> nameQueryWrapper(String name) {
		QueryWrapper<ComPage> queryWrapper = null;
		if(StringUtils.isBlank(name)) {
			queryWrapper = Wrappers.emptyWrapper();
		}else {
			queryWrapper = new QueryWrapper<ComPage>().like("name", name);
		}
		return queryWrapper;
	}

	@Override
	public ComPage save(ComPage comPage) {
		Assert.notNull(comPage, "不能为空");
		if(StringUtils.isBlank(comPage.getId())) {
			comPage.setId(IDCreator.genId());
			Person person = CurrentUserHelper.currentUser();
			comPage.setCreatePersonName(person.getName());
			comPage.setCreatePersonId(person.getId());
			comPage.setCreateTime(new Date());
			mapper.insert(comPage);
		}else {
			mapper.updateById(comPage);
		}
		return comPage;
	}


	@Override
	public int delete(String id) {
		return mapper.deleteById(id);
	}

}
