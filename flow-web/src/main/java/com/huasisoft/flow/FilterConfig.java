package com.huasisoft.flow;

import javax.servlet.Filter;

import org.jasig.cas.client.authentication.AuthenticationFilter;
import org.jasig.cas.client.session.SingleSignOutFilter;
import org.jasig.cas.client.validation.Cas20ProxyReceivingTicketValidationFilter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import com.huasisoft.flow.common.filter.CheckUserFilter;

@Configuration
public class FilterConfig implements ApplicationContextAware{

	@Value("${singleSignOutFilter.casServerUrlPrefix}")  
	String casServerUrlPrefix;  
	
	@Value("${authenticationFilter.casServerLoginUrl}")  
	String casServerLoginUrl;  
	
	@Value("${authenticationFilter.serverName}")  
	String serverName;  
	
	@Value("${authenticationFilter.ignorePattern}")  
	String ignorePattern;  
	
	
	private static ApplicationContext applicationContext ;
	
	@Bean
	@DependsOn("checkUserFilter")
	public FilterRegistrationBean<Filter> checkUserFilterBean(){
		FilterRegistrationBean<Filter> checkUserFilterBean = new FilterRegistrationBean<Filter>();
		checkUserFilterBean.setFilter(applicationContext.getBean(CheckUserFilter.class));
		checkUserFilterBean.setName("checkUserFilter");
		checkUserFilterBean.addUrlPatterns("/api/*","/app/*");
		checkUserFilterBean.setOrder(3);
		return checkUserFilterBean;
	}
	
	@Bean
	public FilterRegistrationBean<Filter> singleSignOutFilterBean(){
		FilterRegistrationBean<Filter> singleSignOutFilterBean = new FilterRegistrationBean<Filter>();
		singleSignOutFilterBean.setFilter(new SingleSignOutFilter());
		singleSignOutFilterBean.setName("singleSignOutFilter");
		singleSignOutFilterBean.addUrlPatterns("/*");
		singleSignOutFilterBean.addInitParameter("casServerUrlPrefix", casServerUrlPrefix);
		singleSignOutFilterBean.setOrder(4);
		return singleSignOutFilterBean;
	}
	
	@Bean
	public FilterRegistrationBean<Filter> authenticationFilterBean(){
		FilterRegistrationBean<Filter> authenticationFilterBean = new FilterRegistrationBean<Filter>();
		authenticationFilterBean.setFilter(new AuthenticationFilter());
		authenticationFilterBean.setName("authenticationFilter");
		authenticationFilterBean.addUrlPatterns("/api/*","/app/*");
		authenticationFilterBean.addInitParameter("casServerLoginUrl", casServerLoginUrl);
		authenticationFilterBean.addInitParameter("serverName", serverName);
		authenticationFilterBean.addInitParameter("ignorePattern", ignorePattern);
		authenticationFilterBean.setOrder(1);
		return authenticationFilterBean;
	}
	
	@Bean
	public FilterRegistrationBean<Filter> cas20ProxyReceivingTicketValidationFilterBean(){
		FilterRegistrationBean<Filter> cas20ProxyReceivingTicketValidationFilterBean = new FilterRegistrationBean<Filter>();
		cas20ProxyReceivingTicketValidationFilterBean.setFilter(new Cas20ProxyReceivingTicketValidationFilter());
		cas20ProxyReceivingTicketValidationFilterBean.setName("cas20ProxyReceivingTicketValidationFilter");
		cas20ProxyReceivingTicketValidationFilterBean.addUrlPatterns("/api/*","/app/*");
		cas20ProxyReceivingTicketValidationFilterBean.addInitParameter("encoding", "UTF-8");
		cas20ProxyReceivingTicketValidationFilterBean.addInitParameter("casServerUrlPrefix", casServerUrlPrefix);
		cas20ProxyReceivingTicketValidationFilterBean.addInitParameter("serverName", serverName);
		cas20ProxyReceivingTicketValidationFilterBean.setOrder(2);
		return cas20ProxyReceivingTicketValidationFilterBean;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		FilterConfig.applicationContext = applicationContext;
	}

}
