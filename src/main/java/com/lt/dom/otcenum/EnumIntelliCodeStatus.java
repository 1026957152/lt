package com.lt.dom.otcenum;

import com.lt.dom.OctResp.EnumResp;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum EnumIntelliCodeStatus {
    not_redeemed("ts"),
    redeemed("reference number"),
    archived("reference number"),


/*    Can be applied to a subscription.
    redeemed
    Cannot be applied to a subscription as the coupon code has been already used.
    archived
    Cannot be applied to a subscription as it has been made inactive.*/

    ;

    public static List List() {
        return  Arrays.stream(EnumIntelliCodeStatus.values()).map(x->{

            EnumResp enumResp = new EnumResp();

            enumResp.setId(x.name());
            enumResp.setText(x.toString());
            return enumResp;
        }).collect(Collectors.toList());

    }
    EnumIntelliCodeStatus(String barcode) {

    }
}
