package com.lt.dom.otcenum;

public enum EnumPaymentStatus {
    Initialized("barcode"),
    Credited("reference number"),
    Pending("Barcode scan"),
    Authorized("Facial Recognition"),
    Captured("NFC tap"),
    Refunded("NFC tap"),
    Voided("NFC tap"),
    //Initialized" "Credited" "Pending" "Authorized" "Captured" "Refunded" "Voided"
    ;


    EnumPaymentStatus(String barcode) {

    }
}
