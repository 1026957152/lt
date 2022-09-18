package com.lt.dom.otcenum;
//https://manual.bookingsync.com/hc/en-us/articles/360019807738-Cancellation-policies
public enum EnumCancellUntil {
    _14_days_before_arrival("14 days before arrival"),
    _direct_after_reservation("direct after reservation"),


    ;

    EnumCancellUntil(String barcode) {

    }
    EnumCancellUntil() {

    }
}
