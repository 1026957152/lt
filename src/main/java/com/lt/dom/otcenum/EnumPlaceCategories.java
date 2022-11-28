package com.lt.dom.otcenum;

import com.lt.dom.OctResp.EnumResp;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum EnumPlaceCategories {
    discovering("discovering",""),
    eating("eating",""),
    going_out("going_out",""),
    hiking("hiking",""),
    playing("playing",""),

    relaxing("relaxing",""),
    shopping("shopping",""),
    sightseeing("sightseeing",""),
    sleeping("sleeping",""),
    doing_sports("doing_sports",""),
    traveling("traveling","")

    ;

    private String key;
    private String name;

    EnumPlaceCategories(String key, String name) {

    }

    public static List List() {
        return  Arrays.stream(EnumPlaceCategories.values()).map(x->{

            EnumResp enumResp = new EnumResp();

            enumResp.setId(x.name());
            enumResp.setText(x.toString());
            return enumResp;
        }).collect(Collectors.toList());

    }

}
