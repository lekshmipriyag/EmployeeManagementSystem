package com.EmployeeManagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SpringFoxConfig {
    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("employee-api")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.EmployeeManagement"))
                //.paths(PathSelectors.regex("/employee/.*"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("Employee API")
                .description("Spring rest API reference")
                .licenseUrl("lekshmigeethaa@gmail.com")
                .version("1.0")
                .build();
    }
}