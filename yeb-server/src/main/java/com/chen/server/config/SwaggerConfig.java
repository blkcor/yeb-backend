package com.chen.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * Swagger2配置
 *
 * @author: blkcor
 * @DATE: 2022/3/25  20:19
 * @PROJECT_NAME: yeb
 * @since: jdk1.8
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.chen.server.controller"))
                .paths(PathSelectors.any())
                .build()
                .securityContexts(securityContexts())
                .securitySchemes(securitySchemes());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("云e办接口文档")
                .description("云e办接口文档")
                .version("1.0")
                .contact(new Contact("blkcor", "http://localhost:8888/doc.html", "1029129867@qq.com"))
                .build();
    }

    private List<ApiKey> securitySchemes() {
        List<ApiKey> apiKeys = new ArrayList<>();
        ApiKey apiKey = new ApiKey("Authorization", "Authorization", "Header");
        apiKeys.add(apiKey);
        return apiKeys;
    }

    private List<SecurityContext> securityContexts() {
        //需要认证的路径
        List<SecurityContext> securityContexts = new ArrayList<>();
        securityContexts.add(getContextByPath("/hello/.*"));
        return securityContexts;
    }

    private SecurityContext getContextByPath(String pathRegex) {
        return SecurityContext.builder().securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex(pathRegex)).build();
    }

    private List<SecurityReference> defaultAuth() {
        List<SecurityReference> securityReference = new ArrayList<>();
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        securityReference.add(new SecurityReference("Authorization", authorizationScopes));
        return securityReference;
    }
}