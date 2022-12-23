package com.lt.dom.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/*
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
*/

import java.util.Arrays;
import java.util.Collections;

//@Configuration
public class SwaggerConfig {


        @Bean
        public OpenAPI springOpenAPI() {
            return new OpenAPI()
                    .info(new Info().title("My API")
                            .description("My API service documentation. ")
                            .version("v1.0")

                            .license(new License().name("Terms of Use").url("https://myapi.com/terms.html")));
        }

/*    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).securitySchemes(Arrays.asList(apiKey())).select()
                .apis(RequestHandlerSelectors.basePackage("com.tm.x.y.z.your.controller")).paths(PathSelectors.any()).
                        build().apiInfo(apiInfo());
    }*/
/*
    private ApiInfo apiInfo() {
        return new ApiInfo("MyApp Rest APIs",
                "APIs for MyApp.",
                "1.0",
                "Terms of service",
                new Contact("test", "www.org.com", "test@emaildomain.com"),
                "License of API",
                "API license URL",
                Collections.emptyList());
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }*/
}