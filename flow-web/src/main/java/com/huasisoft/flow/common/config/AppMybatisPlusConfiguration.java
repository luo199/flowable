package com.huasisoft.flow.common.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;

/**
 * @ClassName: AppMybatisPlusConfiguration
 * @Author: flq
 * @Description:
 * @CreateTIme: 2020.7.30 0018 上午 11:02
 **/
@MapperScan(basePackages = {
		"com.huasisoft.flow.**.mapper"
},
        sqlSessionTemplateRef = "appSqlSessionTemplate",
        sqlSessionFactoryRef = "appSqlSessionFactory"
)
@EnableConfigurationProperties(MybatisPlusProperties.class)
@Configuration
public class AppMybatisPlusConfiguration extends AbstractMybatisPlusConfiguration {
	
    @Bean(name = "appSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactoryBean(DataSource dataSource,
                                                   MybatisPlusProperties properties,
                                                   ResourceLoader resourceLoader,
                                                   ApplicationContext applicationContext) throws Exception {
    	
        Interceptor[] plugins ={paginationInterceptor()};
    	SqlSessionFactory sqlSessionFactory = getSqlSessionFactory(dataSource,
							                properties,
							                resourceLoader,
							                plugins,
							                null,
							                applicationContext);
    	//sqlSessionFactory.getConfiguration().getVariables().setProperty("dbSchema", dbSchema);
    	//System.out.println(sqlSessionFactory.getConfiguration().getVariables().getProperty("dbSchema"));
    	return sqlSessionFactory;
    }

    @Bean(name = "appSqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(MybatisPlusProperties properties,
                                                 @Qualifier("appSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return getSqlSessionTemplate(sqlSessionFactory, properties);
    }

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
