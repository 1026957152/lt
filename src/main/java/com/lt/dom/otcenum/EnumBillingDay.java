package com.lt.dom.otcenum;

public enum EnumBillingDay {
    DefaultFromCustomerAccount("barcode"),
    SpecificDayOfMonth("reference number"),
    SubscriptionStartDay("Barcode scan"),
    ChargeTriggerDay("Facial Recognition"),
    SpecificDayOfWeek("NFC tap"),


    ;


    EnumBillingDay(String barcode) {

    }
}
