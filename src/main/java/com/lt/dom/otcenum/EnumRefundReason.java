package com.lt.dom.otcenum;

import com.lt.dom.OctResp.EnumResp;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum EnumRefundReason {
    duplicate("barcode"),
    fraudulent("reference number"),
    requested_by_customer("Barcode scan"),
    expired_uncaptured_charge("Facial Recognition"),





    ;

    public static List<EnumResp> list() {
        return Arrays.stream(EnumRefundReason.values()).map(x->{
            EnumResp enumResp = new EnumResp();
            enumResp.setId(x.name());
            //  enumResp.setName(x.name());
            enumResp.setText(x.toString());

            return enumResp;
        }).collect(Collectors.toList());
    }


    EnumRefundReason(String barcode) {

    }
}
