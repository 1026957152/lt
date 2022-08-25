package com.lt.dom.vo;

import com.lt.dom.otcenum.EnumIdentityType;

public class IdentityVo {


    private EnumIdentityType type;
    private String credential;

    public IdentityVo(EnumIdentityType weixin, String username) {
        this.type = weixin;
        this.credential = username;
    }

    public EnumIdentityType getType() {
        return type;
    }

    public void setType(EnumIdentityType type) {
        this.type = type;
    }

    public String getCredential() {
        return credential;
    }

    public void setCredential(String credential) {
        this.credential = credential;
    }
}
