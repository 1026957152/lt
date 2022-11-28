package com.lt.dom.otcenum;

import com.lt.dom.OctResp.EnumResp;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum EnumShippingAddressCollection {
    auto("inclusive"),
    required("exclusive"),



    ;


    EnumShippingAddressCollection(String barcode) {

    }

    public static List List() {
        return  Arrays.stream(EnumShippingAddressCollection.values()).map(x->{

            EnumResp enumResp = new EnumResp();

            enumResp.setId(x.name());
            enumResp.setText(x.toString());
            return enumResp;
        }).collect(Collectors.toList());

    }
}
