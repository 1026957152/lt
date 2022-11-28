package com.lt.dom.otcenum;

import com.lt.dom.OctResp.EnumResp;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum EnumDeliveryFormat {
    QRCODE("Create_pass"),
    PDF_URL("Create_pass"),

    ;


    EnumDeliveryFormat(String barcode) {

    }
    public static List List() {
        return  Arrays.stream(EnumDeliveryFormat.values()).map(x->{

            EnumResp enumResp = new EnumResp();

            enumResp.setId(x.name());
            enumResp.setText(x.toString());
            return enumResp;
        }).collect(Collectors.toList());

    }
}
