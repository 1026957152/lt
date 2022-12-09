package com.lt.dom.otcenum;

import com.lt.dom.OctResp.EnumResp;

import java.time.DayOfWeek;
import java.time.temporal.TemporalAdjusters;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum EnumReportBookingCompression {
    day("day"),
    week("week"),
    month("month"),
    year("year"),



    ;
    public static List<EnumResp> from() {
        return Arrays.stream(EnumReportBookingCompression.values()).map(x->{
            EnumResp enumResp = new EnumResp();
            enumResp.setId(x.name());
            enumResp.setText(x.toString());
            return enumResp;
        }).collect(Collectors.toList());
    }

    EnumReportBookingCompression(String barcode) {

    }
}
