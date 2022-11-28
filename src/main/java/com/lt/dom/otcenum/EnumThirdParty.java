package com.lt.dom.otcenum;

import com.lt.dom.OctResp.EnumResp;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum EnumThirdParty {
    TS("ts"),
    MT("reference number"),
    PFT("reference number"),


    ;

    public static List List() {
        return  Arrays.stream(EnumThirdParty.values()).map(x->{

            EnumResp enumResp = new EnumResp();

            enumResp.setId(x.name());
            enumResp.setText(x.toString());
            return enumResp;
        }).collect(Collectors.toList());

    }
    EnumThirdParty(String barcode) {

    }
}
