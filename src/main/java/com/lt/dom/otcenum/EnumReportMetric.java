package com.lt.dom.otcenum;

import com.lt.dom.OctResp.EnumResp;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum EnumReportMetric {
    People("week"),
    Revenue("month"),
    Bookings("last_month"),


    ;
    public static List<EnumResp> from() {
        return Arrays.stream(EnumReportMetric.values()).map(x->{
            EnumResp enumResp = new EnumResp();
            enumResp.setId(x.name());
            enumResp.setText(x.toString());
            return enumResp;
        }).collect(Collectors.toList());
    }

    EnumReportMetric(String barcode) {

    }
}
