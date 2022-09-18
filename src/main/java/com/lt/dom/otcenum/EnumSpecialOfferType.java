package com.lt.dom.otcenum;

public enum EnumSpecialOfferType {
    No_special_offer("No special offer (regular price)"),
    Date_specific("Date specific special price"),
    Late_booking("Late booking discount"),
    Early_booking("Early booking discount"),
    Duration_specific("Duration specific discount"),


    ;


    EnumSpecialOfferType(String barcode) {

    }
}
