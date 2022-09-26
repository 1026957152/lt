package com.lt.dom.RewardsPoint;

import com.lt.dom.oct.User;

/**
 * 策略模式的接口类
 */
public interface IUserIntegralTaskStrategy {
 
    /**
     * 完成用户积分任务
     * @param doctorUser
     * @return
     */
    void finishUserIntegralTask(User doctorUser);
 
}
/*
————————————————
版权声明：本文为CSDN博主「csdn_guang」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/u010185805/article/details/116998482*/
