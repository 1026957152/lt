package com.lt.dom.otcenum;

import com.lt.dom.OctResp.EnumResp;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum EnumSideOfStreetType {
    left("gem-o","有效期180天",""),
    right("gift-o","",""),
    neither("gift-o","","")

    ,



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
        return  Arrays.stream(EnumSideOfStreetType.values()).map(x->{

            EnumResp enumResp = new EnumResp();

            enumResp.setId(x.name());
            enumResp.setText(x.toString());
            return enumResp;
        }).collect(Collectors.toList());

    }

    EnumSideOfStreetType(String icon, String barcode, String color) {
        this.text = barcode;
        this.icon = icon;


    }
}
