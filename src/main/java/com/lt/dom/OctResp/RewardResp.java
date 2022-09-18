package com.lt.dom.OctResp;

import com.lt.dom.oct.Reward;
import com.lt.dom.otcenum.EnumRewardType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


public class RewardResp {





    private EnumRewardType type;

    private String idId;
    private String url;

    private long stock;


    public static Reward from(Reward referral) {
        return null;
    }
}
