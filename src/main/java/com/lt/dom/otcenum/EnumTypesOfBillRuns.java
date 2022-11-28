package com.lt.dom.otcenum;

import com.lt.dom.OctResp.EnumResp;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum EnumTypesOfBillRuns {

    ad_hoc(),
    scheduled (),

    ;

    public static List<EnumResp> EnumList() {
        return Arrays.stream(EnumTypesOfBillRuns.values()).map(x->{

            EnumResp enumResp = new EnumResp();
            enumResp.setId(x.name());
            //  enumResp.setName(x.name());
            enumResp.setText(x.toString());
            return enumResp;
        }).collect(Collectors.toList());
    }
}
