package com.lt.dom.RewardsPoint;

import com.lt.dom.oct.User;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 临证学习任务完成策略实现
 */
@UserIntegralTaskStrategyType(uri = "/appStudy/getFangZhengDetail")
public class StudyLinZhengUserTaskStrategy implements IUserIntegralTaskStrategy {
 
    @Autowired
    private IDoctorUserServiceImpl doctorUserService;
 
    @Override
    public void finishUserIntegralTask(User doctorUser) {
        doctorUserService.finishUserTask(doctorUser, CmaiConstants.UserTaskType.STUDY_LINZHENG_FIRST, CmaiConstants.UserTaskType.STUDY_LINZHENG);
    }
}
/*
————————————————
版权声明：本文为CSDN博主「csdn_guang」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/u010185805/article/details/116998482*/
