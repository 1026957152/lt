package com.lt.dom.otcenum;

public enum EnumRequestApprove {
    examine_approve("barcode"),
    examine_reject("reference number"),
    request("reference number"),

    examine("reference number"),


    ;


    EnumRequestApprove(String barcode) {

    }
}
