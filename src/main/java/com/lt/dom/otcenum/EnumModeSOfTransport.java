package com.lt.dom.otcenum;

import com.lt.dom.OctResp.EnumResp;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
//https://developer.here.com/documentation/intermodal-routing/dev_guide/concepts/modes.html
public enum EnumModeSOfTransport {
    highSpeedTrain	("High-speed trains"),
    intercityTrain		("Intercity/EuroCity trains"),
    interRegionalTrain		("Inter-regional and fast trains"),
    regionalTrain	("	Regional and other trains"),
    cityTrain	("	City trains"),
    bus	("	Buses"),
    ferry	("	Boats/Ferries"),
    subway	("	Metros/Subways"),
    lightRail	("	Trams"),
    privateBus	("	Ordered services/Taxis"),
    inclined	("	Inclined/Funiculars"),
    aerial	("	Aerials/Cable cars"),
    busRapid	("	Rapid buses"),
    monorail	("	Monorails"),
    flight	("	Airplanes"),
    walk	("	Walk"),
    car	("	Car"),
    bicycle	("	Bicycle"),

    ;
    public static List List() {
        return  Arrays.stream(EnumModeSOfTransport.values()).map(x->{

            EnumResp enumResp = new EnumResp();

            enumResp.setId(x.name());
            enumResp.setText(x.toString());
            return enumResp;
        }).collect(Collectors.toList());

    }

    EnumModeSOfTransport(String barcode) {

    }
}
