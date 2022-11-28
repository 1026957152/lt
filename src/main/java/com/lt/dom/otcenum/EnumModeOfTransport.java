package com.lt.dom.otcenum;

import com.lt.dom.OctResp.EnumResp;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum EnumModeOfTransport {
    Walk("Walk"),
    Drive("Drive"),
    Indoor("Indoor"),
    Cycle("Cycle"),
    TrainTrip("TrainTrip"),
    BoatRide("BoatRide"),

    Run("Run"),
    Other("Other"),
    ;
    public static List List() {
        return  Arrays.stream(EnumModeOfTransport.values()).map(x->{

            EnumResp enumResp = new EnumResp();

            enumResp.setId(x.name());
            enumResp.setText(x.toString());
            return enumResp;
        }).collect(Collectors.toList());

    }

    EnumModeOfTransport(String barcode) {

    }
}
