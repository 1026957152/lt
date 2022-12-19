package com.lt.dom;


import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

//@lombok.NoArgsConstructor
//@lombok.Data
@SpringBootApplication
@EnableAsync
@EnableJpaAuditing
@EnableScheduling

//@EnableRabbit
public class DomApplication {

    @com.fasterxml.jackson.annotation.JsonProperty("errorn")
    private Long errorn;
    @com.fasterxml.jackson.annotation.JsonProperty("g_err")
    private Long gErr;
  //  @com.fasterxml.jackson.annotation.JsonProperty("list")
  //  private java.util.List<ListDTO> list;
    @com.fasterxml.jackson.annotation.JsonProperty("message")
    private String message;
    @com.fasterxml.jackson.annotation.JsonProperty("success")
    private Boolean success;
    @com.fasterxml.jackson.annotation.JsonProperty("total")
    private Long total;

    public static void main(String[] args) {
        SpringApplication.run(DomApplication.class, args);
    }



    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                //    registry.addMapping("/greeting-javaconfig").allowedOrigins("http://localhost:8080");
                registry.addMapping("/**").allowedOrigins("*");//.allowCredentials(true).allowedMethods(ORIGINS).maxAge(3600);


            }
        };
    }



    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration corsConfiguration = new CorsConfiguration();
        List<String> list = new ArrayList<>();
        list.add("*");
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedOriginPatterns(list);
        corsConfiguration.setExposedHeaders(list);
        corsConfiguration.setAllowedMethods(list);
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**",corsConfiguration);
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }


}
