package com.lt.dom.otcenum;

public enum EnumVefifyStatus {
    pending("barcode"),
    approved("reference number"),
    canceled("Barcode scan"),



    ;


    EnumVefifyStatus(String barcode) {

    }
}
