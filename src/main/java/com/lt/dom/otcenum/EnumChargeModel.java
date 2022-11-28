package com.lt.dom.otcenum;

import com.lt.dom.OctResp.EnumResp;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum EnumChargeModel {
    Flatfee("Flat fee pricing"),
    Perunit("Per unit pricing"),
    Volume("Volume pricing"),
    Tiered("Tiered pricing"),

/*    Flat fee pricing
    Per unit pricing
    Volume pricing*/

    ;


    EnumChargeModel(String barcode) {

    }

    public static List<EnumResp> from() {
        return Arrays.asList(EnumChargeModel.values()).stream().map(x->{
            EnumResp enumResp = new EnumResp();
            enumResp.setId(x.name());
            //  enumResp.setName(x.name());
            enumResp.setText(x.toString());

            return enumResp;
        }).collect(Collectors.toList());
    }
}
