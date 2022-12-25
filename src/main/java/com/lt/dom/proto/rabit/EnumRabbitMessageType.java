package com.lt.dom.proto.rabit;

import com.lt.dom.OctResp.EnumResp;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum EnumRabbitMessageType {

    BUY_CITY_HERO_NOTIFICATION(),

    VERIFICATION_TOEKN(),

    ;

    public static List<EnumResp> EnumList() {
        return Arrays.stream(EnumRabbitMessageType.values()).map(x->{

            EnumResp enumResp = new EnumResp();
            enumResp.setId(x.name());
            //  enumResp.setName(x.name());
            enumResp.setText(x.toString());
            return enumResp;
        }).collect(Collectors.toList());
    }
}
