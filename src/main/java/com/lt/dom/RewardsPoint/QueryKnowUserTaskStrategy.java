package com.lt.dom.RewardsPoint;

import com.lt.dom.oct.User;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 首次查看知识库任务完成策略实现
 */
@UserIntegralTaskStrategyType(uri = {"/know/fangji/detail", "/know/jibing/detail", "/know/shuji/bookContent",
        "/know/yaocai/detail", "/know/yian/yiAnDetail", "/know/zhenjiu/jingluoDetail"})
public class QueryKnowUserTaskStrategy implements IUserIntegralTaskStrategy {
 
    @Autowired
    private IDoctorUserServiceImpl doctorUserService;
 
    @Override
    public void finishUserIntegralTask(User doctorUser) {
        doctorUserService.finishUserTask(doctorUser, CmaiConstants.UserTaskType.QUERY_KNOWLEDGE_FIRST.getUniCode());
    }
}
/*
————————————————
版权声明：本文为CSDN博主「csdn_guang」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/u010185805/article/details/116998482*/
