package com.lt.dom.otcenum;

import com.lt.dom.OctResp.EnumResp;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum EnumTermType {
    PrivacyPolicy("barcode"),
    TermsOfUse("reference number"),
    booking("reference number"),


    ;

    public static List List() {
        return  Arrays.stream(EnumTermType.values()).map(x->{

            EnumResp enumResp = new EnumResp();

            enumResp.setId(x.name());
            enumResp.setText(x.toString());
            return enumResp;
        }).collect(Collectors.toList());

    }
    EnumTermType(String barcode) {

    }
}
