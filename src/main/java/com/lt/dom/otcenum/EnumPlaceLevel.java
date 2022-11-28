package com.lt.dom.otcenum;

import com.lt.dom.OctResp.EnumResp;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum EnumPlaceLevel {
    continent("continent"),
    country("country"),
    state("state"),
    region("region"),
    county("county"),
    city("city"),
    town("town"),
    village("village"),
    settlement("settlement"),
    locality("locality"),
    neighbourhood("neighbourhood"),
    archipelago("archipelago"),
    island("island"),
    poi("poi")
    ;


    EnumPlaceLevel(String barcode) {

    }

    public static List List() {
        return  Arrays.stream(EnumPlaceLevel.values()).map(x->{

            EnumResp enumResp = new EnumResp();

            enumResp.setId(x.name());
            enumResp.setText(x.toString());
            return enumResp;
        }).collect(Collectors.toList());

    }
}
