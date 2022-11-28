package com.lt.dom.otcenum;

import com.lt.dom.OctResp.EnumResp;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum EnumUserType {//交易类型
    customer("为个人用户"),
    business("为企业用户"),
    app("平台"),



    ;

    public static List<EnumResp> from() {
        return Arrays.stream(EnumUserType.values()).map(x->{
            EnumResp enumResp = new EnumResp();
            enumResp.setId(x.name());
            enumResp.setText(x.toString());
            return enumResp;
        }).collect(Collectors.toList());
    }

    EnumUserType(String 入账退款) {

    }
}
