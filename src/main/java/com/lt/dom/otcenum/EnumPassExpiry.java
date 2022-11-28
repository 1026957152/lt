package com.lt.dom.otcenum;

import com.lt.dom.OctResp.EnumResp;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum EnumPassExpiry {
    NEVER("The pass actually never expires. Simple as that!"),
    FIXED_DATE("The pass is valid until a fixed date as specified in the activity rate fixedPassExpiryDate."),
    RELATIVE_DATE("The pass is valid for X days after purchase, as specified in the activity rate passValidForDays ."),

    ;

    public static List<EnumResp> EnumList() {
        return Arrays.stream(EnumPassExpiry.values()).map(x->{

            EnumResp enumResp = new EnumResp();
            enumResp.setId(x.name());
            //  enumResp.setName(x.name());
            enumResp.setText(x.toString());
            return enumResp;
        }).collect(Collectors.toList());
    }



    EnumPassExpiry(String barcode) {

    }
}
