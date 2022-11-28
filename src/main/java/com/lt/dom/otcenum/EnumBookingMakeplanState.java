package com.lt.dom.otcenum;

public enum EnumBookingMakeplanState {
    awaiting_verification("awaiting_verification"),
    verification_expired("verification_expired"),
    awaiting_payment("awaiting_payment"),
    awaiting_confirmation("awaiting_confirmation"),
    confirmed("confirmed"),

    declined("declined"),
    cancelled("cancelled"),
    deleted("deleted"),
    ;










    EnumBookingMakeplanState(String barcode) {

    }
}
