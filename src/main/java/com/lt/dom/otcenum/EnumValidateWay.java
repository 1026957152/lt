package com.lt.dom.otcenum;

import com.lt.dom.OctResp.EnumLongIdResp;
import com.lt.dom.OctResp.EnumResp;
import com.lt.dom.oct.ComponentRight;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum EnumValidateWay {
    offline("barcode"),
    none("reference number"),
    online("Barcode scan"),

    offline_manual("线下人工经验"),


    ;

    public static List<EnumResp> EnumList() {
        return Arrays.stream(EnumValidateWay.values()).map(x->{

            EnumResp enumResp = new EnumResp();
            enumResp.setId(x.name());
            //  enumResp.setName(x.name());
            enumResp.setText(x.toString());
            return enumResp;
        }).collect(Collectors.toList());
    }

    EnumValidateWay(String barcode) {

    }
}
