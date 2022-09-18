package com.lt.dom.otcenum;

public enum EnumIdentityType {
    phone("barcode"),
    weixin("reference number"),
    email("Facial Recognition"),
    identity_card("identity_card"),

    user_code("user_code"),

    ;


    EnumIdentityType(String barcode) {

    }
}
