package com.lt.dom.otcenum;

import com.lt.dom.OctResp.EnumLongIdResp;
import com.lt.dom.OctResp.EnumResp;
import com.lt.dom.oct.Attraction;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum EnumAvailabilityType {
    INVENTORY("Fixed dates & times"),
    DATE_ENQUIRY("Any date - Accept enquiries for any date"),
    NO_DATE("Date not required for booking"),


    OPENING_HOURS("Date not required for booking"),
    START_TIME("Date not required for booking"),

    ;


    EnumAvailabilityType(String barcode) {

    }

    public static List<EnumResp> EnumList() {
        return Arrays.stream(EnumAvailabilityType.values()).map(x->{

            EnumResp enumResp = new EnumResp();
            enumResp.setId(x.name());
            //  enumResp.setName(x.name());
            enumResp.setText(x.toString());
            return enumResp;
        }).collect(Collectors.toList());
    }
}
