package com.lt.dom.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync
public class SpringAsyncConfig {
    
   // @Bean(name = "threadPoolTaskExecutor")
   // public Executor threadPoolTaskExecutor() {
    //    return new ThreadPoolTaskExecutor();
   // }
}