package com.lt.dom.vo;

import com.lt.dom.otcenum.EnumIdentityType;

public class LogVo {
    private EnumIdentityType type;
    private String name;

    public EnumIdentityType getType() {
        return type;
    }

    public void setType(EnumIdentityType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LogVo(EnumIdentityType i, String username) {

        this.type = i;
        this.name = username;
    }
}
