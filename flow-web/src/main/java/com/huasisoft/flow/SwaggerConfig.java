package com.huasisoft.flow;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;

import io.swagger.annotations.ApiOperation;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig{

	/*private List<Parameter> addHeader() {
        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<>();
        tokenPar.name(CommonConst.SYSTEM_NAME).description(CommonConst.SYSTEM_NAME_CH).modelRef(new ModelRef("string")).parameterType("header").required(true).build();
        pars.add(tokenPar.build());
        tokenPar.name(CommonConst.COMPANY_NAME).description(CommonConst.COMPANY_NAME_CH).modelRef(new ModelRef("string")).parameterType("header").required(true).build();
        pars.add(tokenPar.build());
        return pars;
    }*/
	
	/*@Bean
    public Docket testApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("easyoffice of test")
                .select()  
                .apis(RequestHandlerSelectors.basePackage("com.huasisoft.flow"))// 选择那些路径和api会生成document
                .paths(PathSelectors.any()) // 对所有路径进行监控
                .build()
                .globalOperationParameters(addHeader())//所有swager请求添加请求头
                .apiInfo(apiInfo());
    }
	
	 private ApiInfo apiInfo() {
	        return new ApiInfoBuilder().title("框架组件功能测试")
	                .description("公文办理各模块相关的接口")
	                .termsOfServiceUrl("www.huasisoft.com")
	                .contact(new Contact("huasisoft", "www.huasisoft.com", "zhaobo@huasisoft.com"))
	                .version("8.0")
	                .build();
	    }*/
	    
	/**
	 * 创建api信息
	 * @param name
	 * @param description
	 * @param version
	 * @return
	 */
	private ApiInfo apiInfo(String name, String description, String version) {
        return new ApiInfoBuilder().title(name).description(description).version(version).build();
    }
	/**
	 * 框架组件功能测试api
	 * @return
	 */
	@Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo("测试","框架组件功能测试","1.0"))
                .select()
                //.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .apis(RequestHandlerSelectors.basePackage("com.huasisoft.flow.test"))
                .paths(PathSelectors.any())
                .build()
                .groupName("框架测试")
                ;
    }

    @Bean
    public Docket createFlowableApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo("flowable-rest","flowable-rest","1.0"))
                .select()
                //.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .apis(RequestHandlerSelectors.basePackage("org.flowable"))
                .paths(PathSelectors.any())
                .build()
                .groupName("flowable-rest")
                ;
    }
    
    @Bean
    public Docket createDesignApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo("流程管理","流程管理","1.0"))
                .select()
                //.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .apis(RequestHandlerSelectors.basePackage("com.huasisoft.flow.process"))
                .paths(PathSelectors.any())
                .build()
                .groupName("流程管理")
                ;
    }

    @Bean
    public Docket createBusinessApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo("业务管理","业务管理","1.0"))
                .select()
                //.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .apis(RequestHandlerSelectors.basePackage("com.huasisoft.flow.business"))
                .paths(PathSelectors.any())
                .build()
                .groupName("业务管理")
                ;
    }
    @Bean
    public Docket createPlatformApi() {
    	return new Docket(DocumentationType.SWAGGER_2)
    			.apiInfo(apiInfo("平台管理","平台管理","1.0"))
    			.select()
    			.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
    			.apis(RequestHandlerSelectors.basePackage("com.huasisoft.flow.platform"))
    			.paths(PathSelectors.any())
    			.build()
    			.groupName("平台管理")
    			;
    }
	public static Predicate<RequestHandler> basePackage(final String basePackage) {
        return input -> declaringClass(input).transform(handlerPackage(basePackage)).or(true);
    }

    private static Function<Class<?>, Boolean> handlerPackage(final String basePackage)     {
        return input -> {
            // 循环判断匹配
            for (String strPackage : basePackage.split(",")) {
                boolean isMatch = input.getPackage().getName().startsWith(strPackage);
                if (isMatch) {
                    return true;
                }
            }
            return false;
        };
    }

	private static Optional<? extends Class<?>> declaringClass(RequestHandler input) {
		return Optional.fromNullable(input.declaringClass());
    }
}
