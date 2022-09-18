package com.lt.dom.otcReq;

import com.lt.dom.otcenum.EnumRewardType;


public class RewardReq {





    private EnumRewardType type;

    private String idId;
    private String url;

    private long stock;
    private String name;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
