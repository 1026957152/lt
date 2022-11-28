package com.lt.dom.otcenum;

public enum EnumFulfillment_behavior {
    Create_pass("Create_pass"),
    Create_voucher("Create_pass"),

 //   Create_voucher("Create_pass"),

    Universal("Universal"),

    Free_ticket("Universal"),


    UNSPECIFIED_FULFILLMENT_TYPE("Universal"),
    PICKUP("Universal"),
    DELIVERY("Universal"),


    DINE_IN("Universal"),



    Unknow("Unknow"),

    ;


    EnumFulfillment_behavior(String barcode) {

    }
}
