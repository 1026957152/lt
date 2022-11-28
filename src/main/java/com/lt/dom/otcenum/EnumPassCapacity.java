package com.lt.dom.otcenum;

import com.lt.dom.OctResp.EnumResp;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum EnumPassCapacity {
    FREE_SALE("Fixed dates & times"),
    LIMITED ("Any date - Accept enquiries for any date"),
    ON_REQUEST("Date not required for booking"),



    ;


    EnumPassCapacity(String barcode) {

    }

    public static List<EnumResp> EnumList() {
        return Arrays.stream(EnumPassCapacity.values()).map(x->{

            EnumResp enumResp = new EnumResp();
            enumResp.setId(x.name());
            //  enumResp.setName(x.name());
            enumResp.setText(x.toString());
            return enumResp;
        }).collect(Collectors.toList());
    }
}
