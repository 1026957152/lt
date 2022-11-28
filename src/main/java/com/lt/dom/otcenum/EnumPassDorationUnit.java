package com.lt.dom.otcenum;

import com.lt.dom.OctResp.EnumResp;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum EnumPassDorationUnit {
    days("days"),
    months("months"),

    ;


    EnumPassDorationUnit(String barcode) {

    }

    public static List<EnumResp> from() {
        return Arrays.asList(EnumPassDorationUnit.values()).stream().map(x->{
            EnumResp enumResp = new EnumResp();
            enumResp.setId(x.name());
            //  enumResp.setName(x.name());
            enumResp.setText(x.toString());

            return enumResp;
        }).collect(Collectors.toList());
    }
}
