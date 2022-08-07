package com.lt.dom.otcenum;

public enum EnumPayoutStatus {
    paid("paid"),
    pending("pending"),
    in_transit("in_transit"),
    canceled("canceled"),
    failed("failed"),


    ;


    EnumPayoutStatus(String barcode) {

    }
}
