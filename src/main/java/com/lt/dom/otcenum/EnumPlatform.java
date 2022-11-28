package com.lt.dom.otcenum;

import com.lt.dom.OctResp.EnumResp;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum EnumPlatform {
    LT("旅投"),
    TS("天时同城"),



    ;


    EnumPlatform(String barcode) {

    }

    public static List List() {
        return  Arrays.stream(EnumPlatform.values()).map(x->{

            EnumResp enumResp = new EnumResp();

            enumResp.setId(x.name());
            enumResp.setText(x.toString());
            return enumResp;
        }).collect(Collectors.toList());

    }
}
