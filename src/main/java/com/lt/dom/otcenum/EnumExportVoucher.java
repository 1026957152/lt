package com.lt.dom.otcenum;

public enum EnumExportVoucher {
    voucher("barcode"),
    redemption("reference number"),
    publication("Barcode scan"),
    customer("Facial Recognition"),
    points_expiration("NFC tap"),


    ;


    EnumExportVoucher(String barcode) {

    }
}
