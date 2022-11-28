package com.lt.dom.otcenum;

import com.lt.dom.OctResp.EnumResp;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum EnumBillingPeriod {


    Month("Month"),
    Quarter("Quarter"),
    SemiAnnual("Semi-Annual"),
            Annual("barcode"),
    EighteenMonths("Eighteen Months"),
    TwoYears("Two Years"),
    ThreeYears("Three Years"),
    FiveYears("Five Years"),
    SpecificMonths("Specific Months"),
    SubscriptionTerm("Subscription Term"),
    Week("Week"),
    SpecificWeeks("Specific Weeks"),
    ;


    EnumBillingPeriod(String barcode) {

    }

    public static List<EnumResp> from() {
        return Arrays.asList(EnumBillingPeriod.values()).stream().map(x->{
            EnumResp enumResp = new EnumResp();
            enumResp.setId(x.name());
            //  enumResp.setName(x.name());
            enumResp.setText(x.toString());

            return enumResp;
        }).collect(Collectors.toList());
    }
}
