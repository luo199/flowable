package com.huasisoft.flow.business.action;

import java.util.Map;
/**
 * 模板初始化化数据接口
 * @author Administrator
 *
 */
public interface ITemplateInitService {
		
	Map<String,Object> init(String businessIncetanceId);
	
	
	@SuppressWarnings("rawtypes")
	default boolean match(String className) {
		try {
			Class clazz = Class.forName(className);
			if(clazz.newInstance() instanceof ITemplateInitService) {
				return true;
			}else{
				return false;
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return false;
		
	}
}
