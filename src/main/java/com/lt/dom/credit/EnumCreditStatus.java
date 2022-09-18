package com.lt.dom.credit;

public enum EnumCreditStatus {
    请求("barcode"),
    已授信("reference number"),
    已放款("Barcode scan"),


    ;


    EnumCreditStatus(String barcode) {

    }
}
