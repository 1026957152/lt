package com.lt.dom.otcenum;

public enum EnumRequestStatus {
    APPROVE("barcode"),
    REJECT("reference number"),
    ABSTAIN("Barcode scan"),
    RESET("Facial Recognition"),

/*
    APPROVE, REJECT, ABSTAIN, RESET*/
    ;


    EnumRequestStatus(String barcode) {

    }
}
