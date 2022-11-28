package com.lt.dom.otcenum;

public enum EnumPayment_behavior {

    record_payment("record_payment"),
    normal_payment("record_payment"),

    allow_incomplete("Create_pass"),
    error_if_incomplete("Create_pass"),

 //   Create_voucher("Create_pass"),

    pending_if_incomplete("Universal"),

    default_incomplete("Universal"),



    ;


    EnumPayment_behavior(String barcode) {

    }
}
