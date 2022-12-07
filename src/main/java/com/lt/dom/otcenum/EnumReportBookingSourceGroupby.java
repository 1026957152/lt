package com.lt.dom.otcenum;

import com.lt.dom.OctResp.EnumResp;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum EnumReportBookingSourceGroupby {
    agent("week"),



    ;
    public static List<EnumResp> from() {
        return Arrays.stream(EnumReportBookingSourceGroupby.values()).map(x->{
            EnumResp enumResp = new EnumResp();
            enumResp.setId(x.name());
            enumResp.setText(x.toString());
            return enumResp;
        }).collect(Collectors.toList());
    }

    EnumReportBookingSourceGroupby(String barcode) {

    }
}
