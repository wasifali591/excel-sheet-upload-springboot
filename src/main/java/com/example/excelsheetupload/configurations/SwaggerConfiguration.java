package com.example.excelsheetupload.configurations;
/*
 * Copyright (c) 2022 Md Wasif Ali.
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

/**
 * This is a configuration class for Swagger
 *
 * @author Md Wasif Ali
 * @version 1.0
 * @since 23/11/22
 */
//@Configuration
//@EnableSwagger2
public class SwaggerConfiguration {
    /**
     * This method define {@link Docket} bean.
     * It returns an instance of ApiSelectorBuilder,
     * which control the endpoints exposed by Swagger
     *
     * @return {@link Docket}
     */
//    @Bean
    public Docket swaggerConfigurationDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(PathSelectors.ant("/**"))
                .apis(RequestHandlerSelectors.basePackage("com.example.excelsheetupload"))
                .build()
                .apiInfo(apiDetails());
    }

    /**
     * This method add custom information about the api.
     *
     * @return {@link ApiInfo}
     */
    private ApiInfo apiDetails() {
        return new ApiInfo(
                "Excel Sheet Upload",
                "Backend Spring Boot Project to upload data from a Excel Sheet to database ",
                "1.0",
                "Free to use",
                new springfox.documentation.service.Contact("Golify", "https://gloify.com/", "contact@gloify.com"),
                "Api License",
                "https://xyz.com",
                Collections.emptyList());

    }
}
