package com.lt.dom.otcenum;

public enum EnumCapacitytype {
    FREE_SALE("Unlimited seats, can be sold without performing capacity checks."),
    LIMITED("Only a limited number of seats on offer, must check remaining capacity before booking."),
    ON_REQUEST("No capacity specified. Instead the operator (also called supplier) will manually confirm or reject each booking."),


    ;






    EnumCapacitytype(String barcode) {

    }
}
