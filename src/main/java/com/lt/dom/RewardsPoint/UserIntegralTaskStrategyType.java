package com.lt.dom.RewardsPoint;

import org.springframework.stereotype.Service;

import java.lang.annotation.*;

/**
 * 用户积分任务策略类型
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Service
public @interface UserIntegralTaskStrategyType {
 
    /**
     * 用户请求URI
     * @return
     */
    String[] uri();
 
}
/*
————————————————
版权声明：本文为CSDN博主「csdn_guang」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/u010185805/article/details/116998482*/
