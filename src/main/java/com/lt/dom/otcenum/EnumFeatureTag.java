package com.lt.dom.otcenum;

import com.lt.dom.OctResp.EnumResp;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum EnumFeatureTag {
    持续时间("gem-o","有效期180天",""),
    接送("gift-o","",""),
    团体("gift-o","",""),

    小团体("gift-o","",""),
    私人团("gift-o","",""),
    可选语音导游("gift-o","",""),


    有效期("gem-o","有效期180天",""),

    免费取消("gift-o","Free cancellation",""),
    先预定后支付("gift-o","Reserve new & pay later",""),


    演出介绍("gift-o","",""),
    剧情介绍("gift-o","",""),
    演出团体("gift-o","",""),


/*        productResp.setFeatureTags(Arrays.asList(FeatureTag.of("","gem-o","45分~1小时"),
                FeatureTag.of("","gift-o","接送")));*/

    ;

    private String icon;
    private String text;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public static List List() {
        return  Arrays.stream(EnumFeatureTag.values()).map(x->{

            EnumResp enumResp = new EnumResp();

            enumResp.setId(x.name());
            enumResp.setText(x.toString());
            return enumResp;
        }).collect(Collectors.toList());

    }

    EnumFeatureTag(String icon,String barcode,String color) {
        this.text = barcode;
        this.icon = icon;


    }
}
