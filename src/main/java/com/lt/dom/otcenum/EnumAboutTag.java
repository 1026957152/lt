package com.lt.dom.otcenum;

import com.lt.dom.OctResp.EnumResp;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum EnumAboutTag {
    退票规则("gem-o","Cancellation policy",""),
    预定费用("gift-o","Booking fee",""),
    供应商("gift-o","Provider",""),

    注意事项("gift-o","What to remember",""),
    地址("gift-o","Where",""),
    不包含("gift-o","What's not included",""),


    包含("gem-o","What's included",""),

    防疫("gift-o","COVID-19 information",""),


    期望("gift-o","What to expect",""),


    因为("gift-o","Do this because",""),




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
        return  Arrays.stream(EnumAboutTag.values()).map(x->{

            EnumResp enumResp = new EnumResp();

            enumResp.setId(x.name());
            enumResp.setText(x.toString());
            return enumResp;
        }).collect(Collectors.toList());

    }

    EnumAboutTag(String icon, String barcode, String color) {
        this.text = barcode;
        this.icon = icon;


    }
}
