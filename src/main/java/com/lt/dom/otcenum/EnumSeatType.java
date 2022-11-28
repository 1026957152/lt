package com.lt.dom.otcenum;

import com.lt.dom.OctResp.EnumResp;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum EnumSeatType {

    //The type of seat
    CanReserve,//CanReserve
    NotASeat,//NotASeat (used to persist layout and designate isles)
    Blocked,//Blocked
    Wheelchair,//Wheelchair
    Companion,//Companion
    GeneralAdmission,//GeneralAdmission
    NA,//NA
    LoveSeatLeft,//LoveSeatLeft
    LoveSeatRight,
    ;//LoveSeatRight


    public static List<EnumResp> EnumList() {
        return Arrays.stream(EnumSeatTier.values()).map(x->{

            EnumResp enumResp = new EnumResp();
            enumResp.setId(x.name());
            //  enumResp.setName(x.name());
            enumResp.setText(x.toString());
            return enumResp;
        }).collect(Collectors.toList());
    }
}
