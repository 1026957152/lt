package com.lt.dom.otcenum;

import com.lt.dom.OctResp.EnumResp;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum EnumTags {
    桃花分支付("榆林桃花分"),
    榆林主人卡("榆林主人卡"),
    官方直营("官方直营"),
    随时定("随时定"),
    商品可退("商品可退"),
    必看("Must see"),
    需预定("Booking Required"),
    下午人少(" Less busy AM"),



    ;
    public static List List() {
        return  Arrays.stream(EnumTags.values()).map(x->{

            EnumResp enumResp = new EnumResp();

            enumResp.setId(x.name());
            enumResp.setText(x.toString());
            return enumResp;
        }).collect(Collectors.toList());

    }

    EnumTags(String barcode) {

    }
}
