package com.lt.dom.otcenum;

import com.lt.dom.OctResp.EnumResp;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum EnumIdType {
    身份证(1),
    护照(2),

    港澳通行证(4),
    台胞证(8),
    台湾通行证(16),
    军人证(32),
    学生证(64),
    驾驶证(128),
    回乡证(256),
    国际海员证(512),
    外国人永久居留证(1024),


    ;


    EnumIdType(Integer barcode) {

    }

    public static List List() {
        return  Arrays.stream(EnumIdType.values()).map(x->{

            EnumResp enumResp = new EnumResp();

            enumResp.setId(x.name());
            enumResp.setText(x.toString());
            return enumResp;
        }).collect(Collectors.toList());

    }
}
