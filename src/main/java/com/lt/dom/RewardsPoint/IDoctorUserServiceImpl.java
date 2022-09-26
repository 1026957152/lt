package com.lt.dom.RewardsPoint;


import com.lt.dom.OctResp.AssetResp;
import com.lt.dom.config.LtConfig;
import com.lt.dom.oct.*;
import com.lt.dom.otcenum.EnumAssetType;
import com.lt.dom.repository.AssetRepository;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class IDoctorUserServiceImpl {

    @Autowired
    private LtConfig ltConfig;

    @Autowired
    private AssetRepository assetRepository;


    public void finishUserTask(User doctorUser, CmaiConstants.UserTaskType studyLinzhengFirst, CmaiConstants.UserTaskType studyLinzheng) {

        System.out.println("============这里玩车了一线任务啊啊啊");
    }

    public User getById(User user) {
        return user;
    }

    public void finishUserTask(User doctorUser, String uniCode) {
        System.out.println("============这里玩车了uniCode");

    }
}
