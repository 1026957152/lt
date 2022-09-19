package com.lt.dom.otcenum;

public enum EnumFulfillmentType {
    Create_pass("Create_pass"),
    Universal("Universal"),

    Free_ticket("Universal"),


    UNSPECIFIED_FULFILLMENT_TYPE("Universal"),
    PICKUP("Universal"),
    DELIVERY("Universal"),
    DINE_IN("Universal"),



    Unknow("Unknow"),

    ;


    EnumFulfillmentType(String barcode) {

    }
}
