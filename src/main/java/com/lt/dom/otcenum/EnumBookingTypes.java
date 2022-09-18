package com.lt.dom.otcenum;

public enum EnumBookingTypes {
    DATE_AND_TIME("You must book a specific date and start time. Availability and capacity is managed per start time."),
    DATE("You only need to specify the date when booking, no start time. Availability and capacity is managed per date"),
    PASS("You do not need to specify date or start time when booking"),

    ;


    EnumBookingTypes(String barcode) {

    }
}
