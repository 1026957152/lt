package com.lt.dom.RewardsPoint;

import com.lt.dom.config.UserIntegralTaskInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 拦截器配置
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
 
    private List<String> uriList = new ArrayList<>();
 
    @Autowired
    private UserIntegralTaskInterceptor integralTaskInterceptor;
 
    @Autowired
    public void setUserIntegralTaskStrategyMap(List<IUserIntegralTaskStrategy> userIntegralTaskStrategies) {
        userIntegralTaskStrategies.forEach(userIntegralTaskStrategy -> {
            String[] uris = Objects.requireNonNull(AnnotationUtils.findAnnotation(userIntegralTaskStrategy.getClass(), UserIntegralTaskStrategyType.class)).uri();
            uriList.addAll(Arrays.asList(uris));
        });
    }
 
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(integralTaskInterceptor).addPathPatterns(this.uriList);
    }
 
}
/*
————————————————
版权声明：本文为CSDN博主「csdn_guang」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/u010185805/article/details/116998482*/
