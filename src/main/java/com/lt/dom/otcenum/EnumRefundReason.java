package com.lt.dom.otcenum;

public enum EnumRefundReason {
    duplicate("barcode"),
    fraudulent("reference number"),
    requested_by_customer("Barcode scan"),
    expired_uncaptured_charge("Facial Recognition"),





    ;


    EnumRefundReason(String barcode) {

    }
}
