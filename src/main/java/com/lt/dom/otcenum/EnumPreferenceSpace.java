package com.lt.dom.otcenum;

import com.lt.dom.OctResp.EnumResp;

import java.util.List;
import java.util.stream.Collectors;

public enum EnumPreferenceSpace {
    default_("default_",EnumValueType.string_,"user"),
    employee("employee",EnumValueType.string_,"employee"),
    user("user",EnumValueType.string_,"user"),

    ;

    private final String role;

    public String getRole() {
        return role;
    }

    String key;
    EnumValueType type;

    public static List<EnumResp> list(List<EnumPreferenceSpace> enumPayChannels) {
        return enumPayChannels.stream().map(x->{
            EnumResp enumResp = new EnumResp();
            enumResp.setId(x.name());
            //  enumResp.setName(x.name());
            enumResp.setText(x.toString());

            return enumResp;
        }).collect(Collectors.toList());
    }
    public EnumValueType getType() {
        return type;
    }

    public void setType(EnumValueType type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    EnumPreferenceSpace(String key, EnumValueType type,String role) {

        this.key = key;
        this.type = type;
        this.role = role;
    }

}
