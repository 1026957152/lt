package com.lt.dom.otcenum;

public enum EnumAvailabilityType {
    INVENTORY("Fixed dates & times"),
    DATE_ENQUIRY("Any date - Accept enquiries for any date"),
    NO_DATE("Date not required for booking"),


    OPENING_HOURS("Date not required for booking"),
    START_TIME("Date not required for booking"),

    ;


    EnumAvailabilityType(String barcode) {

    }
}
