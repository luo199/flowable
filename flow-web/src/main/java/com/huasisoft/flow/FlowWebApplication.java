package com.huasisoft.flow;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import com.huasisoft.flow.common.config.AppDispatcherServletConfiguration;
import com.huasisoft.flow.common.config.ApplicationConfiguration;

@Import(value={
		// 引入修改的配置
		ApplicationConfiguration.class,
	    AppDispatcherServletConfiguration.class
		// 引入 DatabaseConfiguration 表更新转换
		//DatabaseConfiguration.class
		})
@SpringBootApplication(exclude={SecurityAutoConfiguration.class})
@EnableTransactionManagement
public class FlowWebApplication {
	
	public static ApplicationContext applicationContext ;

	public static void main(String[] args) {
		SpringApplication.run(FlowWebApplication.class, args);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) throws BeansException {
		return (T) applicationContext.getBean(name);
	}
	
	@Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        // 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
        // paginationInterceptor.setOverflow(false);
        // 设置最大单页限制数量，默认 500 条，-1 不受限制
        // paginationInterceptor.setLimit(500);
        // 开启 count 的 join 优化,只针对部分 left join
        paginationInterceptor.setCountSqlParser(new JsqlParserCountOptimize(true));
        return paginationInterceptor;
    }
	 
	/*@Bean(name="pageHelper")  
	public PageHelper pageHelper() {  
		Properties properties = new Properties();
		properties.put("dialect", "oracle");
		properties.put("reasonable",true);
		properties.put("returnPageInfo","always");
		properties.put("supportMethodsArguments",true);
		properties.put("params","count=countSql");
		properties.put("autoRuntimeDialect",true);
		PageHelper pageHelper = new PageHelper();
		pageHelper.setProperties(properties); 
	    return pageHelper;  
	}*/

}
