package com.lt.dom.otcenum;

import com.lt.dom.OctResp.EnumResp;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum EnumPrivacyLevel {
    private_("private"),
    shareable("reference number"),
    public_("public"),


    ;


    EnumPrivacyLevel(String barcode) {

    }

    public static List List() {
       return  Arrays.stream(EnumPrivacyLevel.values()).map(x->{

            EnumResp enumResp = new EnumResp();

            enumResp.setId(x.name());
            enumResp.setText(x.toString());
            return enumResp;
        }).collect(Collectors.toList());

    }

}
