package com.lt.dom.config;

import com.lt.dom.RewardsPoint.IDoctorUserServiceImpl;
import com.lt.dom.RewardsPoint.IUserIntegralTaskStrategy;
import com.lt.dom.RewardsPoint.UserIntegralTaskStrategyType;
import com.lt.dom.oct.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 积分任务拦截器
 */

@Component
public class UserIntegralTaskInterceptor implements HandlerInterceptor {
 
    private Map<String, IUserIntegralTaskStrategy> userIntegralTaskStrategyMap = new HashMap<>();
 
    @Autowired
    private IDoctorUserServiceImpl doctorUserService;
 
    @Autowired
    public void setUserIntegralTaskStrategyMap(List<IUserIntegralTaskStrategy> userIntegralTaskStrategies) {
        userIntegralTaskStrategies.forEach(userIntegralTaskStrategy -> {
            String[] uris = Objects.requireNonNull(AnnotationUtils.findAnnotation(userIntegralTaskStrategy.getClass(), UserIntegralTaskStrategyType.class)).uri();
            userIntegralTaskStrategyMap.putAll(Arrays.stream(uris).collect(Collectors.toMap(uri -> uri, uri -> userIntegralTaskStrategy)));
        });
    }
 
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        IUserIntegralTaskStrategy userIntegralTaskStrategy = userIntegralTaskStrategyMap.get(request.getRequestURI());
        if (userIntegralTaskStrategy != null) {


            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();


            userIntegralTaskStrategy.finishUserIntegralTask(doctorUserService.getById(user));
        }
    }
}
/*
————————————————
版权声明：本文为CSDN博主「csdn_guang」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/u010185805/article/details/116998482*/
