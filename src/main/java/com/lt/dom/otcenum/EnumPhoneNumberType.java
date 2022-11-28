package com.lt.dom.otcenum;

public enum EnumPhoneNumberType {
    Business("barcode"),
    Cellular("reference number"),
    Home("Barcode scan"),
    Agent("Facial Recognition"),


    ;

    EnumPhoneNumberType(String barcode) {

    }
}
