package com.huasisoft.flow.common.config;
import org.flowable.ui.common.service.idm.RemoteIdmService;
import org.flowable.ui.modeler.properties.FlowableModelerAppProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@EnableConfigurationProperties(FlowableModelerAppProperties.class)
@ComponentScan(basePackages = {
//        "org.flowable.ui.modeler.conf",
        "org.flowable.ui.modeler.repository",
        "org.flowable.ui.modeler.service",
//        "org.flowable.ui.modeler.security", //授权方面的都不需要
//        "org.flowable.ui.common.conf", // flowable 开发环境内置的数据库连接
//        "org.flowable.ui.common.filter", // IDM 方面的过滤器
        "org.flowable.ui.common.service",
        "org.flowable.ui.common.repository",
        //
//        "org.flowable.ui.common.security",//授权方面的都不需要
        "org.flowable.ui.common.tenant" },excludeFilters = {
// 移除 RemoteIdmService
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value =
                RemoteIdmService.class),
}
)
public class ApplicationConfiguration {

   /* @Bean
    public ServletRegistrationBean modelerApiServlet(ApplicationContext applicationContext) {
        AnnotationConfigWebApplicationContext dispatcherServletConfiguration = new AnnotationConfigWebApplicationContext();
        dispatcherServletConfiguration.setParent(applicationContext);
        dispatcherServletConfiguration.register(ApiDispatcherServletConfiguration.class);
        DispatcherServlet servlet = new DispatcherServlet(dispatcherServletConfiguration);
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(servlet, "/app/*");
        registrationBean.setName("Flowable Modeler App API Servlet");
        registrationBean.setLoadOnStartup(2);
        registrationBean.setAsyncSupported(true);
        return registrationBean;
    }*/
}